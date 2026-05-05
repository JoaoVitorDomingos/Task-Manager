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
    Map<Integer, Task> taskMap;

    public TaskService() {
        this.taskList = new LinkedList<>();
        this.taskMap = new HashMap<>();
    }
    
    public void addTask(String description, String[] tags) {
        Task newTask;
        
        if(tags.length == 0) 
            newTask = new Task(description);
        else 
            newTask = new Task(description, tags);
        
        // TODO - Criar id
        int id = taskList.size();
        newTask.setId((long) id);
        
        taskList.addLast(newTask);
        taskMap.put(id, newTask);
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
