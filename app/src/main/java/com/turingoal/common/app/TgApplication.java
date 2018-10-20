package com.turingoal.common.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.turingoal.laundry.BuildConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * 主应用
 */
public class TgApplication extends Application {
    private static Context context; //context
    private static int screenWidth; // 屏幕宽度
    private static int screenHeight; // 屏幕高度
    private static int dialogWidth; // 对话框宽度
    private static int dialogHeight; // 对话框高度
    private static TgUserPreferences userPreferences; // 用户数据存储
    private static ArrayList<Activity> signAtyList = new ArrayList<>(); // activity堆栈
    public static String bluetoothName; // 蓝牙名字
    public static String bluetoothAddress; // 蓝牙地址
    public static final int TYPE_SEND = 1; // 1收污
    public static final int TYPE_RECEIVE = 2; // 2发净
    public static int type; // 1收污 2发净

    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext(); //context
        Utils.init(this); // 初始化Utils工具类
        appInit(); // 初始化 app
        initLogger(); // 初始化Logger
        initARouter(); // 初始化ARouter
        initOkGo(); // 初始化OKhttp OkGO
    }

    /**
     * 系统初始化配置
     */
    private void appInit() {
        userPreferences = new TgUserPreferences(this); // 初始化 TgUserPreferences
        screenWidth = this.getResources().getDisplayMetrics().widthPixels; // 屏幕宽度
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; // 屏幕高度
        dialogWidth = screenWidth * 9 / 10;  // 对话框宽度
        dialogHeight = screenHeight * 2 / 3; // 对话框高度
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false) // 显示线程信息
                .tag(TgSystemConfig.LOG_TAG)   // LOG_TAG
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {

            @Override
            public boolean isLoggable(final int priority, final String tag) {
                return BuildConfig.DEBUG; // 是否debug模式
            }
        });
    }

    /**
     * 初始化ARouter
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // ARouter打印日志
            ARouter.openDebug();   // ARouter开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 初始化ARouter
    }

    /**
     * 初始化OKhttp OkGo
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(TgSystemConfig.LOG_TAG); //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY); //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);  //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS); //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS); //全局的连接超时时间
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this))); //使用sp保持cookie，如果cookie不过期，则一直有效
        OkGo.getInstance().init(this)                         //必须调用初始化
                .setOkHttpClient(builder.build())             //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)             //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE) //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0;                       //全局公共参数
    }

    /**
     * 添加 Activity 到堆栈
     */
    public static void addActivity(final Activity activity) {
        if (signAtyList != null) {
            boolean contains = signAtyList.contains(activity);
            if (contains) {
                signAtyList.remove(activity);
            }
            signAtyList.add(activity);
        }
    }

    /**
     * 从堆栈删除Activity
     */
    public static void deleteActivity(final Activity activity) {
        if (signAtyList != null) {
            boolean contains = signAtyList.contains(activity);
            if (contains) {
                signAtyList.remove(activity);
            }
        }
    }

    /**
     * 从堆栈finish Activity
     */
    public static void finishActivity(final Class<?> cls) {
        for (Activity activity : signAtyList) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 清空Activity堆栈
     */
    public static void clearActivitys() {
        if (signAtyList != null) {
            final int size = signAtyList.size();
            for (int i = 0; i < size; i++) {
                Activity aty = signAtyList.get(i);
                if (aty != null) {
                    aty.finish();
                }
            }
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        return screenHeight;
    }

    /**
     * 获取对话框宽度
     */
    public static int getDialogWidth() {
        return dialogWidth;
    }

    /**
     * 获取对话框高度
     */
    public static int getDialogHeight() {
        return dialogHeight;
    }

    /**
     * getContext
     */
    public static Context getContext() {
        return context;
    }

    /**
     * getTgUserPreferences
     */
    public static TgUserPreferences getTgUserPreferences() {
        return userPreferences;
    }
}
