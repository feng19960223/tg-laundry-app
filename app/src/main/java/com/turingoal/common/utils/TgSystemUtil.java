package com.turingoal.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.turingoal.laundry.R;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * 系统工具类
 */
public final class TgSystemUtil {
    private TgSystemUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 打开网址
     */
    public static void openWebSite(final String webSite) {
        if (!TextUtils.isEmpty(webSite)) {
            Uri uri;
            if (webSite.startsWith("http://") || webSite.startsWith("https://")) {
                uri = Uri.parse(webSite);
            } else {
                uri = Uri.parse("http://" + webSite);
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    /**
     * 拨打电话号码
     */
    public static void call(final String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        }
    }

    /**
     * 发送短信
     */
    public static void sms(final String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("smsto:" + phoneNum));
            startActivity(intent);
        }
    }

    /**
     * 发送邮件
     */
    public static void mail(final Context context, final String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        // intent.setType("text/plain"); // 模拟器使用
        intent.setType("message/rfc822"); // 真实设备使用
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, "" + context.getString(R.string.hint_title_select)));
    }
}