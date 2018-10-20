package com.turingoal.laundry.ui.activity;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备配置页面，主页面点击电量进入
 */
@Route(path = ConstantActivityPath.ELECTRICITY)
public class ElectricityActivity extends TgBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_electricity;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.activity_electricity);
    }

    /**
     * OnClick
     */
    @OnClick(R.id.tvStart)
    public void onViewClicked() {
        defaultFinish();
    }
}
