package com.domingos.jv.task_manager.enums;

public enum SortingType {
    NATURAL(1),
    DESC_NATURAL(2),
    ALPHABETICAL(3),
    DESC_ALPHABETICAL(4),
    INVALID(-1);

    private final int code;

    SortingType(int code) {
        this.code = code;
    }

    public static SortingType fromCode(int code) {
        for (var type : values()) {
            if (type.code == code) {
                return type;
            }
        }

        return INVALID;
    }
}