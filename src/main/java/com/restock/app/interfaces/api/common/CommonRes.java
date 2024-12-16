package com.restock.app.interfaces.api.common;

import com.restock.app.interfaces.api.exception.ExceptionMessage;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

import static com.restock.app.interfaces.api.common.ResultType.FAIL;
import static com.restock.app.interfaces.api.common.ResultType.SUCCESS;

public record CommonRes<T>(
        ResultType resultType, //
        T data,
        ExceptionMessage exception
) {
    @Override
    public String toString() {
        return "{\"CommonRes\":{"
                + "         \"resultType\":\"" + resultType + "\""
                + ",        \"data\":" + data
                + ",        \"exception\":" + exception + "\""
                + "}}";
    }

    public static <T> CommonRes<T> success(T data) {
        return new CommonRes<>(SUCCESS, data, new ExceptionMessage());
    }

    public static CommonRes<?> error(Exception error, HttpStatus status) {
        return new CommonRes<>(FAIL, new HashMap<>(), new ExceptionMessage(error, status));
    }

    public static CommonRes<?> error(Exception error, Object errorData) {
        return new CommonRes<>(FAIL, new HashMap<>(), new ExceptionMessage(error, (HttpStatus) errorData));
    }
}
