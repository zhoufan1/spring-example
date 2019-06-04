package com.example.foundation.dto;

/**
 * @author zhoufan
 * @date 2019/6/4 22:34
 * @description
 */
public enum  SystemCode implements BaseCode {
    SUCCESS("200", "SUCCESS"),
    ERROR("500", "ERROR"),
    ;


    SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;



    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
