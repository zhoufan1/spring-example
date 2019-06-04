package com.example.foundation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class Response<T> {
    private String code;
    private String message;
    private T data;

    private Response() {

    }

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .code(SystemCode.SUCCESS.getCode())
                .message(SystemCode.SUCCESS.getMessage())
                .data(data).build();
    }

    public static Response<Void> success() {
        return Response.<Void>builder().code(SystemCode.SUCCESS.getCode()).message(SystemCode.SUCCESS.getMessage()).build();
    }

    public static Response<Void> systemError(String message) {
        return Response.<Void>builder().code(SystemCode.ERROR.getCode()).message(message).build();
    }

    public static <T> Response<T> create(BaseCode baseCode, T data) {
        return Response.<T>builder().code(baseCode.getCode()).message(baseCode.getMessage()).data(data).build();
    }

    public static Response<Void> failed(String code, String message) {
        return Response.<Void>builder().code(code).message(message).build();
    }

    @NotNull
    public boolean isSuccess() {
        return StringUtils.equals(SystemCode.SUCCESS.getCode(), code);
    }
}
