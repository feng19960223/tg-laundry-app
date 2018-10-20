package com.turingoal.laundry.ui.fragment;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.request.PostRequest;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseFragment;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.constants.TgConstantGetDataType;
import com.turingoal.common.utils.BluetoothUtils;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgHttpCallback;
import com.turingoal.common.utils.TgHttpUtil;
import com.turingoal.common.utils.TgJsonUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.ReaderHolder;
import com.turingoal.laundry.bean.Record;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.constants.ConstantUrls;
import com.turingoal.laundry.ui.adapter.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import invengo.javaapi.core.BaseReader;
import invengo.javaapi.core.IMessageNotification;
import invengo.javaapi.handle.IMessageNotificationReceivedHandle;
import invengo.javaapi.protocol.IRP1.RXD_ReaderElectricQuantity;
import invengo.javaapi.protocol.IRP1.Reader;
import invengo.javaapi.protocol.IRP1.SysConfig_800;
import invengo.javaapi.protocol.IRP1.SysQuery_800;
import invengo.javaapi.protocol.receivedInfo.ReaderElectricQuantityReceivedInfo;

/**
 * 首页Fragment
 */

public class HomeFragment extends TgBaseFragment implements IMessageNotificationReceivedHandle {
    @BindView(R.id.tvStart)
    TextView tvStart; // 设备点量剩余
    @BindView(R.id.tvEnd)
    TextView tvEnd; // 范围
    @BindView(R.id.tvName)
    TextView tvName; // 名字
    @BindView(R.id.tvWorkId)
    TextView tvWorkId; // 工号
    @BindView(R.id.tvPhone)
    TextView tvPhone; // 电话
    @BindView(R.id.llConnect)
    LinearLayout llConnect; // 未连接视图控制
    @BindView(R.id.rvRecord)
    RecyclerView rvRecord; // 流水单V
    private RecordAdapter mAdapter = new RecordAdapter(); // 流水单C
    private ReaderHolder readerHolder;
    private int[] scopeArray = {0, 3, 6, 9, 12, 15, 18, 21, 24, 27, 30}; // 功率取值范围
    private int limitSize = 10; // 一次加载多少条数据
    private int pageSize = 2; // 第几页

    private static final int HANDLER_RXD_ReaderElectricQuantity = 0; //  电量
    /**
     * handler
     */
    private Handler cardOperationHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case HANDLER_RXD_ReaderElectricQuantity: // 电量
                    tvStart.setText((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialized() {
        readerHolder = ReaderHolder.getInstance();
        tvName.setText(TgSystemHelper.getRealname());
        tvWorkId.setText(TgSystemHelper.getCodeNum());
        tvPhone.setText(TgSystemHelper.getCellphoneNum());
        initRecyclerAndAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BluetoothUtils.isOpenBluetooth()) { // 蓝牙打开状态
            if (readerHolder.isConnected()) { // 设备连接状态
                readerHolder.getCurrentReader().onMessageNotificationReceived.add(HomeFragment.this);
                connectedStatus();
            } else { // 设备未连接状态
                if (!TextUtils.isEmpty(TgApplication.bluetoothAddress) && !TextUtils.isEmpty(TgApplication.bluetoothName)) { // 设备名字并且地址是不是空的
                    new ConnectReaderTask().execute();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (readerHolder.isConnected()) {
            readerHolder.getCurrentReader().onMessageNotificationReceived.remove(this);
        }
    }

    /**
     * OnClick
     */
    @OnClick({R.id.tvStart, R.id.tvEnd, R.id.btnConnect})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tvStart: // 设备电量
                TgSystemHelper.handleIntent(ConstantActivityPath.ELECTRICITY); // 跳转到设备配置页面
                break;
            case R.id.tvEnd: // 范围
                showScopeDialog();
                break;
            case R.id.btnConnect: // 连接设备
                if (BluetoothUtils.isOpenBluetooth()) {
                    TgSystemHelper.handleIntent(ConstantActivityPath.BLUETOOTH);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化recyclerView和adapter
     */
    private void initRecyclerAndAdapter() {
        rvRecord.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecordAdapter();
        mAdapter.isFirstOnly(true); // 再次打开不要动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); // 动画
        rvRecord.setAdapter(mAdapter);
        mAdapter.setEmptyView(getEmptyView((ViewGroup) rvRecord.getParent()));
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // 加载更多
            @Override
            public void onLoadMoreRequested() {
                getData(TgConstantGetDataType.LOAD_MORE);
            }
        }, rvRecord);
    }

    /**
     * 显示范围dialog
     */
    private void showScopeDialog() {
        new MaterialDialog.Builder(getContext())
                .title(getString(R.string.main_select_scope_hint))
                .titleGravity(GravityEnum.CENTER)
                .itemsGravity(GravityEnum.CENTER)
                .items(R.array.main_select_scope)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(final MaterialDialog dialog, final View itemView, final int position, final CharSequence text) {
                        configScope(scopeArray[position]);
                    }
                }).show();
    }

    @Override
    public void messageNotificationReceivedHandle(BaseReader baseReader, IMessageNotification msg) {
        if (BluetoothUtils.isOpen() && baseReader.isConnected()) { // 蓝牙开启，设备连接状态
            if (msg instanceof RXD_ReaderElectricQuantity) {
                Message dataArrivedMsg = new Message();
                dataArrivedMsg.what = HomeFragment.HANDLER_RXD_ReaderElectricQuantity;
                // 电量
                ReaderElectricQuantityReceivedInfo receivedMessage = ((RXD_ReaderElectricQuantity) msg).getReceivedMessage();
                int electricQuantityPercent = receivedMessage.getElectricQuantityPercent();
                dataArrivedMsg.obj = getString(R.string.main_title_electricity) + electricQuantityPercent + "%";
                cardOperationHandler.sendMessage(dataArrivedMsg);
            }
        }
    }

    /**
     * 链接设备
     */
    private class ConnectReaderTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgress(getString(R.string.main_connected_title), TgApplication.bluetoothName + getString(R.string.main_connected_content));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Reader reader = readerHolder.createBluetoothReader(TgApplication.bluetoothName, TgApplication.bluetoothAddress);
            reader.setChannelType(BaseReader.ReaderChannelType.RFID_CHANNEL_TYPE); // 选择通道
            return readerHolder.connect();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            dismissLoadingProgress();
            if (result) {
                Toast.makeText(getContext(), getString(R.string.main_connected_successful), Toast.LENGTH_SHORT).show();
                connectedStatus();
                readerHolder.getCurrentReader().onMessageNotificationReceived.add(HomeFragment.this);
            } else {
                Toast.makeText(getContext(), getString(R.string.main_connected_failure), Toast.LENGTH_SHORT).show();
                llConnect.setVisibility(View.VISIBLE);
                rvRecord.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            dismissLoadingProgress();
        }
    }

    /**
     * 设备处于连接状态
     */
    private void connectedStatus() {
        if (readerHolder.isConnected()) { // 如果设备连接
            llConnect.setVisibility(View.GONE);
            rvRecord.setVisibility(View.VISIBLE);
            tvStart.setVisibility(View.VISIBLE);
            tvEnd.setVisibility(View.VISIBLE);
            queryElectric(); // 查询电量
            queryScope(); //  查询功率
            getData(TgConstantGetDataType.INIT);
        } else {
            llConnect.setVisibility(View.VISIBLE);
            rvRecord.setVisibility(View.GONE);
            tvStart.setVisibility(View.GONE);
            tvEnd.setVisibility(View.GONE);
        }
    }

    /**
     * 加载数据
     */
    private void getData(final int getDataType) {
        PostRequest request = TgHttpUtil.requestPost(ConstantUrls.URL_RECORD_FIND, getHttpTaskKey());
        request.params("userId", TgSystemHelper.getId());
        if (TgConstantGetDataType.LOAD_MORE == getDataType) {
            request.params("page", pageSize);
        }
        request.params("limit", limitSize);
        request.execute(new TgHttpCallback(null) {
            @Override
            public void successHandler(final TgResponseBean result) {
                if (result.isSuccess()) {
                    if (result.getData() == null) {
                        return;
                    }
                    List<Record> records = TgJsonUtil.jsonResultBean2List(result, Record.class);
                    if (records != null) {
                        if (TgConstantGetDataType.INIT == getDataType) {
                            pageSize = 2;
                            mAdapter.setNewData(records);
                        } else {
                            pageSize++;
                            // 去重
                            List<Record> recordList = new ArrayList<>();
                            recordList.addAll(records);
                            recordList.removeAll(mAdapter.getData());
                            mAdapter.addData(recordList);
                        }
                        mAdapter.loadMoreComplete(); // 成功获取更多数据
                        if (records.size() < limitSize) {
                            mAdapter.loadMoreEnd(false); // 加载结束，没有数据，false显示没有更多数据，true不显示任何提示信息
                        }
                        if (mAdapter.getItemCount() < limitSize) { // 第一页如果不够一页就不显示没有更多数据布局
                            mAdapter.loadMoreEnd(true);
                        }
                    }
                } else {
                    mAdapter.loadMoreFail(); // 获取更多数据失败，点击重试
                    TgDialogUtil.showToast(result.getMsg()); // 弹出错误信息
                }
            }
        });
    }

    /**
     * 查询电池电量
     */
    private void queryElectric() {
        if (readerHolder.isConnected()) {
            SysQuery_800 sysQuery800 = new SysQuery_800((byte) 135); // 87H
            if (readerHolder.getCurrentReader().send(sysQuery800)) {
                byte[] queryData = sysQuery800.getReceivedMessage().getQueryData();
                if (queryData != null) {
                    tvStart.setText(getString(R.string.main_title_electricity) + queryData[0] + "%");
                }
            }
        }
    }

    /**
     * 查询天线端口功率
     */
    private void queryScope() {
        if (readerHolder.isConnected()) {
            SysQuery_800 sysQuery800 = new SysQuery_800((byte) 101); // 65H
            if (readerHolder.getCurrentReader().send(sysQuery800)) {
                byte[] queryData = sysQuery800.getReceivedMessage().getQueryData();
                if (queryData != null) {
                    tvEnd.setText(getString(R.string.main_title_scope) + queryData[0] + " dBm");
                }
            }
        }
    }

    /**
     * 配置设备功率
     */
    private void configScope(final int scope) {
        if (readerHolder.isConnected()) {
            byte[] arrayOfByte = new byte[3];
            arrayOfByte[0] = 2;
            arrayOfByte[1] = (byte) 255; // 天线号范围0～3，分别表示天线1、2、3、4，当天线端口号为FFH时，则表示将所有天线端口配置成相同的功率等级参数
            arrayOfByte[2] = (byte) scope; // 3,6,9,12,...36
            SysConfig_800 sysConfig800 = new SysConfig_800((byte) 101, arrayOfByte); // 65H
            if (readerHolder.getCurrentReader().send(sysConfig800)) {
                queryScope();
            } else {
                Toast.makeText(getContext(), getString(R.string.main_config_scope_failure), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
