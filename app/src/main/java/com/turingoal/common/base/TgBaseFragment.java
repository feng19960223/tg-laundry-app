package com.turingoal.common.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzy.okgo.OkGo;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.laundry.R;

import butterknife.ButterKnife;

/**
 * 基类Fragment
 */
public abstract class TgBaseFragment extends Fragment {
    private View rootView; // 视图
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutID(), container, false);
            ButterKnife.bind(this, rootView);
            ARouter.getInstance().inject(this);
            initialized();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    /**
     * 显示加载进度条
     */
    public void showLoadingProgress(final String titile, final String content) {
        TgDialogUtil.showLoadingProgress(this.getContext(), titile, content);
    }

    @Override
    public void onStop() {
        super.onStop();
        TgDialogUtil.closeLoadingProgress(); //关闭进度条
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
        getActivity().finish();
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
    public void onDestroyView() {
        super.onDestroyView();
        OkGo.getInstance().cancelTag(httpTaskKey); // 取消http请求
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
