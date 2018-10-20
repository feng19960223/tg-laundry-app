package com.turingoal.laundry.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.utils.BluetoothUtils;
import com.turingoal.common.utils.TgDateUtil;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgHttpCallback;
import com.turingoal.common.utils.TgHttpUtil;
import com.turingoal.common.utils.TgJsonUtil;
import com.turingoal.laundry.DataUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.ReaderHolder;
import com.turingoal.laundry.bean.Record;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.constants.ConstantUrls;
import com.turingoal.laundry.ui.adapter.RfidAdapter;
import com.turingoal.laundry.ui.dialog.RfidQrDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.core.Util;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.RXD_TagData;
import invengo.javaapi.protocol.IRP1.ReadTag;

/**
 * 扫描activity
 */
@Route(path = ConstantActivityPath.RFID)
public class RfidActivity extends TgBaseActivity implements IMessageNotificationReceivedHandle {
    @BindView(R.id.tvTitle)
    TextView tvTitle; // title
    @BindView(R.id.tvEnd)
    TextView tvEnd; // 清空
    @BindView(R.id.tvSendUnit)
    TextView tvSendUnit; // 发送单位
    @BindView(R.id.tvReceiveUnit)
    TextView tvReceiveUnit; // 承接单位
    @BindView(R.id.tvWorkUser)
    TextView tvWorkUser; // 操作员
    @BindView(R.id.tvTime)
    TextView tvTime; // 扫描时间
    @BindView(R.id.tvTotalNumber)
    TextView tvTotalNumber; // 总计
    @BindView(R.id.llInput)
    LinearLayout llInput; // 正在扫描视图控制器
    @BindView(R.id.llNoStart)
    LinearLayout llNoStart; // 初始化的时候，点击清空的时候显示，扫描控制器
    @BindView(R.id.rvRfid)
    RecyclerView rvRfid; // 扫描结果V
    private RfidAdapter mAdapter;  // 扫描结果C
    private List<RXD_TagData> mRxdTagData = new ArrayList<>(); // 所有扫描数据
    private ReaderHolder readerHolder; // 设备
    private Date startTime; // 扫描开始时间

    @Override
    protected int getLayoutID() {
        return R.layout.activity_rfid;
    }

    @Override
    protected void initialized() {
        readerHolder = ReaderHolder.getInstance();
        tvTitle.setText(R.string.activity_rfid_receive);
        if (TgApplication.type == TgApplication.TYPE_SEND) { // 发净
            tvTitle.setText(R.string.activity_rfid_send);
        }
        tvEnd.setText(R.string.rfid_title_cancel);
        tvReceiveUnit.setText(getString(R.string.rfid_receiveUnit) + TgSystemHelper.getFactoryName());
        tvWorkUser.setText(getString(R.string.rfid_workUser) + TgSystemHelper.getRealname() + " " + TgSystemHelper.getCellphoneNum());
        initRecyclerAndAdapter();
        if (BluetoothUtils.isOpenBluetooth()
                && !TextUtils.isEmpty(TgApplication.bluetoothAddress)
                && !TextUtils.isEmpty(TgApplication.bluetoothName)) { // 蓝牙打开状态并且 设备名字并且地址是不是空的
            readerHolder.getCurrentReader().setChannelType(BaseReader.ReaderChannelType.RFID_CHANNEL_TYPE);
            readerHolder.getCurrentReader().onMessageNotificationReceived.add(RfidActivity.this);
            readerHolder.sendMessage(new ReadTag(ReadTag.ReadMemoryBank.EPC_6C)); // 读卡
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (readerHolder.isConnected()) {
            readerHolder.getCurrentReader().onMessageNotificationReceived.remove(this);
        }
    }

    /**
     * OnClick
     */
    @OnClick({R.id.tvStart, R.id.tvEnd, R.id.btnGenerate})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tvStart:
                defaultFinish();
                break;
            case R.id.tvEnd:
                llInput.setVisibility(View.GONE);
                llNoStart.setVisibility(View.VISIBLE);
                tvEnd.setVisibility(View.GONE);
                mRxdTagData.clear();
                mAdapter.setNewData(null);
                break;
            case R.id.btnGenerate: // 生成流水单
                showGenerateDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化recyclerView和adapter
     */
    private void initRecyclerAndAdapter() {
        rvRfid.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RfidAdapter();
        mAdapter.isFirstOnly(true); // 再次打开不要动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); // 动画
        rvRfid.setAdapter(mAdapter);
        mAdapter.setEmptyView(getEmptyView((ViewGroup) rvRfid.getParent()));
    }

    /**
     * 从扫描枪读取回调
     */
    @Override
    public void messageNotificationReceivedHandle(final BaseReader baseReader, final IMessageNotification msg) {
        if (BluetoothUtils.isOpen() && baseReader.isConnected()) { // 蓝牙开启，设备连接状态
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (msg instanceof RXD_TagData) {
                        if (mAdapter.getData().isEmpty()) { // 第一条数据被录入
                            startTime = new Date();
                            llInput.setVisibility(View.VISIBLE);
                            llNoStart.setVisibility(View.GONE);
                            tvEnd.setVisibility(View.VISIBLE);
                            tvTime.setText(TgDateUtil.date2String(new Date(), TgDateUtil.FORMAT_YYYY_MM_DD_ZH));
                            tvSendUnit.setText(getString(R.string.rfid_sendUnit) + ""); // TODO: 2018/1/17
                        }
                        RXD_TagData data = (RXD_TagData) msg;
                        String epc = Util.convertByteArrayToHexString(data.getReceivedMessage().getEPC()); // epc唯一
                        boolean isExists = false; // 这个标签是否被扫描过
                        for (RXD_TagData rxdTagData : mRxdTagData) { // 这个标签已经被扫描过了
                            String oldEpc = Util.convertByteArrayToHexString(rxdTagData.getReceivedMessage().getEPC());
                            if (epc.equals(oldEpc)) {
                                isExists = true;
                            }
                        }
                        if (!isExists) { // 如果是一条新的数据
                            mRxdTagData.add(data); // 将记录加入List
                            mAdapter.setNewData(DataUtil.rxdTagData2ScanTotal(mRxdTagData)); // 将mRxdTagDate转化成adapter需要的数据 并更新adapter
                            tvTotalNumber.setText(String.format(getString(R.string.rfid_total_number), "" + mRxdTagData.size())); // 总数
                        }
                    }
                }
            });
        }
    }

    /**
     * 生成流水单提示
     */
    private void showGenerateDialog() {
        TgDialogUtil.showConfirmDialog(this, getString(R.string.hint), getString(R.string.rfid_generate_hint), new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                generateRequest();
            }
        });
    }

    /**
     * 数据上传服务器
     */
    private void generateRequest() {
        // 对象构建 start
        final Record userRecord = new Record();
        userRecord.setSendUnit("ABCDEFGHIJ"); // 发送单位--医院
        userRecord.setSendUnitCode("ABCDEFGHIJ"); // 发送单位--医院
        userRecord.setReceiveUnit(TgSystemHelper.getFactoryName()); // 接收单位
        userRecord.setUserId(TgSystemHelper.getId()); // 用户id
        userRecord.setUserName(TgSystemHelper.getRealname()); // 用户真实姓名
        userRecord.setUserPhone(TgSystemHelper.getCellphoneNum()); // 用户电话
        userRecord.setTotal("" + mRxdTagData.size()); // 总数
        userRecord.setGpsLongitute("ABCDEFGHIJ"); // 经度
        userRecord.setGpsLatitude("ABCDEFGHIJ"); // 纬度
        userRecord.setDeviceId("ABCDEFGHIJ"); // 扫描枪设备ID
        userRecord.setDevicePower("ABCDEFGHIJ"); // 设备功率
        userRecord.setDeviceScanRadius("ABCDEFGHIJ"); // 扫描半径
        userRecord.setStarTime(startTime);
        userRecord.setEndTime(new Date());
        userRecord.setStatus("" + 1);
        userRecord.setType("" + TgApplication.type); // 1：收污 2：发净
        userRecord.setScanTotals(DataUtil.rxdTagData2ScanTotalAndScan(mRxdTagData)); // 数据
        // 对象构建 end
        String jsonRecord = JSON.toJSONString(userRecord); // json上传
        PostRequest request = TgHttpUtil.requestPost(ConstantUrls.URL_RECORD_ADD, getHttpTaskKey());
        request.upJson(jsonRecord);
        request.execute(new TgHttpCallback(null) {
            @Override
            public void successHandler(final TgResponseBean result) {
                if (result.isSuccess()) {
                    if (result.getData() == null) {
                        return;
                    }
                    Record serverRecord = TgJsonUtil.jsonResultBean2Object(result, Record.class);
                    if (serverRecord != null) {
                        userRecord.setId(serverRecord.getId());
                        userRecord.setCodeNum(serverRecord.getCodeNum());
                        qrGenerate(userRecord); // 成功拿到单号后
                    }
                } else {
                    mAdapter.loadMoreFail(); // 获取更多数据失败，点击重试
                    TgDialogUtil.showToast(result.getMsg()); // 弹出错误信息
                }
            }
        });
    }

    /**
     * 二维码生成
     */
    private void qrGenerate(final Record record) {
        new RfidQrDialog.Builder(this)
                .setTotalNumber(mRxdTagData.size()) // 总数
                .setRecordNum(record.getCodeNum()) // 单号
                .setSendUnit(record.getReceiveUnit()) // 发送单位
                .setReceiveUnit(TgSystemHelper.getFactoryName()) // 承接单位
                .setWorkUser(TgSystemHelper.getRealname())
                .setWorkUserPhone(TgSystemHelper.getCellphoneNum())
                .setTime(TgDateUtil.date2String(new Date(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH))
                .addCancelListener(new RfidQrDialog.Builder.OnCancelClickListener() {
                    @Override
                    public void onCancelClick(RfidQrDialog dialog) {
                        dialog.dismiss();
                        TgSystemHelper.handleIntentAndFinishWithObj(ConstantActivityPath.RECORD, "record", record, RfidActivity.this);
                    }
                }).build().show();
    }
}
