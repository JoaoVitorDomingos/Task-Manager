package com.domingos.jv.task_manager.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/*
    Classe entidade da tarefa.
*/

public class Task implements Comparable<Task> {
    private Long id;
    
    private String description;
    
    private boolean finished;

    private LocalDateTime dateCreated;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Set<String> tags;

    public Task(long id, String description) {
        this.description = description;
        
        this.finished = false;

        this.dateCreated = LocalDateTime.now();
        
        this.tags = new HashSet<>();
        
        this.id = id;
    }
    
    public Task(long id, String description, HashSet<String> tags) {
        this.description = description;
        
        this.finished = false;

        this.dateCreated = LocalDateTime.now();
        
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
        return id + " - " + description + " - " + dateCreated.format(formatter)
                + (finished ? " (Finalizada)" : " (Nao finalizada)");
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
        return finished;
    }

    public void setFinished(boolean isFinished) {
        this.finished = isFinished;
    }

    public LocalDateTime getDateCreated() {return dateCreated;}

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
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
        return this.dateCreated.compareTo(o.getDateCreated());
    }
}
