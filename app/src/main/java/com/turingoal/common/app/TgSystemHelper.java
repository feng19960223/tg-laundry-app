package com.turingoal.common.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.turingoal.common.base.TgBaseActivity;
import com.turingoal.common.bean.TgResponseBean;
import com.turingoal.common.utils.TgDialogUtil;
import com.turingoal.laundry.R;
import com.turingoal.laundry.constants.ConstantActivityPath;

import java.util.Map;

/**
 * 公用方法
 */
public final class TgSystemHelper {
    private static long exitTime = 0; // 退出系统
    private static final long EXIT_DELAY_TIME = 2000; // 再按一次退出系统，间隔时间

    private TgSystemHelper() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 再按一次退出系统
     */
    public static void dbClickExit(final Context context) {
        if ((System.currentTimeMillis() - exitTime) > EXIT_DELAY_TIME) {
            TgDialogUtil.showToast(TgApplication.getContext().getString(R.string.dbclick_exit));
            exitTime = System.currentTimeMillis();
        } else {
            TgApplication.clearActivitys(); // 清除Activity栈
            System.exit(0);
        }
    }

    /**
     * 处理跳转
     */
    public static void handleIntent(final String path) {
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 处理跳转
     */
    public static void handleIntent(final String path, final Activity mContext, final int requestCode) {
        ARouter.getInstance().build(path).navigation(mContext, requestCode);
    }

    /**
     * 处理跳转
     */
    public static void handleIntent(final String path, final Activity mContext, final int requestCode, final NavigationCallback callback) {
        ARouter.getInstance().build(path).navigation(mContext, requestCode, callback);
    }

    /**
     * 处理跳转,带参数过去
     */
    public static void handleIntentWithObj(final String path, final String objName, final Object obj) {
        ARouter.getInstance().build(path)
                .withObject(objName, obj)
                .navigation();
    }

    /**
     * 处理跳转,带参数过去
     */
    public static void handleIntentWithObj(final String path, final String key, final String value) {
        ARouter.getInstance().build(path)
                .withString(key, value)
                .navigation();
    }

    /**
     * 处理跳转,带参数过去
     */
    public static void handleIntentWithBundle(final String path, final String objName, final Bundle bundle) {
        ARouter.getInstance().build(path)
                .withBundle(objName, bundle)
                .navigation();
    }

    /**
     * 处理跳转,带参数过去
     */
    public static void handleIntentWithObj(final String path, final String objName, final Object obj, final Activity mContext, final int requestCode) {
        ARouter.getInstance().build(path)
                .withObject(objName, obj)
                .navigation(mContext, requestCode);
    }

    /**
     * 处理跳转,带参数过去
     */
    public static void handleIntentWithObj(final String path, final String objName, final Object obj, final Activity mContext, final int requestCode, final NavigationCallback callback) {
        ARouter.getInstance().build(path)
                .withObject(objName, obj)
                .navigation(mContext, requestCode, callback);
    }

    /**
     * 处理跳转，关闭当前页面
     */
    public static void handleIntentAndFinish(final String path, final Context context) {
        ARouter.getInstance().build(path).navigation(context, new NavCallback() {
            @Override
            public void onArrival(final Postcard postcard) {
                ((TgBaseActivity) context).defaultFinish(); // 关闭当前页面
            }
        });
    }

    /**
     * 处理跳转，关闭当前页面， 带参数过去
     */
    public static void handleIntentAndFinishWithObj(final String path, final String objName, final Object obj, final TgBaseActivity context) {
        ARouter.getInstance().build(path).withObject(objName, obj).navigation(context, new NavCallback() {
            @Override
            public void onArrival(final Postcard postcard) {
                context.defaultFinish(); // 关闭当前页面
            }
        });
    }

    /**
     * 获取用户toekn
     */
    public static String getToekn() {
        return TgApplication.getTgUserPreferences().getToken();
    }

    /**
     * 检查token
     */
    public static boolean checkToken(final TgResponseBean result, final Context context) {
        boolean flag = false;
        if (result == null) {
            return false;
        }
        if (result.isTokenValidateResult()) {
            flag = true;
        } else {
            TgDialogUtil.showToast(TgApplication.getContext().getString(R.string.token_expired)); // 弹出错误信息
            if (context != null) {
                logout(context); //注销并跳转到登录页面
            }
        }
        return flag;
    }

    /**
     * 清空用户个人信息
     */
    public static void clearUserInfo() {
        TgApplication.getTgUserPreferences().clear();
    }

    /**
     * 注销
     */
    public static void logout(final Context context) {
        clearUserInfo(); // 清空用户个人信息
        TgApplication.clearActivitys(); // 清空activiti堆栈
        TgSystemHelper.handleIntentAndFinish(ConstantActivityPath.LOGIN, context); // 跳转到登录页面
    }

    /**
     * 获取登录信息
     */
    public static void setUserInfo(final TgResponseBean result) {
        Map<String, Object> map = (Map<String, Object>) result.getData();
        String token = (String) map.get(SharedPreferencesKey.TOKEN);
        TgApplication.getTgUserPreferences().setToken(token);
        String id = (String) map.get(SharedPreferencesKey.ID);
        TgApplication.getTgUserPreferences().setId(id);
        String codeNum = (String) map.get(SharedPreferencesKey.CODE_NUM);
        TgApplication.getTgUserPreferences().setCodeNum(codeNum);
        String userName = (String) map.get(SharedPreferencesKey.USER_NAME);
        TgApplication.getTgUserPreferences().setUsername(userName);
        String realname = (String) map.get(SharedPreferencesKey.REALNAME);
        TgApplication.getTgUserPreferences().setRealname(realname);
        String cellphoneNumber = (String) map.get(SharedPreferencesKey.CELLPHONE_NUM);
        TgApplication.getTgUserPreferences().setCellphoneNum(cellphoneNumber);
        String factoryName = (String) map.get(SharedPreferencesKey.FACTORY_NAME);
        TgApplication.getTgUserPreferences().setFactoryName(factoryName);
        String userJobNo = (String) map.get(SharedPreferencesKey.USER_JOB_NO);
        TgApplication.getTgUserPreferences().setUserJobNo(userJobNo);
    }

    /**
     * 获取用户id
     */
    public static String getId() {
        return TgApplication.getTgUserPreferences().getId();
    }

    /**
     * 获取工号
     */
    public static String getCodeNum() {
        return TgApplication.getTgUserPreferences().getCodeNum();
    }

    /**
     * 获取用户名字
     */
    public static String getUsername() {
        return TgApplication.getTgUserPreferences().getUsername();
    }

    /**
     * 获取用户真实姓名
     */
    public static String getRealname() {
        return TgApplication.getTgUserPreferences().getRealname();
    }

    /**
     * 获取用户电话
     */
    public static String getCellphoneNum() {
        return TgApplication.getTgUserPreferences().getCellphoneNum();
    }

    /**
     * 获取工厂（发送单位）
     */
    public static String getFactoryName() {
        return TgApplication.getTgUserPreferences().getFactoryName();
    }

    /**
     * 获取职位（暂时无用）
     */
    public static String getUserJobNo() {
        return TgApplication.getTgUserPreferences().getUserJobNo();
    }

    /**
     * 获取用户登录时间
     */
    public static long getLoginTime() {
        return TgApplication.getTgUserPreferences().getLoginTime();
    }
}