package com.turingoal.common.bean;


import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import lombok.Data;

/**
 * 服务端json返回结果
 */
@Data
public class TgResponseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final boolean SUCCESS = true;
    public static final boolean FAULT = false;
    private boolean success;
    private boolean tokenValidateResult = true;
    private String msg;
    private String exceptionDetailMsg; // 异常详细信息
    private Object data;

    public TgResponseBean() {
        this.success = true;
    }

    public TgResponseBean(final String message) {
        this.success = true;
        this.msg = message;
    }

    public TgResponseBean(final Object object) {
        this.success = true;
        this.data = object;
    }

    public TgResponseBean(final boolean suc) {
        this.success = suc;
    }

    public TgResponseBean(final boolean suc, final String message) {
        this.success = suc;
        this.msg = message;
    }

    /**
     * 返回异常信息
     */
    public TgResponseBean(final Throwable exceptionMessage) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        // 异常情况
        this.success = false;
        // this.exceptionMessage = sw.toString(); 太详细了
        this.msg = exceptionMessage.getMessage();
    }

    /**
     * 返回异常详细信息
     */
    public TgResponseBean(final Throwable exceptionMessage, final boolean detailMsg) {
        exceptionMessage.printStackTrace(new PrintWriter(new StringWriter()));
        // 异常情况
        this.success = false;
        // this.exceptionMessage = sw.toString(); 太详细了
        this.msg = exceptionMessage.getMessage();
        if (detailMsg) {
            this.exceptionDetailMsg = exceptionMessage.toString(); // 太详细了
        }
    }
}
