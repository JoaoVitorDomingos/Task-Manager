package com.domingos.jv.task_manager.model;

import java.util.HashSet;
import java.util.Set;

/*
    Classe entidade da tarefa.
*/

public class Task implements Comparable<Task> {
    Long id;
    
    String description;
    
    boolean Finished;
    
    Set<String> tags;

    public Task(long id, String description) {
        this.description = description;
        
        this.Finished = false;
        
        this.tags = new HashSet<>();
        
        this.id = id;
    }
    
    public Task(long id, String description, HashSet<String> tags) {
        this.description = description;
        
        this.Finished = false;
        
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
                + (Finished ? " (Finalizada)" : " (Nao finalizada)");
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

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean isFinished) {
        this.Finished = isFinished;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    // Comparable
    @Override
    public int compareTo(Task o) {
        return (int) (this.id - o.getId());
    }
}
