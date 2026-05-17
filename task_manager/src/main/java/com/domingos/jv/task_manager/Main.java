package com.domingos.jv.task_manager;

import com.domingos.jv.task_manager.enums.Operations;
import static com.domingos.jv.task_manager.enums.Operations.CREATE;
import static com.domingos.jv.task_manager.enums.Operations.EDIT;
import static com.domingos.jv.task_manager.enums.Operations.EXIT;
import static com.domingos.jv.task_manager.enums.Operations.FINISH;
import static com.domingos.jv.task_manager.enums.Operations.INVALID;
import static com.domingos.jv.task_manager.enums.Operations.LIST;
import static com.domingos.jv.task_manager.enums.Operations.REMOVE;
import com.domingos.jv.task_manager.service.TaskService;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    
    static TaskService taskService = new TaskService();
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        int number;
        Operations op;
        
        do {
            printMenu();
        
            System.out.print("Qual operacao deseja realizar? ");

            op = Operations.fromCode(
                    readInt(scanner.nextLine()));

            switch(op) {
                case CREATE -> createTask();
                case EDIT -> editTask();
                case LIST -> listTasks();
                case FINISH -> finishTask();
                case REMOVE -> removeTask();
                case EXIT -> exit();
                case INVALID -> invalidPrint();
            }
            
        } while(op != Operations.EXIT);
    }
    
    static void printMenu() {
        System.out.println("\n==================================");
        System.out.println("\n\tTask Manager (JV)\n");
        
        taskService.listTop5Tasks();
        
        System.out.println("\nOperacoes:");
        System.out.println("1 - Criar tarefa");
        System.out.println("2 - Editar tarefa");
        System.out.println("3 - Remover tarefa");
        System.out.println("4 - Listar tarefas");
        System.out.println("5 - Concluir tarefa");
        System.out.println("0 - Sair\n");
    }
    
    static int readInt(String input) {
        int ret;
        
        try {
            ret = Integer.parseInt(input);
        } catch(NumberFormatException ex) {
            ret = -1;
        }
        
        return ret;
    }
    
    static long readLong(String input) {
        long ret;
        
        try {
            ret = Long.parseLong(input);
        } catch(NumberFormatException ex) {
            ret = -1;
        }
        
        return ret;
    }
    
    static int readValidInt() {
        int number;
        
        do {
            number = readInt(scanner.nextLine());
            
            if(number == -1) {
                System.out.println("\n=================");
                System.out.println("Digite apenas numeros");
                System.out.println("=================");
                
                pause();
            }
        } while(number == -1);
        
        return number;
    }
    
    static long readValidLong() {
        long number;
        
        do {
            System.out.print("Digite o numero: ");
            number = readLong(scanner.nextLine());
            
            if(number == -1) {
                System.out.println("\n=================");
                System.out.println("Digite apenas numeros");
                System.out.println("=================");
                
                pause();
            }
        } while(number == -1);
        
        return number;
    }
    
    static String readYesNo() {
        String res;
        do {
            System.out.print("(Y/N): ");
            res = scanner.nextLine().trim();
            
            if(!res.equalsIgnoreCase("Y") 
                && !res.equalsIgnoreCase("N"))
                System.err.println("\n--\n"
                        + "Digite apenas Y (Sim) ou N (Nao)");
            
        } while(!res.equalsIgnoreCase("Y") 
                && !res.equalsIgnoreCase("N"));
        
        return res;
    }
    
    static void pause() {
        System.out.println("\nDigite qualquer tecla para continuar...");
        scanner.nextLine();
    }
    
    static void createTask() {
        System.out.println("\n-------- Criacao de tarefa");
        
        System.out.print("Digite o nome da tarefa: ");
        String nome = scanner.nextLine();
        
        System.out.println("\n--\nVoce quer adicionar tags na tarefa?");
        System.out.println("Obs: tags servem para filtrar as terefas");
        
        String res = readYesNo();
        
        HashSet<String> tags = new HashSet<>();
        if(res.equalsIgnoreCase("Y")) {
            String tag;
            do {
                System.out.println("\n--\n"
                        + "Obs: Deixe em branco para finalizar");
                System.out.print("Digite a tag: ");
                tag = scanner.nextLine().trim();
                
                boolean isAdded = tags.add(tag);
                
                if(!isAdded) {
                    System.out.println("\n--\n"
                            + "Tag ja adicionada anteriormente!");
                }
                
            } while(!tag.isEmpty());
        }
        
        taskService.addTask(nome, tags);
    }
    
    static void editTask() {
        System.out.println("\n-------- Edicao de tarefa");
    }
    
    static void listTasks() {
        System.out.println("\n-------- Lista");
        
        taskService.listTasks();
        
        pause();
    }
    
    static void finishTask() {
        System.out.println("\n-------- Finalizar tarefa");
        
        taskService.listTasks();
        
        long id;
        boolean exist;
        
        do {
            System.out.println("\n--Qual tarefa deseja finalizar?");
            System.out.print("Digite o numero: ");
            id = readLong(scanner.nextLine());

            exist = taskService.existTask(id);

            if(!exist) {
                System.err.println("\n===========================");
                System.err.println("Esta tarefa nao existe!");
                System.err.println("===========================\n");
                pause();
            }
            
        } while(!exist);
        
        System.out.println("\n--Voce deseja concluir a seguinte tarefa?");
        taskService.printTask(id);
        
        String res = readYesNo();
        
        if(res.equalsIgnoreCase("Y")) {
            taskService.completeTask(id);
            
            System.out.println("\n--Tarefa finalizada!");
        } else System.out.println("\n--Tarefa nao foi concluida.");
        
        pause();
    }
    
    static void removeTask() {
        System.out.println("\n-------- Remover tarefa");
    }
    
    static void invalidPrint() {
        System.err.println("\n==========================");
        System.err.println("Operacao invalida!!!");
        System.err.println("==========================\n");
        
        pause();
    }
    
    static void exit() {
        System.out.println("\n-------- Saindo");
    }
}
