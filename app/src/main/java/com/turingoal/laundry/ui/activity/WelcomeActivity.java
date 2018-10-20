package com.turingoal.laundry.ui.activity;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.lzy.okgo.request.PostRequest;
import com.turingoal.common.app.TgSystemConfig;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.utils.BluetoothUtils;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgHttpCallback;
import com.turingoal.common.utils.TgHttpUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;
import com.turingoal.laundry.constants.ConstantUrls;

/**
 * 欢迎页面
 */

public class WelcomeActivity extends TgBaseActivity {
    @Override
    protected int getLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initialized() {
        BluetoothUtils.openBluetooth(); // 开蓝牙
        if (DateUtils.isToday(TgSystemHelper.getLoginTime())) { // 如果上次登录时间是今天
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String token = TgSystemHelper.getToekn(); // 获取token
                    if (TextUtils.isEmpty(token)) {
                        TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.LOGIN, WelcomeActivity.this); //跳转到登录页面,关闭当前页面
                    } else { // 有token
                        checkToken(); // 检查token
                    }
                }
            }, TgSystemConfig.WELCOME_DELAY_TIME); //设置延迟，再进入正式界面
        } else {
            TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.LOGIN, this); // 跳转到登录页面
        }
    }

    /**
     * 检查token
     */
    void checkToken() {
        PostRequest request = TgHttpUtil.requestPost(ConstantUrls.URL_TOKEN_CHECK, getHttpTaskKey());
        request.execute(new TgHttpCallback(null) {
            @Override
            public void successHandler(final TgResponseBean result) {
                if (result.isSuccess()) {
                    // 成功
                    TgSystemHelper.setUserInfo(result);
                    TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.MAIN, WelcomeActivity.this); // 跳转到主页面,关闭当前页面
                } else {
                    TgDialogUtil.showToast(result.getMsg()); // 登录失败，弹出错误信息
                    TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.LOGIN, WelcomeActivity.this);  // 跳转到登录页面,关闭当前页面
                }
            }
        });
    }
}
