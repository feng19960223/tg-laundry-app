package com.turingoal.common.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.RegexUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类
 */
public final class TgValidateUtil {

    private TgValidateUtil() {
        throw new Error("工具类不能实例化！");
    }

    public static final Integer MIN_LENGTH = 6;
    public static final Integer MAX_PORT = 65535;

    /**
     * 数字—字母-下划线
     */
    public static boolean isGereralCharacters(final String str) {
        String r = "^[a-zA-Z0-9_]+";
        Pattern p = Pattern.compile(r);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 校验用户名和密码
     */
    public static boolean validateUsernameAndPass(final String username, final String userPass) {
        boolean result = false;
        if (TextUtils.isEmpty(username)) {
            TgDialogUtil.showToast("请输入账号！");
        } else if (TextUtils.isEmpty(userPass)) {
            TgDialogUtil.showToast("请输入密码！");
        } else if (username.length() < 3 || username.length() > 25) {
            TgDialogUtil.showToast("用户名为3到25个字符！");
        } else if (userPass.length() < MIN_LENGTH || userPass.length() > 25) {
            TgDialogUtil.showToast("密码为6到25个字符！");
        } else if (!TgValidateUtil.isGereralCharacters(username) || !TgValidateUtil.isGereralCharacters(userPass)) {
            TgDialogUtil.showToast("账号或密码只能包含数字字母下划线！");
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 校验密码和确认密码
     */
    public static boolean validatePassword(final String oldPassword, final String newPassword, final String confirmPassword) {
        boolean result = false;
        if (TextUtils.isEmpty(oldPassword)) {
            TgDialogUtil.showToast("请输入原密码");
        } else if (TextUtils.isEmpty(newPassword)) {
            TgDialogUtil.showToast("请输入新密码");
        } else if (TextUtils.isEmpty(confirmPassword)) {
            TgDialogUtil.showToast("请输入确认密码");
        } else if (oldPassword.length() < MIN_LENGTH || oldPassword.length() > 25) {
            TgDialogUtil.showToast("原密码为6到25个字符！");
        } else if (newPassword.length() < MIN_LENGTH || newPassword.length() > 25) {
            TgDialogUtil.showToast("用新密码为6到25个字符！");
        } else if (confirmPassword.length() < MIN_LENGTH || confirmPassword.length() > 25) {
            TgDialogUtil.showToast("确认密码为6到25个字符！");
        } else if (!newPassword.equals(confirmPassword)) {
            TgDialogUtil.showToast("两次输入的密码不一致");
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 校验ip
     */
    public static boolean validateIp(final String ipStr) {
        boolean result = false;
        if (TextUtils.isEmpty(ipStr)) {
            TgDialogUtil.showToast("IP地址不能为空！");
        } else {
            if (!RegexUtils.isIP(ipStr)) {
                TgDialogUtil.showToast("请输入正确的ip格式！");
            }
        }
        return result;
    }

    /**
     * 校验端口
     */
    public static boolean validatePort(final String portStr) {
        boolean result = false;
        if (TextUtils.isEmpty(portStr)) {
            TgDialogUtil.showToast("端口不能为空！");
        } else {
            int port = Integer.parseInt(portStr);
            if (port < 0 || port > MAX_PORT) {
                TgDialogUtil.showToast("端口范围为0到" + MAX_PORT + "！");
            } else {
                result = true;
            }
        }
        return result;
    }
}