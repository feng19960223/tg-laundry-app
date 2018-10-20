package com.turingoal.common.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.DeviceUtils;

/**
 * 设备工具类
 */
public final class TgDeviceUtil {
    private TgDeviceUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRooted() {
        return DeviceUtils.isDeviceRooted();
    }


    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress() {
        return DeviceUtils.getMacAddress();
    }


    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return DeviceUtils.getManufacturer();
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        return DeviceUtils.getModel();
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    public static String getAndroidID() {
        return DeviceUtils.getAndroidID();
    }

    /**
     * 获取设备id
     */
    public static String getDeviceId(final Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 唯一的用户ID：
     * 例如：IMSI(国际移动用户识别码) for a GSM phone.
     * 需要权限：READ_PHONE_STATE
     */
    public static String getSubscriberId(final Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }
}