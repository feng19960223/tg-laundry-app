package com.turingoal.laundry.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.base.TgBaseViewHolder;
import com.turingoal.laundry.R;
import com.turingoal.laundry.ui.activity.BluetoothActivity;

/**
 * 流水单adapter
 */

public class MyBluetoothAdapter extends BaseQuickAdapter<BluetoothDevice, TgBaseViewHolder> {
    public MyBluetoothAdapter() {
        super(R.layout.item_bluetooth);
    }

    @Override
    protected void convert(final TgBaseViewHolder helper, final BluetoothDevice bluetoothDevice) {
        helper.setText(R.id.tvName, bluetoothDevice.getName());
        // item点击事件
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                TgApplication.bluetoothName = bluetoothDevice.getName();
                TgApplication.bluetoothAddress = bluetoothDevice.getAddress();
                ((BluetoothActivity) mContext).defaultFinish();
            }
        });
    }
}
