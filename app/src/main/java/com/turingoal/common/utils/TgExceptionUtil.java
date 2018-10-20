package com.turingoal.common.utils;


import com.turingoal.laundry.BuildConfig;

/**
 * 异常信息控制类
 */
public final class TgExceptionUtil {

    private TgExceptionUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 打印出错信息
     */
    public static void printMessage(final Throwable e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }
}
