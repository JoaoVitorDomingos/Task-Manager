package com.domingos.jv.task_manager.enums;

public enum FilteringType {
    IS_FINISHED(1),
    IS_NOT_FINISHED(2),
    NAME(3),
    TAG(4),
    INVALID(-1);
    
    final int code;

    private FilteringType(int code) {
        this.code = code;
    }
    
    public static FilteringType fromCode(int num) {
        for(var type : FilteringType.values()) {
            if(type.code == num) return type;
        }
        
        return INVALID;
    }
}
