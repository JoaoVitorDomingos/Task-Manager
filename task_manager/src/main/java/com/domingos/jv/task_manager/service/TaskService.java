package com.domingos.jv.task_manager.service;

import com.domingos.jv.task_manager.enums.FilteringType;
import com.domingos.jv.task_manager.enums.ListingType;
import com.domingos.jv.task_manager.enums.SortingType;
import com.domingos.jv.task_manager.enums.TaskStatus;
import com.domingos.jv.task_manager.model.Task;
import com.domingos.jv.task_manager.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    public boolean isEmpty() {
        if(taskList.isEmpty()) {
            System.out.println("\n==========================");
            System.out.println("Voce nao possui nenhuma tarefa"
                    + " no momento!");
            System.out.println("==========================");
            return true;
        } else return false;
    }
    
    // Task
    public TaskStatus completeTask(long id) {
        return find(id)
                .map(task -> {
                    if(task.isFinished()) return TaskStatus.ALREADY_COMPLETED;
                    
                    task.setFinished(true);
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
                        task.adicionarTag(tag.toLowerCase());
                    }
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }
    
    public TaskStatus removeTags(long id, String[] tagsToRemove) {
        return find(id)
                .map(task -> {
                    for (var tag : tagsToRemove) {
                        task.removerTag(tag.toLowerCase());
                    }
                    
                    return TaskStatus.SUCESS;
                })
                .orElseGet(() -> TaskStatus.NOT_FOUND);
    }

    // Print
    public void list(ListingType typeListing, 
            SortingType typeSorting) {
        
        if(typeListing == ListingType.SIMPLE) {
            switch(typeSorting) {
                case NATURAL -> listTasksSorted(null);
                case DESC_NATURAL -> 
                    listTasksSorted(Collections.reverseOrder());
                case ALPHABETICAL -> listTasksSorted(alphabeticalSorting);
                case DESC_ALPHABETICAL -> 
                    listTasksSorted(Collections.reverseOrder(alphabeticalSorting));
            }
            
        } else {
            switch(typeSorting) {
                case NATURAL -> listTasksTagsSorted(null);
                case DESC_NATURAL -> 
                    listTasksTagsSorted(Collections.reverseOrder());
                case ALPHABETICAL -> listTasksTagsSorted(alphabeticalSorting);
                case DESC_ALPHABETICAL -> 
                    listTasksTagsSorted(Collections.reverseOrder(alphabeticalSorting));
            }
        }
    }
    
    public void listTasksSorted(Comparator<Task> comparator) {
        if(isEmpty()) return;
        
        System.out.println("\nLista de Tarefas:");
        
        this.taskList.sort(comparator);
        
        for (var task : this.taskList) {
            System.out.println(task);
        }
    }
    
    public void listFilteredTasks(List<Task> filteredList) {
        if(filteredList.isEmpty()) System.out.println("Lista vazia!");
        
        for (var task : filteredList) {
            System.out.println(task);
        }
    }
    
    public void listTasksTagsSorted(Comparator<Task> comparator) {
        if(isEmpty()) return;
        
        System.out.println("\nLista de Tarefas com Tags:");
        
        this.taskList.sort(comparator);
        
        for (var task : this.taskList) {
            System.out.println(task.toStringTags());
        }
    }
    
    public void listFilteredTasksTags(List<Task> filteredList) {
        if(filteredList.isEmpty()) System.out.println("Lista vazia!");
        
        for (var task : filteredList) {
            System.out.println(task.toStringTags());
        }
    }
    
    public void listTop5Tasks() {
        if(isEmpty()) return;
        
        if(taskList.size() < 5) {
            listTasksSorted(null);
            return;
        }
        
        System.out.println("Lista de tarefas: ");
        
        this.taskList.sort(null);
        
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
    
    // Filters
    public void filter(FilteringType typeFilter, String search, String tag) {
        List<Task> filteredList = new ArrayList<>();
        
        switch(typeFilter) {
            case IS_FINISHED -> filteredList = this.taskList.stream()
                    .filter(Task::isFinished)
                    .sorted()
                    .toList();
            case IS_NOT_FINISHED -> filteredList = this.taskList.stream()
                    .filter((task) -> !task.isFinished())
                    .sorted()
                    .toList();
            case NAME -> filteredList = this.taskList.stream()
                    .filter((task) -> task.getDescription().toLowerCase()
                            .contains(search.toLowerCase()))
                    .sorted()
                    .toList();
            case TAG -> filteredList = this.taskList.stream()
                    .filter((task) -> task.getTags()
                            .contains(tag.toLowerCase()))
                    .sorted()
                    .toList();
        }
        
        if(typeFilter == FilteringType.TAG) 
            listFilteredTasksTags(filteredList);
        else 
            listFilteredTasks(filteredList);
    }
    
    // Comparator
    Comparator<Task> alphabeticalSorting = 
            (t1, t2) -> t1.getDescription()
                    .compareToIgnoreCase(t2.getDescription());
    
    // Repository
    public boolean save() {
        return taskRepository.save(taskList);
    }
}
