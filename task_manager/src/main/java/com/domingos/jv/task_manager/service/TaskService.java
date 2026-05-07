package com.domingos.jv.task_manager.service;

import com.domingos.jv.task_manager.enums.TaskStatus;
import com.domingos.jv.task_manager.model.Task;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    Classe responsável por toda regra de negócio
    Ela terá controle das tarefas
    Armazenará as tarefas em memória
*/

public class TaskService {
    List<Task> taskList;
    Map<Long, Task> taskMap;

    public TaskService() {
        this.taskList = new LinkedList<>();
        this.taskMap = new HashMap<>();
    }
    
    public void addTask(String description, String[] tags) {
        Task newTask = new Task(description, tags);
        
        taskList.addLast(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    public void addTask(String description) {
        Task newTask = new Task(description);
        
        taskList.add(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    public Optional<Task> find(int id) {
        Task task = taskMap.get(id);
        
        return Optional.ofNullable(task);
    }
    
    public TaskStatus completeTask(int id) {
        return find(id)
                .map(task -> {
                    if(task.isIsFinished()) return TaskStatus.ALREADY_COMPLETED;
                    
                    task.setIsFinished(true);
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }
    
    public TaskStatus removeTask(int id) {
        return find(id)
                .map(task -> {
                    taskList.remove(task);
                    taskMap.remove(id);
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }

    public void listTasks() {
        System.out.println("Lista de Tarefas:");
        
        for (var task : this.taskList) {
            System.out.println(task);
        }
    }
    
    public void listTasksTags() {
        System.out.println("Lista de Tarefas com Tags:");
        
        for (var task : this.taskList) {
            System.out.println(task.toStringTags());
        }
    }
}
