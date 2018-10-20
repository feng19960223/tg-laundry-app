package com.turingoal.laundry.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.utils.QrUtils;
import com.turingoal.common.utils.TgDateUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.bean.Record;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.ui.adapter.RfidAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 流水详情
 */
@Route(path = ConstantActivityPath.RECORD)
public class RecordActivity extends TgBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle; // title
    @BindView(R.id.tvRecordNum)
    TextView tvRecordNum; // 流水单号
    @BindView(R.id.tvSendUnit)
    TextView tvSendUnit; // 发送单位
    @BindView(R.id.tvReceiveUnit)
    TextView tvReceiveUnit; // 承接单位
    @BindView(R.id.tvWorkUser)
    TextView tvWorkUser; // 操作员
    @BindView(R.id.tvTime)
    TextView tvTime; // 扫描时间
    @BindView(R.id.ivQr)
    ImageView ivQr; // 二维码
    @BindView(R.id.tvStatus)
    TextView tvStatus; // 状态
    @BindView(R.id.tvTotalNumber)
    TextView tvTotalNumber; // 总计
    @BindView(R.id.rvRfid)
    RecyclerView rvRfid; // 扫描结果V
    private RfidAdapter mAdapter;  // 扫描结果C
    @Autowired
    Record record;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_record;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.activity_record);
        initRecyclerAndAdapter();
        tvRecordNum.setText(getText(R.string.rfid_record_num) + record.getCodeNum());
        tvSendUnit.setText(getText(R.string.rfid_sendUnit) + record.getSendUnit());
        tvReceiveUnit.setText(getText(R.string.rfid_receiveUnit) + record.getReceiveUnit());
        tvWorkUser.setText(getText(R.string.rfid_workUser) + record.getUserName() + " " + record.getUserPhone());
        tvTime.setText(getText(R.string.rfid_time) + TgDateUtil.date2String(record.getCreateTime(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_ZH));
        ivQr.setImageBitmap(QrUtils.createQRBitmap(this, 0, record.getCodeNum()));
        tvStatus.setText(getStatusStr(record.getStatus()));
        mAdapter.setNewData(record.getScanTotals());
        tvTotalNumber.setText(String.format(getString(R.string.rfid_total_number), record.getTotal()));
    }

    /**
     * 根据不同状态显示不同的状态字符串
     */
    private String getStatusStr(final String status) {
        if (status.equals("1")) {
            return getString(R.string.record_adapter_status1);
        } else if (status.equals("2")) {
            return getString(R.string.record_adapter_status2);
        } else {
            return getString(R.string.record_adapter_status3);
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

    @OnClick(R.id.tvStart)
    public void onViewClicked() {
        defaultFinish();
    }
}
