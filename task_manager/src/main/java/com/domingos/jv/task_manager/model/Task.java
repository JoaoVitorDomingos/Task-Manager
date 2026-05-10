package com.domingos.jv.task_manager.model;

import com.domingos.jv.task_manager.repository.TaskRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
    Classe entidade da tarefa.
*/

public class Task {
    Long id;
    
    String description;
    
    boolean isFinished;
    
    Set<String> tags;

    public Task(long id, String description) {
        this.description = description;
        
        this.isFinished = false;
        
        this.tags = new HashSet<>();
        
        this.id = id;
    }
    
    public Task(long id, String description, String[] tags) {
        this.description = description;
        
        this.isFinished = false;
        
        this.tags = new HashSet<>();
        setTags(tags);
        
        this.id = id;
    }
    
    public boolean adicionarTag(String tag) {
        return this.tags.add(tag);
    }
    
    public boolean removerTag(String tag) {
        return this.tags.remove(tag);
    }
    
    public void removerTodasTags() {
        this.tags.clear();
    }

    // toString's
    @Override
    public String toString() {
        return id + " - " + description
                + (isFinished ? " (Finalizada)" : " (Nao finalizada)");
    }
    
    public String toStringTags() {
        return toString()
                + (tags.isEmpty() ? "" : (" - Tags: " + tags));
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(this.id == null)
            this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags.addAll(Arrays.asList(tags));
    }
    
    
}
