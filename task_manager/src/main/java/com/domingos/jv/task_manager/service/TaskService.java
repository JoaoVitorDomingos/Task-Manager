package com.domingos.jv.task_manager.service;

import com.domingos.jv.task_manager.enums.TaskStatus;
import com.domingos.jv.task_manager.model.Task;
import com.domingos.jv.task_manager.repository.TaskRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
    
    // Service
    public void addTask(String description, HashSet<String> tags) {
        Task newTask = new Task(nextID++, description, tags);
        
        taskList.addLast(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    public void addTask(String description) {
        Task newTask = new Task(nextID++, description);
        
        taskList.add(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    Optional<Task> find(long id) {
        Task task = taskMap.get(id);
        
        return Optional.ofNullable(task);
    }
    
    public boolean existTask(long id) {
        return find(id).isPresent();
    }
    
    public TaskStatus removeTask(long id) {
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
    
    // Task
    public TaskStatus completeTask(long id) {
        return find(id)
                .map(task -> {
                    if(task.isIsFinished()) return TaskStatus.ALREADY_COMPLETED;
                    
                    task.setIsFinished(true);
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }
    
    public TaskStatus editTaskDescription(long id, String newDescription) {
        return find(id)
                .map(task -> {
                    task.setDescription(newDescription);
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }
    
    public TaskStatus addTags(long id, String[] newTags) {
        return find(id)
                .map(task -> {
                    for (var tag : newTags) {
                        task.adicionarTag(tag);
                    }
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }
    
    public TaskStatus removeTags(long id, String[] tagsToRemove) {
        return find(id)
                .map(task -> {
                    for (var tag : tagsToRemove) {
                        task.removerTag(tag);
                    }
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }

    // Print
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
        
        if(taskList.size() < 5) {
            listTasks();
            return;
        }
        
        System.out.println("Lista de tarefas: ");
        
        for(int i = 0; i < 5; i++) {
            System.out.println(taskList.get(i));
        }
        
        if(taskList.size() > 5)
            System.out.println("...");
    }
    
    public void printTask(long id) {
        find(id)
                .ifPresentOrElse(System.out::println, 
                        () -> System.out.
                                println("Esta tarefa nao existe!"));
    }
    
    public void printTaskTags(long id) {
        find(id)
                .ifPresentOrElse(task -> task.toStringTags(), 
                        () -> System.out
                                .println("Esta tarefa nao existe!"));
    }
    
    // Repository
    public boolean save() {
        return taskRepository.save(taskList);
    }
}
