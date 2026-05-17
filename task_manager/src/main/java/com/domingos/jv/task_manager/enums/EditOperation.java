package com.domingos.jv.task_manager.enums;

public enum EditOperation {
    NAME(1),
    ADD_TAG(2), 
    REMOVE_TAG(3),
    CANCEL(0),
    INVALID(-1);
    
    int code;

    private EditOperation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    public static EditOperation fromCode(int number) {
        for (var op : EditOperation.values()) {
            if(number == op.getCode()) return op;
        }
        
        return INVALID;
    }
}
