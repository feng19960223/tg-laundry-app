package com.turingoal.laundry.constants;

/**
 * 常量-Activity路径
 */
public interface ConstantActivityPath {
    String PACKAGE = "/path/rfid/"; // 所有Activity路径，前面都要加上这个字段，防止起名字出错，页面路由必须是2个以上字符串组成的
    String MAIN = PACKAGE + "main/index"; // Main主界面
    String LOGIN = PACKAGE + "login"; // 登录
    String ABOUT = PACKAGE + "about"; // 关于我们
    String ELECTRICITY = PACKAGE + "electricity"; // 电量
    String BLUETOOTH = PACKAGE + "bluetooth"; // 蓝牙列表
    String RFID = PACKAGE + "rfid"; // 扫描
    String RECORD = PACKAGE + "record"; // 流水详情
}
