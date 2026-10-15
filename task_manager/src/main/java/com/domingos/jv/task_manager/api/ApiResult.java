package com.domingos.jv.task_manager.api;

import com.domingos.jv.task_manager.enums.ApiStatus;

public final class ApiResult<T> {

    private final ApiStatus status;

    private final T data;

    private final String errorMessage;

    private final Exception exception;

    private ApiResult(ApiStatus status, T data, String errorMessage, Exception exception) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ApiStatus.SUCESS, data, null, null);
    }

    public static <T> ApiResult<T> failure(String errorMessage, Exception ex) {
        return new ApiResult<>(ApiStatus.FAILED, null, errorMessage, ex);
    }

    public ApiStatus getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Exception getException() {
        return exception;
    }
}
