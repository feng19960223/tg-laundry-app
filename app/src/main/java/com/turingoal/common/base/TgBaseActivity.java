package com.turingoal.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.okgo.OkGo;
import com.turingoal.common.app.TgApplication;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.common.utils.TgExceptionUtil;
import com.turingoal.common.utils.TgKeyboardUtil;
import com.turingoal.laundry.R;

import butterknife.ButterKnife;

/**
 * 基类Activity
 */
public abstract class TgBaseActivity extends AppCompatActivity {
    private String httpTaskKey = "HttpTaskKey_" + hashCode(); // HttpTaskKey
    private View emptyView; // 数据没有view
    private View errorView; // 数据出错view

    /**
     * 设置布局界面的ID
     */
    protected abstract int getLayoutID();

    /**
     * 初始化组件/数据
     */
    protected abstract void initialized();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutID = getLayoutID(); //layoutId
        if (layoutID != 0) {
            setContentView(layoutID);
            getWindow().setBackgroundDrawable(null);
        }
        TgApplication.addActivity(this);
        ButterKnife.bind(this); // ButterKnife
        ARouter.getInstance().inject(this); // ARouter自动解析参数，每个Activity里不用再写
        initialized();
    }

    @Override
    public void onStop() {
        super.onStop();
        TgDialogUtil.closeLoadingProgress(); //关闭进度条
    }

    /**
     * 显示加载进度条
     */
    public void showLoadingProgress() {
        TgDialogUtil.showLoadingProgress(this);
    }

    /**
     * 显示加载进度条
     */
    public void showLoadingProgress(final String titile, final String content) {
        TgDialogUtil.showLoadingProgress(this, titile, content);
    }

    /**
     * 隐藏加载进度条
     */
    public void dismissLoadingProgress() {
        TgDialogUtil.dismissLoadingProgress();
    }

    /**
     * 默认finish
     */
    public void defaultFinish() {
        TgDialogUtil.closeLoadingProgress(); //关闭进度条
        finish();
    }

    /**
     * HttpTaskKey
     */
    public String getHttpTaskKey() {
        return httpTaskKey;
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TgApplication.deleteActivity(this); // 从堆栈删除当前Activity
        OkGo.getInstance().cancelTag(httpTaskKey); // 取消http请求
    }

    /**
     * 触摸事件
     */
    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        try {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (TgKeyboardUtil.isShouldHideInput(v, ev)) {
                    TgKeyboardUtil.hideSoftInput(this);
                }
                return super.dispatchTouchEvent(ev);
            }
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            TgExceptionUtil.printMessage(e);
        }
        return onTouchEvent(ev);
    }


    /**
     * 没有数据view
     */
    protected View getEmptyView(final ViewGroup viewGroup) {
        if (emptyView == null) {
            emptyView = getLayoutInflater().inflate(R.layout.common_view_empty, viewGroup, false);
        }
        return emptyView;
    }

    /**
     * 加载数据出错view
     */
    protected View getErrorView(final ViewGroup viewGroup) {
        if (errorView == null) {
            errorView = getLayoutInflater().inflate(R.layout.common_view_error, viewGroup, false);
        }
        return errorView;
    }
}
