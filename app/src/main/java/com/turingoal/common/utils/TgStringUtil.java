package com.turingoal.common.utils;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * String工具类
 */
public final class TgStringUtil {
    private TgStringUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 判断String是否为空
     */
    public static boolean isEmpty(final CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        if ("null".equals(str.toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 如果judgeStr不空返回judgeStr，否则返回emptyStr
     */
    public static String getSelectStr(final CharSequence judgeStr, final CharSequence emptyStr) {
        if (isEmpty(judgeStr)) { // 如果judgeStr空
            return getStr(emptyStr);
        } else {
            return getStr(judgeStr);
        }
    }

    /**
     * 得到字符串
     */
    public static String getStr(final CharSequence str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str.toString().trim();
        }
    }

    /**
     * 设置字符串
     */
    public static void setStr(final CharSequence str, final TextView textView) {
        if (textView != null) {
            textView.setText(getStr(str));
        }
    }
}