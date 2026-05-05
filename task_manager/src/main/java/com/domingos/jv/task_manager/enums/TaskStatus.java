package com.domingos.jv.task_manager.enums;


public enum TaskStatus {
    SUCESS ("sucess"),
    ALREADY_COMPLETED ("already completed"),
    INCOMPLETE ("imcomplete"),
    NOT_FOUND ("not found"),
    HAS_TAGS ("task with tags"),
    EMPTY_TAGS ("task with no tags");
    
    private String description;

    private TaskStatus(String description) {
        this.description = description;
    }
}
