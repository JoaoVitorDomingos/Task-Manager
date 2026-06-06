package com.domingos.jv.task_manager.enums;

public enum ListingType {
    SIMPLE(1),
    COMPLETE(2),
    CANCEL(0),
    INVALID(-1);

    private final int code;

    ListingType(int code) {
        this.code = code;
    }

    public static ListingType fromCode(int code) {
        for (var type : values()) {
            if (type.code == code) {
                return type;
            }
        }

        return INVALID;
    }
}
