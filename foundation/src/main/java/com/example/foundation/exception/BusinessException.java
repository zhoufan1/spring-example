package com.example.foundation.exception;

import lombok.Data;

/**
 * @author zhoufan
 * @date 2019/6/4 21:33
 * @description
 */
@Data
public class BusinessException extends RuntimeException{
    private String code;
    private String message;
    private Object data;

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
