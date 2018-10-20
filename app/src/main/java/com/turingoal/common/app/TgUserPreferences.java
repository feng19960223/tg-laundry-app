package com.turingoal.common.app;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 用户数据_参数保存服务
 */

public class TgUserPreferences {
    private SharedPreferences sharedPreferences;

    public TgUserPreferences(final Context context) {
        sharedPreferences = context.getSharedPreferences(TgSystemConfig.SP_NAME, Context.MODE_PRIVATE); //name 在TgSystemConfig中统一配置
    }

    /**
     * 清空信息
     */
    public void clear() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * 设置token
     */
    public void setToken(final String token) {
        sharedPreferences.edit().putString(SharedPreferencesKey.TOKEN, token).apply();
    }

    /**
     * 获取token
     */
    public String getToken() {
        return sharedPreferences.getString(SharedPreferencesKey.TOKEN, "");
    }

    /**
     * 设置用户id
     */
    public void setId(final String id) {
        sharedPreferences.edit().putString(SharedPreferencesKey.ID, id).apply();
    }

    /**
     * 获取用户id
     */
    public String getId() {
        return sharedPreferences.getString(SharedPreferencesKey.ID, "");
    }

    /**
     * 设置工号
     */
    public void setCodeNum(final String codeNum) {
        sharedPreferences.edit().putString(SharedPreferencesKey.CODE_NUM, codeNum).apply();
    }

    /**
     * 获取工号
     */
    public String getCodeNum() {
        return sharedPreferences.getString(SharedPreferencesKey.CODE_NUM, "");
    }

    /**
     * 设置用户名字
     */
    public void setUsername(final String username) {
        sharedPreferences.edit().putString(SharedPreferencesKey.USER_NAME, username).apply();
    }

    /**
     * 获取用户名字
     */
    public String getUsername() {
        return sharedPreferences.getString(SharedPreferencesKey.USER_NAME, "");
    }

    /**
     * 设置用户真实姓名
     */
    public void setRealname(final String realname) {
        sharedPreferences.edit().putString(SharedPreferencesKey.REALNAME, realname).apply();
    }

    /**
     * 获取用户真实姓名
     */
    public String getRealname() {
        return sharedPreferences.getString(SharedPreferencesKey.REALNAME, "");
    }

    /**
     * 设置用户电话
     */
    public void setCellphoneNum(final String cellphoneNum) {
        sharedPreferences.edit().putString(SharedPreferencesKey.CELLPHONE_NUM, cellphoneNum).apply();
    }

    /**
     * 获取用户电话
     */
    public String getCellphoneNum() {
        return sharedPreferences.getString(SharedPreferencesKey.CELLPHONE_NUM, "");
    }

    /**
     * 设置工厂（发送单位）
     */
    public void setFactoryName(final String cellphoneNum) {
        sharedPreferences.edit().putString(SharedPreferencesKey.FACTORY_NAME, cellphoneNum).apply();
    }

    /**
     * 获取工厂（发送单位）
     */
    public String getFactoryName() {
        return sharedPreferences.getString(SharedPreferencesKey.FACTORY_NAME, "");
    }

    /**
     * 设置职位（暂时无用）
     */
    public void setUserJobNo(final String userJobNo) {
        sharedPreferences.edit().putString(SharedPreferencesKey.USER_JOB_NO, userJobNo).apply();
    }

    /**
     * 获取职位（暂时无用）
     */
    public String getUserJobNo() {
        return sharedPreferences.getString(SharedPreferencesKey.USER_JOB_NO, "");
    }

    /**
     * 设置用户登录时间
     */
    public void setLoginTime(final long loginTime) {
        sharedPreferences.edit().putLong(SharedPreferencesKey.USER_LOGIN_TIME, loginTime).apply();
    }

    /**
     * 获取用户登录时间
     */
    public long getLoginTime() {
        return sharedPreferences.getLong(SharedPreferencesKey.USER_LOGIN_TIME, 0);
    }
}

interface SharedPreferencesKey {
    // 系统
    String TOKEN = "token";
    // 用户
    String ID = "id"; // 用户id
    String CODE_NUM = "codeNum"; // 工号
    String USER_NAME = "username"; // 用户名字
    String REALNAME = "realname"; // 用户真实姓名
    String CELLPHONE_NUM = "cellphoneNum"; // 用户电话
    String FACTORY_NAME = "factoryName"; // 工厂（发送单位）
    String USER_JOB_NO = "userJobNo"; // 职位（暂时无用）
    String USER_LOGIN_TIME = "loginTime"; // 用户上次登录时间
}
