package com.domingos.jv.task_manager.model;

import java.util.List;

public class TaskApi {

    // DummyJson
    private String todo;

    private boolean completed;

    private String translatedTodo;

    public TaskApi() {
    }

    @Override
    public String toString() {
        return "TaskApi{" +
                "todo='" + todo + '\'' +
                ", completed=" + completed +
                '}';
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTranslatedTodo() {
        return translatedTodo;
    }

    public void setTranslatedTodo(String translatedTodo) {
        this.translatedTodo = translatedTodo;
    }
}
