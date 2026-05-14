package com.domingos.jv.task_manager.service;

import com.domingos.jv.task_manager.enums.TaskStatus;
import com.domingos.jv.task_manager.model.Task;
import com.domingos.jv.task_manager.repository.TaskRepository;
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
    
    TaskRepository taskRepository;
    
    long nextID;

    public TaskService() {
        this.taskRepository = new TaskRepository();
        
        this.taskList = taskRepository.load();
        
        this.taskMap = new HashMap<>();
        carregarMap();
        
        this.nextID = getMaxId() + 1;
    }
    
    void carregarMap() {
        for (var task : this.taskList) {
            this.taskMap.put(task.getId(), task);
        }
    }
    
    long getMaxId() {
        if(taskList.isEmpty()) return 0;
        
        long max = taskList.getFirst().getId();
        
        Task t;
        for (int i = 1; i < taskList.size(); i++) {
            t = taskList.get(i);
            
            if(t.getId() > max) max = t.getId();
        }
        
        return max;
    }
    
    public void addTask(String description, String[] tags) {
        Task newTask = new Task(nextID++, description, tags);
        
        taskList.addLast(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    public void addTask(String description) {
        Task newTask = new Task(nextID++, description);
        
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
    
    boolean isEmpty() {
        if(taskList.isEmpty()) {
            System.out.println("Voce nao possui nenhuma tarefa"
                    + " no momento!");
            return true;
        } else return false;
    }

    public void listTasks() {
        if(isEmpty()) return;
        
        System.out.println("Lista de Tarefas:");
        
        for (var task : this.taskList) {
            System.out.println(task);
        }
    }
    
    public void listTasksTags() {
        if(isEmpty()) return;
        
        System.out.println("Lista de Tarefas com Tags:");
        
        for (var task : this.taskList) {
            System.out.println(task.toStringTags());
        }
    }
    
    public void listTop5Tasks() {
        if(isEmpty()) return;
        
        System.out.println("Lista de tarefas: ");
        
        for(int i = 0; i < 5; i++) {
            System.out.println(taskList.get(i));
        }
        
        if(taskList.size() > 5)
            System.out.println("...");
    }
    
    public boolean save() {
        return taskRepository.save(taskList);
    }
}
