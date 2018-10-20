package com.turingoal.common.utils;

import com.alibaba.android.arouter.utils.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.turingoal.common.bean.TgResponseBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 工具类-》IO处理工具类-》 json 操作工具类
 * <p>
 * [依赖 fastJson.jar]
 * </p>
 */
public final class TgJsonUtil {

    private TgJsonUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 将TgJsonResultBean 解析成List
     */
    public static <T> List<T> jsonResultBean2List(final TgResponseBean result, final Class<T> c) {
        JSONArray jsonObj = (JSONArray) result.getData();
        return TgJsonUtil.json2List(jsonObj.toJSONString(), c);
    }


    /**
     * 将TgJsonResultBean 解析成对象
     */
    public static <T> T jsonResultBean2Object(final TgResponseBean result, final Class<T> c) {
        JSONObject jsonObj = (JSONObject) result.getData();
        return TgJsonUtil.json2Object(jsonObj.toJSONString(), c);
    }

    /**
     * 将JSON解析成map
     */
    public static Map<String, Object> json2Map(final String json) {
        if (!android.text.TextUtils.isEmpty(json)) {
            return (Map<String, Object>) JSON.parse(json);
        } else {
            return null;
        }
    }


    /**
     * 将JSON解析成对象
     */
    public static <T> T json2Object(final String json, final Class<T> c) {
        if (!android.text.TextUtils.isEmpty(json)) {
            return JSON.parseObject(json, c);
        } else {
            return null;
        }
    }

    /**
     * 将JSON解析成对象
     */
    public static <T> T json2Object(final String json, final Type t) {
        if (!android.text.TextUtils.isEmpty(json)) {
            return JSON.parseObject(json, t);
        } else {
            return null;
        }
    }

    /**
     * 将字符串包装成json数组
     */
    public static String warpJson2ListJson(final String json) {
        String jsonStr = json;
        if (!TextUtils.isEmpty(json)) {
            if (!json.startsWith("[")) {
                jsonStr = "[" + json + "]";
            }
        }
        return jsonStr;
    }

    /**
     * 将JSON解析成对象list
     */
    public static <T> List<T> json2List(final String json, final Class<T> c) {
        String jsonStr = json;
        if (!TextUtils.isEmpty(json)) {
            if (!json.startsWith("[")) {
                jsonStr = "[" + json + "]";
            }
            return JSON.parseArray(jsonStr, c);
        } else {
            return new ArrayList<T>();
        }
    }

    /**
     * 将对象转换成json
     */
    public static String object2Json(final Object entity) {
        return JSON.toJSONString(entity);
    }

}
