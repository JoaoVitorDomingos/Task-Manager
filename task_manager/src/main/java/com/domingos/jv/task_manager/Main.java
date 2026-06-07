package com.domingos.jv.task_manager;

import com.domingos.jv.task_manager.enums.EditOperation;
import static com.domingos.jv.task_manager.enums.EditOperation.NAME;
import com.domingos.jv.task_manager.enums.FilteringType;
import com.domingos.jv.task_manager.enums.ListingType;
import com.domingos.jv.task_manager.enums.Operations;
import static com.domingos.jv.task_manager.enums.Operations.CREATE;
import static com.domingos.jv.task_manager.enums.Operations.EDIT;
import static com.domingos.jv.task_manager.enums.Operations.EXIT;
import static com.domingos.jv.task_manager.enums.Operations.FINISH;
import static com.domingos.jv.task_manager.enums.Operations.INVALID;
import static com.domingos.jv.task_manager.enums.Operations.LIST;
import static com.domingos.jv.task_manager.enums.Operations.REMOVE;
import com.domingos.jv.task_manager.enums.SortingType;
import com.domingos.jv.task_manager.enums.TaskStatus;
import com.domingos.jv.task_manager.enums.YesNo;
import com.domingos.jv.task_manager.model.Task;
import com.domingos.jv.task_manager.service.TaskService;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
                case EDIT -> {
                    if(!taskService.isEmpty()) editTask();
                    else pause();
                }
                case LIST -> {
                    if(!taskService.isEmpty()) listTasks();
                    else pause();
                }
                case FILTER -> {
                    if(!taskService.isEmpty()) filterTasks();
                    else pause();
                }
                case FINISH -> {
                    if(!taskService.isEmpty()) finishTask();
                    else pause();
                }
                case REMOVE -> {
                    if(!taskService.isEmpty()) removeTask();
                    else pause();
                }
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
        System.out.println("6 - Filtrar tarefa");
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
    
    static YesNo readYesNo() {
        String res;
        YesNo resEnum;
        
        do {
            System.out.println("-> Deixe em branco para "
                    + "cancelar toda a operacao");
            System.out.print("(Y/N): ");
            res = scanner.nextLine().trim();
            
            resEnum = YesNo.fromCode(res);
            
            if(resEnum == YesNo.INVALID)
                System.err.println("\n--\n"
                        + "Digite apenas Y (Sim) ou N (Nao)");
            
        } while(resEnum == YesNo.INVALID);
        
        return resEnum;
    }
    
    static long readTask(String operation, List<Task> list) {
        long id;
        boolean exist;
        
        do {
            System.out.println("\n--Qual tarefa deseja " + operation + "?");
            System.out.println("-> Digite 0 para cancelar!");
            id = readValidLong();
            
            if(id == 0) return id;

            if(list == null)
                exist = taskService.existTask(id);
            else 
                exist = taskService.existTask(id, list);

            if(!exist) {
                System.err.println("\n===========================");
                System.err.println("Esta tarefa nao existe!");
                System.err.println("===========================\n");
                pause();
            }
            
        } while(!exist);
        
        return id;
    }
    
    static List<String> readTag() {
        List<String> tags = new LinkedList<>();
        String tag;
        
        do {
            System.out.println("\n--\n"
                    + "Obs: Deixe em branco para finalizar");
            System.out.print("Digite a tag: ");
            tag = scanner.nextLine().trim();

            if(!tag.isEmpty())
                tags.add(tag.toLowerCase());

        } while(!tag.isEmpty());
        
        return tags;
    }
    
    static void pause() {
        System.out.println("\nDigite qualquer tecla para continuar...");
        scanner.nextLine();
    }
    
    static void cancel() {
        System.out.println("\n=====================");
        System.out.println("Operacao cancelada!");
        System.out.println("=====================");

        pause();
    }
    
    static void pauseTime(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    static void printExit(String menssage) {
        System.out.print("\n--" + menssage);
        
        pauseTime(1500);
        
        System.out.print(".");
        
        pauseTime(1000);
        
        System.out.print(".");
        
        pauseTime(800);
        
        System.out.print(".\n");
    }
    
    static void createTask() {
        System.out.println("\n-------- Criacao de tarefa");
        
        System.out.println("-> Deixe em branco para cancelar!");
        System.out.print("Digite o nome da tarefa: ");
        String nome = scanner.nextLine();
        
        if(nome.trim().isEmpty()) {
            cancel();
            return;
        }
        
        System.out.println("\n--\nVoce quer adicionar tags na tarefa?");
        System.out.println("Obs: tags servem para filtrar as terefas");
        
        YesNo res = readYesNo();
        
        HashSet<String> tags = new HashSet<>();
        
        if(res == YesNo.YES)
            tags.addAll(readTag());
        else if(res == YesNo.CANCEL) {
            cancel();
            return;
        }
        
        long newID = taskService.addTask(nome, tags);
        
        System.out.println("\n-- Tarefa criada com sucesso");
        taskService.printTask(newID, true);
        pause();
    }
    
    static void editTask() {
        System.out.println("\n-------- Edicao de tarefa");
        // Nome - Tags (Adicionar | Remover)
        EditOperation op;
        
        do {
            System.out.println("\n---Qual edicao deseja realizar?");
            System.out.println("1 - Editar nome");
            System.out.println("2 - Adicionar Tag");
            System.out.println("3 - Remover Tag");
            System.out.println("0 - Cancelar");
            
            System.out.print("Digite um numero: ");
            String stringOp = scanner.nextLine();
            
            op = EditOperation.fromCode(readInt(stringOp));
            
            if(op == EditOperation.INVALID) invalidPrint();
            
        } while(op == EditOperation.INVALID);
        
        switch (op) {
            case NAME -> {
                System.out.println("\n--Editar nome");
                
                taskService.listTasks(null, null, false);
                
                long id = readTask("editar o nome", null);
                
                if(id == 0) {
                    cancel();
                    return;
                }
                
                System.out.println();
                taskService.printTask(id, false);
                
                System.out.println("-> Deixe em branco para cancelar!");
                System.out.print("Digite o novo nome: ");
                String newName = scanner.nextLine();
                
                if(newName.trim().isEmpty()) {
                    cancel();
                    return;
                }
                
                TaskStatus status = taskService
                        .editTaskDescription(id, newName);
                
                if(status == TaskStatus.SUCESS) 
                    System.out.println("\n--Nome alterado com sucesso!");
                else 
                    System.out.println("\n--Ocorreu um erro "
                            + "ao alterar o nome!!");
            }
            case ADD_TAG -> {
                System.out.println("\n--Adicionar Tag");
                
                taskService.listTasks(null, null, true);
                
                long id = readTask("adicionar tags", null);
                
                if(id == 0) {
                    cancel();
                    return;
                }
                
                System.out.println();
                taskService.printTask(id, true);
                
                List<String> newTagas = readTag();
                
                TaskStatus status = taskService.addTags(id, 
                        newTagas.toArray(String[]::new));
                
                if(status == TaskStatus.SUCESS) {
                    System.out.println("\n--Tags adicionadas com sucesso!");
                } else 
                    System.out.println("\n--Ocorreu um erro "
                            + "ao adicionar as tags");
            }
            case REMOVE_TAG -> {
                System.out.println("\n--Remover Tag");
                
                taskService.listTasks(null, null, true);
                
                long id = readTask("remover tags", null);
                
                if(id == 0) {
                    cancel();
                    return;
                }
                
                System.out.println();
                taskService.printTask(id, true);
                
                List<String> tagsToRemove = readTag();
                
                TaskStatus status = taskService.removeTags(id, 
                        tagsToRemove.toArray(String[]::new));
                
                if(status == TaskStatus.SUCESS) {
                    System.out.println("\n--Tags removidas com sucesso!");
                } else 
                    System.out.println("\n--Ocorreu um erro "
                            + "ao remover as tags");
            }
            case CANCEL -> {
                System.out.println("\n--Operacao cancelada!");
            }
        }
        pause();
    }
    
    static void listTasks() {
        System.out.println("\n-------- Lista");
        
        ListingType typeListing;
        SortingType typeSorting;
        
        do {
            System.out.println("\n---Qual listagem deseja realizar?");
            System.out.println("1 - Listagem Simples");
            System.out.println("2 - Listagem Completa");
            System.out.println("0 - Cancelar");
            
            System.out.print("\nDigite o numero: ");
            String res = scanner.nextLine();
            
            typeListing = ListingType.fromCode(readInt(res));
            
            if(typeListing == ListingType.INVALID) invalidPrint();
            
        } while(typeListing == ListingType.INVALID);
        
        if(typeListing == ListingType.CANCEL) {
            cancel();
            return;
        }
        
        do {
            System.out.println("\n---Qual orgenacao deseja realizar?");
            System.out.println("1 - Ordenacao Data Adicao");
            System.out.println("2 - Ordenacao Data Adicao Decrescente");
            System.out.println("3 - Ordenacao Alfabetica");
            System.out.println("4 - Ordenacao Alfabetica Decrescente");
            System.out.println("0 - Cancelar");
            
            System.out.print("\nDigite o numero: ");
            String res = scanner.nextLine();
            
            typeSorting = SortingType.fromCode(readInt(res));
            
            if(typeSorting == SortingType.INVALID) invalidPrint();
            
        } while(typeSorting == SortingType.INVALID);
        
        if(typeSorting == SortingType.CANCEL) {
            cancel();
            return;
        }
        
        taskService.list(typeListing, typeSorting);
        
        pause();
    }
    
    static void filterTasks() {
        System.out.println("\n-------- Filtro");
        
        FilteringType typeFilter;
        
        do {
            System.out.println("\n---Qual filtro deseja realizar?");
            System.out.println("1 - Tarefas concluidas");
            System.out.println("2 - Tarefas nao concluidas");
            System.out.println("3 - Nome");
            System.out.println("4 - Tag");
            System.out.println("0 - Cancelar");
            
            System.out.print("\nDigite o numero: ");
            String res = scanner.nextLine();
            
            typeFilter = FilteringType.fromCode(readInt(res));
            
            if(typeFilter == FilteringType.INVALID) invalidPrint();
            
        } while(typeFilter == FilteringType.INVALID);
        
        switch (typeFilter) {
            case NAME -> {
                System.out.println("\n--Filtrar por nome");
                System.out.println("\n-> Deixe em branco para cancelar!");
                System.out.print("Digite: ");
                String name = scanner.nextLine();
                
                if(name.trim().isEmpty()) {
                    cancel();
                    return;
                }
                
                System.out.println("\n---Tarefas com '" + name + "'");
                taskService.filter(typeFilter, name, null);
            }
            case TAG -> {
                System.out.println("\n--Filtrar por tag");
                System.out.println("-> Deixe em branco para"
                        + "cancelar toda a operacao!");
                System.out.print("Digite: ");
                String tag = scanner.nextLine();
                
                if(tag.trim().isEmpty()) {
                    cancel();
                    return;
                }
                
                System.out.println("\n---Tarefas que possuem a tag '"
                        + tag + "'");
                taskService.filter(typeFilter, null, tag);
            }
            case IS_FINISHED -> {
                System.out.println("\n---Tarefas concluidas");
                taskService.filter(typeFilter, null, null);
            }
            case IS_NOT_FINISHED -> {
                System.out.println("\n---Tarefas nao concluidas");
                taskService.filter(typeFilter, null, null);
            }
            case CANCEL -> {
                cancel();
                return;
            }
        }
        
        pause();
    }
    
    static void finishTask() {
        System.out.println("\n-------- Finalizar tarefa");
        
        //taskService.listTasks(null, null, true);
        List<Task> filteredList = taskService.filter(
                FilteringType.IS_NOT_FINISHED, null, null);
        
        long id = readTask("finalizar", filteredList);
        
        if(id == 0) {
            cancel();
            return;
        }
        
        System.out.println("\n--Voce deseja concluir a seguinte tarefa?");
        taskService.printTask(id, true);
        
        YesNo res = readYesNo();
        
        if(res == YesNo.YES) {
            taskService.completeTask(id);
            
            System.out.println("\n--Tarefa finalizada!");
        } else
            System.out.println("\n--Tarefa nao foi concluida.");
        
        pause();
    }
    
    static void removeTask() {
        System.out.println("\n-------- Remover tarefa");
        
        taskService.listTasks(null, null, true);
        
        long id = readTask("remover", null);
        
        if(id == 0) {
            cancel();
            return;
        }
        
        System.out.println("\n--Voce realmente deseja remover esta tarefa?");
        taskService.printTask(id, true);
        
        YesNo res = readYesNo();
        
        if(res == YesNo.YES) {
            taskService.removeTask(id);
            
            System.out.println("\n--Tarefa removida com sucesso");
            
            pause();
        } else {
            System.out.println("\n--Operacao cancelada!");
            pause();
        }
    }
    
    static void invalidPrint() {
        System.err.println("\n==========================");
        System.err.println("Operacao invalida!!!");
        System.err.println("==========================\n");
        
        pause();
    }
    
    static void exit() {
        System.out.println("\n-------- Saindo");
        
        boolean status = taskService.save();
        printExit("Salvando tarefas");
        
        if(status) 
            System.out.println("Tarefas salvas com sucesso!");
        else 
            System.err.println("Ocorreu um erro ao salvar as tarefas!");
        
        printExit("Saindo");
    }
}
