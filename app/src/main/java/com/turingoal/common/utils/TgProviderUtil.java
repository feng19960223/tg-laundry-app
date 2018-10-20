package com.turingoal.common.utils;

import android.content.Context;

/**
 * 用于解决provider冲突的util, 读写sd卡需要用
 */
public final class TgProviderUtil {
    private TgProviderUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * getFileProviderName
     */
    public static String getFileProviderName(final Context context) {
        return context.getPackageName() + ".provider";
    }
}
