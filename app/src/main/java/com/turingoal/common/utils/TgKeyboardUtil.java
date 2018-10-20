package com.turingoal.common.utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;

/**
 * 键盘工具类
 */
public class TgKeyboardUtil {
    private TgKeyboardUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 动态显示软键盘
     */
    public static void showSoftInput(final Activity activity) {
        KeyboardUtils.showSoftInput(activity);
    }

    /**
     * 动态显示软键盘
     */
    public static void showSoftInput(final View view) {
        KeyboardUtils.showSoftInput(view);
    }

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(final Activity activity) {
        KeyboardUtils.hideSoftInput(activity);
    }

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(final View view) {
        KeyboardUtils.hideSoftInput(view);
    }

    /**
     * 切换键盘显示与否状态
     */
    public static void toggleSoftInput() {
        KeyboardUtils.toggleSoftInput();
    }

    /**
     * 点击其他位置是否将要隐藏输入键盘(editview)
     */
    public static boolean isShouldHideInput(final View v, final MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            final int left = leftTop[0];
            final int top = leftTop[1];
            final int right = left + v.getWidth();
            final int bottom = top + v.getHeight();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
