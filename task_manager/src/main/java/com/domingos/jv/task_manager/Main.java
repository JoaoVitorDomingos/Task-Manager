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
import java.util.List;
import java.util.Scanner;

public class Main {
    
    static TaskService taskService = new TaskService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int number;
        Operations op;
        
        do {
            printMenu();
        
            System.out.print("Qual operacao deseja realizar? ");
            number = scanner.nextInt();

            op = Operations.fromCode(number);

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
    
    static void createTask() {
        System.out.println("\n-------- Criacao de tarefa");
    }
    
    static void editTask() {
        System.out.println("\n-------- Edicao de tarefa");
    }
    
    static void listTasks() {
        System.out.println("\n-------- Lista de tarefas");
    }
    
    static void finishTask() {
        System.out.println("\n-------- Finalizar tarefa");
    }
    
    static void removeTask() {
        System.out.println("\n-------- Remover tarefa");
    }
    
    static void invalidPrint() {
        System.err.println("\n==========================");
        System.err.println("Operacao invalida!!!");
        System.err.println("==========================\n");
    }
    
    static void exit() {
        System.out.println("\n-------- Saindo");
    }
}
