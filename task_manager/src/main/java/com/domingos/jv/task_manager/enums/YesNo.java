package com.domingos.jv.task_manager.enums;

public enum YesNo {
    YES("y"),
    NO("n"),
    CANCEL(""),
    INVALID(-1);
    
    String stringCode;
    int intCode;

    private YesNo(String code) {
        this.stringCode = code;
    }
    
    private YesNo(int code) {
        this.intCode = code;
    }
    
    public static YesNo fromCode(String input) {
        return switch (input) {
            case "y", "Y" -> YES;
            case "n", "N" -> NO;
            case "" -> CANCEL;
            default -> INVALID;
        };
    }
}
