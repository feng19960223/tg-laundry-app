package com.turingoal.common.app;

import android.os.Environment;

import com.turingoal.laundry.BuildConfig;

/**
 * 系统配置
 */
public final class TgSystemConfig {
    public static final int WELCOME_DELAY_TIME = 2000; // 欢迎界面延迟时间
    public static final String SERVER_PROTOCOL = "http"; // server 协议 http，https
    public static final String SERVER_IP_DEBUG = "192.168.1.111"; // 测试 server ip
    public static final String SERVER_IP = "123.56.195.193"; // 上线 server ip
    public static final Integer SERVER_PORT = 8080; // server port
    public static final String SERVER_NAME = "tg-laundry"; // server name
    public static final String LOG_TAG = "tg-laundry"; // log tag
    public static final String SP_NAME = "tg-laundry_data"; // SharedPreferences名称
    public static final String DB_NAME = "ttg-laundry.db"; // 数据库名称
    public static final String BASE_STORE_PATH = Environment.getExternalStorageDirectory() + "/bts/rfid/"; // 基本保存路径
    public static final String IMG_STORE_PATH = BASE_STORE_PATH + "imgs/"; // 基本保存路径,图片

    private TgSystemConfig() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 获取服务器基础路径
     */
    public static String getServerBaseUrl() {
        String serverIp = BuildConfig.DEBUG ? SERVER_IP_DEBUG : SERVER_IP;
        return SERVER_PROTOCOL + "://" + serverIp + ":" + SERVER_PORT + "/" + SERVER_NAME;
    }
}
