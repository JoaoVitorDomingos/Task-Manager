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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/*
    Classe responsável por toda regra de negócio
    Ela terá controle das tarefas
    Armazenará as tarefas em memória
*/

public class TaskService {
    private List<Task> taskList;
    private Map<Long, Task> taskMap;
    
    private final TaskRepository taskRepository;
    
    private long nextID;

    public TaskService() {
        this.taskRepository = new TaskRepository();
        
        this.taskList = taskRepository.load();
        
        this.taskMap = new HashMap<>();
        carregarMap();
        
        this.nextID = getMaxId() + 1;
    }
    
    private void carregarMap() {
        for (var task : this.taskList) {
            this.taskMap.put(task.getId(), task);
        }
    }
    
    private long getMaxId() {
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
    public long addTask(String description, HashSet<String> tags) {
        Task newTask = new Task(nextID++, description, tags);
        
        taskList.addLast(newTask);
        taskMap.put(newTask.getId(), newTask);
        
        return newTask.getId();
    }
    
    public void addTask(String description) {
        Task newTask = new Task(nextID++, description);
        
        taskList.add(newTask);
        taskMap.put(newTask.getId(), newTask);
    }
    
    private Optional<Task> find(long id) {
        Task task = taskMap.get(id);
        
        return Optional.ofNullable(task);
    }
    
    public boolean existTask(long id) {
        return find(id).isPresent();
    }
    
    public boolean existTask(long id, List<Task> list) {
        for(var task : list) {
            if(task.getId() == id) return true;
        }
        
        return false;
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
        
        boolean printTags = false;
        if(typeListing == ListingType.COMPLETE)
            printTags = true;
        
        Comparator<Task> comparator = null;
        switch (typeSorting) {
            case DESC_NATURAL -> comparator = Collections.reverseOrder();
            case ALPHABETICAL -> comparator = alphabeticalSorting;
            case DESC_ALPHABETICAL -> comparator = 
                    Collections.reverseOrder(alphabeticalSorting);
        }
        
        listTasks(null, comparator, printTags);
    }
    
    public void listTasks(List<Task> list, Comparator<Task> comparator, 
            boolean printTags) {
        
        if(list == null && isEmpty()) return;
        
        if(list == null) list = this.taskList;
        
        list.sort(comparator);
        
        System.out.println(
                printTags ? "\nLista de Tarefas com tags:" 
                        : "\nLista de Tarefas:");
        
        for (var task : list) {
            System.out.println(
                    (printTags ? task.toStringTags() : task));
        }
    }
    
    public void listTop5Tasks() {
        if(isEmpty()) return;
        
        if(taskList.size() < 5) {
            listTasks(null, null, false);
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
    
    public void printTask(long id, boolean printTag) {
        find(id)
                .ifPresentOrElse(
                        (printTag ? task -> 
                                System.out.println(task.toStringTags()) 
                            : System.out::println), 
                        () -> System.out.
                                println("Esta tarefa nao existe!"));
    }
    
    // Filters
    public List<Task> filter(FilteringType typeFilter, 
            String search, String tag) {
        
        List<Task> filteredList;
        
        Predicate<Task> predicate = null;
        
        switch(typeFilter) {
            case IS_FINISHED -> predicate = Task::isFinished;
            case IS_NOT_FINISHED -> predicate = 
                    (task) -> !task.isFinished();
            case NAME -> predicate = 
                    (task) -> task.getDescription().toLowerCase()
                            .contains(search.toLowerCase());
            case TAG -> predicate = 
                    (task) -> task.getTags()
                            .contains(tag.toLowerCase());
        }
        
        filteredList = new ArrayList<>(this.taskList.stream()
                .filter(predicate).sorted().toList());
        
        boolean printTag = false;
        if(typeFilter == FilteringType.TAG)
            printTag = true;
        
        listTasks(filteredList, null, printTag);
        
        return filteredList;
    }
    
    // Comparator
    private static final Comparator<Task> alphabeticalSorting = 
            (t1, t2) -> t1.getDescription()
                    .compareToIgnoreCase(t2.getDescription());
    
    // Repository
    public boolean save() {
        return taskRepository.save(taskList);
    }
}
