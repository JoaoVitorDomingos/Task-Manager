package com.domingos.jv.task_manager.model;

public class TaskApi {

    private String todo;

    private boolean completed;

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


}
