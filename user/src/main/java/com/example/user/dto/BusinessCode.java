package com.example.user.dto;

import com.example.foundation.dto.BaseCode;

/**
 * @author zhoufan
 * @date 2019/6/13 10:15
 * @description
 */
public enum BusinessCode implements BaseCode {

    AuthorityAccount("U0001", "登陆失败"),
    ;
    private String code;
    private String message;

    BusinessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
