package com.turingoal.laundry.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.utils.BluetoothUtils;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.ui.adapter.MyBluetoothAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 蓝牙
 */
@Route(path = ConstantActivityPath.BLUETOOTH)
public class BluetoothActivity extends TgBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvBluetooth)
    RecyclerView rvBluetooth;
    private MyBluetoothAdapter mAdapter; // 蓝牙设备C

    @Override
    protected int getLayoutID() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.activity_bluetooth);
        initRecyclerAndAdapter();
        mAdapter.setNewData(BluetoothUtils.getBluetoothDeviceList()); // 打开蓝牙，并且得到设备
    }

    /**
     * 初始化recyclerView和adapter
     */
    private void initRecyclerAndAdapter() {
        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyBluetoothAdapter();
        mAdapter.isFirstOnly(true); // 再次打开不要动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM); // 动画
        rvBluetooth.setAdapter(mAdapter);
        mAdapter.setEmptyView(getEmptyView((ViewGroup) rvBluetooth.getParent()));
    }

    /**
     * OnClick
     */
    @OnClick(R.id.tvStart)
    public void onViewClicked() {
        defaultFinish();
    }
}
