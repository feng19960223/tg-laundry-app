package com.turingoal.common.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.turingoal.common.app.TgApplication;
import com.turingoal.laundry.R;

/**
 * 工具类-》dialog
 */
public final class TgDialogUtil {

    private TgDialogUtil() {
        throw new Error("工具类不能实例化！");
    }

    private static MaterialDialog dialog; // 加载框

    /**
     * 显示加载进度条
     */
    public static void showLoadingProgress(final Context context, final String title, final String content) {
        if (dialog == null) {
            dialog = new MaterialDialog.Builder(context).progress(true, 0).progressIndeterminateStyle(false).theme(Theme.LIGHT).build();
            if (TextUtils.isEmpty(title)) {
                dialog.setTitle(title);
            } else {
                dialog.setTitle("处理中");
            }
            if (TextUtils.isEmpty(content)) {
                dialog.setContent("请等待...");
            } else {
                dialog.setContent(content);
            }
        }
        dialog.setCanceledOnTouchOutside(false); // 按对话框以外的地方不起作用。按返回键还起作用
        // dialog.setCancelable(false); // 按对话框以外的地方不起作用。按返回键也不起作用
        dialog.show();
    }

    /**
     * 显示加载进度条
     */
    public static void showLoadingProgress(final Context context) {
        showLoadingProgress(context, null, null);
    }

    /**
     * 隐藏加载进度条
     */
    public static void dismissLoadingProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭加载进度条
     */
    public static void closeLoadingProgress() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 开启确认对话框
     */
    public static void showConfirmDialog(final Context context, final String title, final String content, final MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                // .icon(TgApplication.getContext().getResources().getDrawable(R.mipmap.common_ic_confirm))
                .iconRes(R.mipmap.common_ic_confirm)
                .onPositive(callback).show();
    }

    /**
     * 开启警告确认对话框
     */
    public static void showWarningDialog(final Context context, final String title, final String content, final MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .iconRes(R.mipmap.common_ic_warning)
                // .icon(TgApplication.getContext().getResources().getDrawable(R.mipmap.common_ic_warning))
                .onPositive(callback).show();
    }

    /**
     * showToast
     */
    public static void showToast(final String msg) {
        showToast(msg, false);
    }

    /**
     * showToast
     */
    public static void showToast(final String msg, final boolean isLong) {
        if (isLong) {
            Toast.makeText(TgApplication.getContext(), msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(TgApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showMultiDialog(final Context context, final String title, final String content, final String[] strings, MaterialDialog.SingleButtonCallback callback) {
        if (strings == null || strings.length < 1) {
            return;
        }
        new MaterialDialog.Builder(context)
                .title(title)
                .iconRes(R.mipmap.common_ic_confirm)
                .content(content)
                .items(strings)
                .positiveText("确定")
                .widgetColor(Color.RED)//改变checkbox的颜色
                //多选框添加
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        return true;//false 的时候没有选中样式
                    }
                })
                .onPositive(callback)
                .show();
    }
}
