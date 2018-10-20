package com.turingoal.laundry.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 用户
    private String token; // token
    private String username; // 用户名
    private String realname; // 真实姓名
    private String userPhone; // 手机号
    private String codeNum; // 工号
    private String userJobNo; // 职位（暂时无用）
    private String factoryName; // 工厂（发送单位）
}
