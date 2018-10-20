package com.turingoal.common.utils;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.turingoal.common.app.TgSystemHelper;
import com.turingoal.common.bean.TgResponseBean;

import okhttp3.ResponseBody;

/**
 * 实现Callback
 */
public class TgHttpCallback implements Callback<TgResponseBean> {
    private Context context;

    public TgHttpCallback(final Context contextParm) {
        this.context = contextParm;
    }

    /**
     * successHandler 需要覆盖该方法
     */
    public void successHandler(final TgResponseBean result) {

    }

    @Override
    public void onSuccess(final Response<TgResponseBean> response) {
        if (context != null) {
            TgDialogUtil.dismissLoadingProgress(); // 隐藏加载提示框
        }
        TgResponseBean result = response.body();
        //检查token
        if (TgSystemHelper.checkToken(result, context)) {
            successHandler(result);
        }
    }

    @Override
    public void onStart(final Request<TgResponseBean, ? extends Request> request) {
        // 进度和提醒
        if (context != null) {
            TgDialogUtil.showLoadingProgress(context); // 显示加载提示框
        }
    }

    @Override
    public void onCacheSuccess(final Response<TgResponseBean> response) {

    }

    @Override
    public void uploadProgress(final Progress progress) {

    }

    @Override
    public void downloadProgress(final Progress progress) {

    }

    @Override
    public void onError(final Response<TgResponseBean> response) {
        String message = response.getException().getMessage();
        TgLogUtil.e(message);
        // 进度和提醒
        if (context != null) {
            TgDialogUtil.dismissLoadingProgress(); // 隐藏加载提示框
            if (!TextUtils.isEmpty(message)) {
                TgDialogUtil.showToast(message);
            }
        }
    }

    @Override
    public void onFinish() {
        // 进度和提醒
        if (context != null) {
            TgDialogUtil.dismissLoadingProgress(); // 隐藏加载提示框
        }
    }

    @Override
    public TgResponseBean convertResponse(final okhttp3.Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        } else {
            TgResponseBean result = TgJsonUtil.json2Object(body.string(), TgResponseBean.class); // body.string()只能执行一次
            return result;
        }
    }
}
