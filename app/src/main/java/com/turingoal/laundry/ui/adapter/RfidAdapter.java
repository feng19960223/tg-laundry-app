package com.turingoal.laundry.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.turingoal.common.base.TgBaseViewHolder;
import com.turingoal.laundry.DataUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.bean.ScanTotal;

/**
 * 流水单adapter
 */
public class RfidAdapter extends BaseQuickAdapter<ScanTotal, TgBaseViewHolder> {
    public RfidAdapter() {
        super(R.layout.item_rfid);
    }

    @Override
    protected void convert(final TgBaseViewHolder helper, final ScanTotal scanTotal) {
        helper.setText(R.id.tvType, scanTotal.getType())
                .setText(R.id.tvCount, "" + scanTotal.getNum());
    }
}
