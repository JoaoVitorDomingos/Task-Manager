package com.domingos.jv.task_manager.repository;

import com.domingos.jv.task_manager.model.Task;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/*
    Classe responsável pela persistência do programa
    Será salvo em arquivo .txt
*/
public class TaskRepository {

    String dirPath = "repository";
    String fileName = "data.txt";

    Path directory;
    Path filePath;

    static long countID;

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
                countID = 0;
                System.out.println("Arquivo criado!");
            } else {
                System.out.println("Arquivo ja existe!");
                // TODO Carregar ID
            }
            
            //reader = new BufferedReader(new FileReader(filePath.toFile()));
            

        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public boolean save(List<Task> taskList) {
        try(BufferedWriter writer = 
                new BufferedWriter(new FileWriter(filePath.toFile()))) {
            
            writer.write(String.valueOf(countID));
            writer.newLine();
            
            for (var task : taskList) {
                String s = task.getId() + ";" 
                        + task.getDescription() + ";"
                        + task.getTags();
                
                writer.write(s);
                writer.newLine();
            }
            
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro ao salvar o arquivo");
            System.out.println(ex);
            
            return false;
        }
        
        return true;
    }
    
    public static long obterID() {
        return ++countID;
    }
}
