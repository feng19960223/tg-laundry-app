package com.turingoal.common.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.turingoal.common.app.TgSystemHelper;

import java.io.File;

/**
 * 工具类-》http处理工具类
 */
public final class TgHttpUtil {

    private TgHttpUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 获取PostRequest, taskkey用来取消请求
     */
    public static PostRequest requestPost(final String url, final Object taskkey) {
        PostRequest request = OkGo.post(url).tag(taskkey).headers("token", TgSystemHelper.getToekn()); // 添加touken
        return request;
    }

    /**
     * 获取GetRequest, taskkey用来取消请求
     */
    public static GetRequest requestGet(final String url, final Object taskkey) {
        GetRequest request = OkGo.get(url).tag(taskkey).headers("token", TgSystemHelper.getToekn()); // 添加touken
        return request;
    }

    /**
     * 下载
     */
    public static GetRequest download(final String url, final Object taskkey) {
        GetRequest request = OkGo.<File>get(url).tag(taskkey).headers("token", TgSystemHelper.getToekn()); // 添加touken
        return request;
    }
}
