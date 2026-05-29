package com.domingos.jv.task_manager.enums;

public enum Operations {
    CREATE(1),
    EDIT(2), 
    REMOVE(3),
    LIST(4),
    FINISH(5),
    EXIT(0),
    INVALID(-1);
    
    final int code;

    private Operations(int code) {
        this.code = code;
    }
    
    public static Operations fromCode(int number) {
        for (var op : Operations.values()) {
            if(number == op.code) return op;
        }
        
        return INVALID;
    }
}
