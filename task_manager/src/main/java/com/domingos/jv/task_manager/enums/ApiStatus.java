package com.domingos.jv.task_manager.enums;

import com.domingos.jv.task_manager.model.TaskApi;

public enum ApiStatus {

    SUCESS(200),
    FAILED(-1);

    private final int code;

    ApiStatus(int code) {
        this.code = code;
    }
}
