package com.turingoal.common.app;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.turingoal.common.utils.TgJsonUtil;

import java.lang.reflect.Type;

/**
 * Arouter 传输自定义对象用
 */
@Route(path = "/service/json")
public class TgJsonServiceImpl implements SerializationService {
    @Override
    public void init(final Context context) {
    }

    @Override
    public <T> T json2Object(final String input, final Class<T> clazz) {
        return TgJsonUtil.json2Object(input, clazz);
    }

    @Override
    public String object2Json(final Object instance) {
        return TgJsonUtil.object2Json(instance);
    }

    @Override
    public <T> T parseObject(final String input, final Type t) {
        return TgJsonUtil.json2Object(input, t);
    }
}