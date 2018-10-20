package com.turingoal.laundry.constants;

import com.turingoal.common.app.TgSystemConfig;

/**
 * 网络请求url路径
 */
public interface ConstantUrls {
    String URL_TOKEN_CHECK = TgSystemConfig.getServerBaseUrl() + "/tg/app/checkToken.app"; // 校验token 欢迎
    String URL_LOGIN = TgSystemConfig.getServerBaseUrl() + "/tg/app/login.app"; // 登录 username+password
    String URL_RECORD_FIND = TgSystemConfig.getServerBaseUrl() + "/handover/today.app"; // 查询
    String URL_RECORD_ADD = TgSystemConfig.getServerBaseUrl() + "/handover/create.app"; // 上传
    String URL_RECORD_DELETE = TgSystemConfig.getServerBaseUrl() + "/handover/cancelApp.app"; // 删除/取消
}
