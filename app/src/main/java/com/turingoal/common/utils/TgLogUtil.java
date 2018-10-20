package com.turingoal.common.utils;

import com.orhanobut.logger.Logger;

/**
 * 输出信息控制类
 */
public final class TgLogUtil {

    private TgLogUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * debug
     */
    public static void d(final String msg) {
        Logger.d(msg);
    }

    /**
     * information
     */
    public static void i(final String msg) {
        Logger.i(msg);
    }

    /**
     * verbose 详细的
     */
    public static void v(final String msg) {
        Logger.v(msg);
    }

    /**
     * warning
     */
    public static void w(final String msg) {
        Logger.w(msg);
    }

    /**
     * error
     */
    public static void e(final String msg) {
        Logger.e(msg);
    }
}
