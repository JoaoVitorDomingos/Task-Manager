package com.domingos.jv.task_manager.repository;

import com.domingos.jv.task_manager.model.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/*
    Classe responsável pela persistência do programa
    Será salvo em arquivo .txt
*/
public class TaskRepository {

    String dirPath = "repository";
    String fileName = "data.txt";

    Path directory;
    Path filePath;

    boolean isFileCreated;
    
    //static long countID;

    public TaskRepository() {
        try {
            directory = Paths.get(dirPath);

            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                System.out.println("Diretrio criado!");
            }

            filePath = directory.resolve(fileName);

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                isFileCreated = true;
                System.out.println("Arquivo criado!");
            } else {
                isFileCreated = false;
                System.out.println("Arquivo ja existe!");
            }
            
        } catch (IOException ex) {
            System.err.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public boolean save(List<Task> taskList) {
        try(BufferedWriter writer = 
                new BufferedWriter(new FileWriter(filePath.toFile()))) {
            
            for (var task : taskList) {
                String s = task.getId() + ";" 
                        + task.getDescription() + ";"
                        + task.getTags();
                
                writer.write(s);
                writer.newLine();
            }
            
        } catch (IOException ex) {
            System.err.println("Ocorreu um erro ao salvar o arquivo");
            System.err.println(ex);
            
            return false;
        }
        
        return true;
    }
    
    public List<Task> load() {
        List<Task> taskList = new LinkedList<>();
        
        if(isFileCreated) {
            return taskList;
        }
        
        try(BufferedReader reader =
                new BufferedReader(new FileReader(filePath.toFile()));) {
            
            String line = reader.readLine();
            
            while(line != null) {
                System.out.println("linha: " + line);
                
                String[] values = line.split(";");
                
                for (var v : values) {
                    System.out.println(v);
                }
                
                taskList.add(criarTask(values));
                
                line = reader.readLine();
            }
            
        } catch (IOException ex) {
            System.err.println("Erro ao carregar o arquivo!");
            System.err.println(ex);
        }
        
        return taskList;
    }
    
    private Task criarTask(String[] values) {
        long id = Long.parseLong(values[0]);
        
        String description = values[1];
        
        Task t = new Task(id, description);
        
        int tamTags = values[2].length();
        
        String tagsString = 
                values[2].substring(1, tamTags - 1);
        
        if(!tagsString.isEmpty()) {
            String[] tags = tagsString.split(",");
            
            for (var tag : tags) {
                System.out.println("Tag: " + tag.trim());
                t.adicionarTag(tag.trim());
            }
        } else System.out.println("Nao tem tags!");
        
        return t;
    }
}
