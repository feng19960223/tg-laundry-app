package com.turingoal.laundry.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseFragment;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我Fragment
 */

public class UserFragment extends TgBaseFragment {
    @BindView(R.id.tvTitle)
    TextView tvTitle; // 标题
    @BindView(R.id.tvName)
    TextView tvName; // 名字
    @BindView(R.id.tvWorkId)
    TextView tvWorkId; // 工号
    @BindView(R.id.tvPhone)
    TextView tvPhone; // 电话
    @BindView(R.id.tvAbout)
    TextView tvAbout; // 关于我们
    @BindView(R.id.tvQuit)
    TextView tvQuit; // 退出

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.fun_user);
        tvName.setText(TgSystemHelper.getRealname());
        tvWorkId.setText(TgSystemHelper.getCodeNum());
        tvPhone.setText(TgSystemHelper.getCellphoneNum());
    }

    /**
     * OnClick
     */
    @OnClick({R.id.tvAbout, R.id.tvQuit})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tvAbout:
                TgSystemHelper.handleIntent(ConstantActivityPath.ABOUT); // 跳转到关于我们页面
                break;
            case R.id.tvQuit:
                TgSystemHelper.logout((MainActivity)getContext()); // 注销退出系统
                break;
            default:
                break;
        }
    }
}
