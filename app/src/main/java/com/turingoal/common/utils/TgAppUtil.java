package com.turingoal.common.utils;

import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import com.blankj.utilcode.util.AppUtils;

/**
 * App工具类
 */
public final class TgAppUtil {
    private TgAppUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @return 当前应用的AppInfo
     */
    public static AppUtils.AppInfo getAppInfo() {
        return AppUtils.getAppInfo();
    }

    /**
     * 获取App名称
     *
     * @return App名称
     */
    public static String getAppName() {
        return AppUtils.getAppName();
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        return AppUtils.getAppVersionName();
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode() {
        return AppUtils.getAppVersionCode();
    }

    /**
     * 获取App包名
     *
     * @return App包名
     */
    public static String getAppPackageName() {
        return AppUtils.getAppPackageName();
    }

    /**
     * 获取App图标
     *
     * @return App图标
     */
    public static Drawable getAppIcon() {
        return AppUtils.getAppIcon();
    }

    /**
     * 获取App签名
     *
     * @return App签名
     */
    public static Signature[] getAppSignature() {
        return AppUtils.getAppSignature();
    }

    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        return AppUtils.isAppForeground();
    }
}