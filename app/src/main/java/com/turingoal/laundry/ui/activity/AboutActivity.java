package com.turingoal.laundry.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.utils.TgSystemUtil;
import com.turingoal.laundry.BuildConfig;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于
 */
@Route(path = ConstantActivityPath.ABOUT)
public class AboutActivity extends TgBaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAppNameAndVersion)
    TextView tvAppNameAndVersion; // 应用名称+版本号

    @Override
    protected int getLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initialized() {
        tvTitle.setText(R.string.activity_about);
        tvAppNameAndVersion.setText("" + getText(R.string.app_name) + BuildConfig.VERSION_NAME); // 应用名称+版本号
    }

    /**
     * OnClick
     */
    @OnClick({R.id.tvStart, R.id.rlWeb, R.id.rlTelephone, R.id.rlMail, R.id.rlWeibo})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.tvStart: // 返回
                defaultFinish();
                break;
            case R.id.rlWeb: // 网页
                TgSystemUtil.openWebSite(getString(R.string.company_web_content)); //打开网址
                break;
            case R.id.rlTelephone: //电话
                TgSystemUtil.call(getString(R.string.company_telephone_content)); //拨打电话号码
                break;
            case R.id.rlMail: // 邮箱
                TgSystemUtil.mail(this, getString(R.string.company_mail_content)); // 发送邮件
                break;
            case R.id.rlWeibo: // 微博
                TgSystemUtil.openWebSite(getString(R.string.company_weibo_content)); //打开网址
                break;
            default:
                break;
        }
    }
}
