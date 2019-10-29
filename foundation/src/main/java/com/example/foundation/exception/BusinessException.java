package com.example.foundation.exception;

import com.example.foundation.dto.BaseCode;
import com.example.foundation.dto.Response;
import lombok.Data;

/**
 * @author zhoufan
 * @date 2019/6/4 21:33
 * @description
 */
@Data
public class BusinessException extends RuntimeException {
    private BaseCode code;
    private Throwable cause;

    public BusinessException(BaseCode code) {
        super(code.getCode());
        this.code = code;
    }

    public BusinessException(BaseCode code, Throwable cause) {
        super(code.getCode(), cause);
        this.code = code;
        this.cause = cause;
    }

    public BusinessException(Response response) {
        super(response.getCode());
        this.code = new BaseCode() {
            @Override
            public String getCode() {
                return response.getCode();
            }

            @Override
            public String getMessage() {
                return response.getMessage();
            }
        };
    }
}
