package com.turingoal.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * 通知栏、状态栏相关
 */
public class TgBarUtil {
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(@NonNull final Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        TgBarUtil.setStatusBarColor(activity, color);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
     */
    public static void setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setStatusBarColor(activity, color, alpha);
    }


    /**
     * 为背景图设置状态栏透明度
     * <p>适用于图片作为背景的界面,此时需要图片填充到状态栏</p>
     *
     * @param activity activity
     */
    public static void setStatusBar4Bg(@NonNull final Activity activity) {
        TgBarUtil.setStatusBar4Bg(activity);
    }

    /**
     * 为背景图设置状态栏透明度
     * <p>适用于图片作为背景的界面,此时需要图片填充到状态栏</p>
     *
     * @param activity activity
     * @param alpha    状态栏透明度
     */
    public static void setStatusBar4Bg(@NonNull final Activity activity,
                                       @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setStatusBar4Bg(activity, alpha);
    }

    /**
     * 为头部ImageView设置状态栏透明度
     * <p>如果</p>
     *
     * @param activity       activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setStatusBar4ImageView(@NonNull final Activity activity,
                                              @Nullable final View needOffsetView) {
        TgBarUtil.setStatusBar4ImageView(activity, needOffsetView);
    }

    /**
     * 为头部ImageView设置状态栏透明度
     *
     * @param activity       activity
     * @param alpha          状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setStatusBar4ImageView(@NonNull final Activity activity,
                                              @IntRange(from = 0, to = 255) final int alpha,
                                              @Nullable final View needOffsetView) {
        TgBarUtil.setStatusBar4ImageView(activity, alpha, needOffsetView);
    }

    /**
     * 为fragment头部是ImageView设置状态栏透明度
     *
     * @param activity       activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
                                                        @Nullable final View needOffsetView) {
        TgBarUtil.setStatusBar4ImageViewInFragment(activity, needOffsetView);
    }

    /**
     * 为fragment头部是ImageView设置状态栏透明度
     *
     * @param activity       activity
     * @param alpha          状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setStatusBar4ImageViewInFragment(@NonNull final Activity activity,
                                                        @IntRange(from = 0, to = 255) final int alpha,
                                                        @Nullable final View needOffsetView) {
        TgBarUtil.setStatusBar4ImageViewInFragment(activity, alpha, needOffsetView);
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColorForSwipeBack(@NonNull final Activity activity, final int color) {
        TgBarUtil.setColorForSwipeBack(activity, color);
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度
     */
    public static void setColorForSwipeBack(@NonNull final Activity activity,
                                            @ColorInt final int color,
                                            @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setColorForSwipeBack(activity, color, alpha);
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     * <p>
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     * @param alpha    状态栏透明度
     */
    public static void setTranslucentForCoordinatorLayout(@NonNull final Activity activity,
                                                          @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setTranslucentForCoordinatorLayout(activity, alpha);
    }


    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorForDrawerLayout(@NonNull final Activity activity,
                                               @NonNull final DrawerLayout drawerLayout,
                                               @ColorInt final int color) {
        TgBarUtil.setColorForDrawerLayout(activity, drawerLayout, color);
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorNoTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                            @NonNull final DrawerLayout drawerLayout,
                                                            @ColorInt final int color) {
        TgBarUtil.setColorNoTranslucentForDrawerLayout(activity, drawerLayout, color);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     * @param alpha        状态栏透明度
     */
    public static void setColorForDrawerLayout(@NonNull final Activity activity,
                                               @NonNull final DrawerLayout drawerLayout,
                                               final @ColorInt int color,
                                               @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setColorForDrawerLayout(activity, drawerLayout, color, alpha);
    }


    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout) {
        TgBarUtil.setTranslucentForDrawerLayout(activity, drawerLayout);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout,
                                                     @IntRange(from = 0, to = 255) final int alpha) {
        TgBarUtil.setTranslucentForDrawerLayout(activity, drawerLayout, alpha);
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTransparentForDrawerLayout(@NonNull final Activity activity,
                                                     @NonNull final DrawerLayout drawerLayout) {
        TgBarUtil.setTransparentForDrawerLayout(activity, drawerLayout);
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    public static void hideFakeStatusBarView(@NonNull final Activity activity) {
        TgBarUtil.hideFakeStatusBarView(activity);
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /*--------------------------------old--------------------------------*/

    /**
     * 设置透明状态栏（api大于19方可使用）
     * <p>可在Activity的onCreat()中调用</p>
     * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下</p>
     * <p>android:clipToPadding="true"</p>
     * <p>android:fitsSystemWindows="true"</p>
     *
     * @param activity activity
     */
    public static void setTransparentStatusBar(@NonNull final Activity activity) {
        TgBarUtil.setTransparentStatusBar(activity);
    }

    /**
     * 隐藏状态栏
     * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错</p>
     * <p>此方法Activity可以继承AppCompatActivity</p>
     * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面</p>
     * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"</p>
     * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错</p>
     *
     * @param activity activity
     */
    public static void hideStatusBar(@NonNull final Activity activity) {
        TgBarUtil.hideStatusBar(activity);
    }

    /**
     * 判断状态栏是否存在
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isStatusBarExists(@NonNull final Activity activity) {
        return TgBarUtil.isStatusBarExists(activity);
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(@NonNull final Activity activity) {
        return TgBarUtil.getActionBarHeight(activity);
    }

    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    public static void showNotificationBar(@NonNull final Context context, final boolean isSettingPanel) {
        TgBarUtil.showNotificationBar(context, isSettingPanel);
    }

    /**
     * 隐藏通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(@NonNull final Context context) {
        TgBarUtil.hideNotificationBar(context);
    }
}
