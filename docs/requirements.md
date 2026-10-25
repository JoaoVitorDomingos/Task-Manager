# Especificação de Requisitos de Software

## 1. Introdução

Este documento especifica os requisitos funcionais, as regras de negócio, as regras de validação e as regras de comportamento do Task Manager.

## 2. Visão geral do sistema

O sistema permite gerenciar tarefas, organizar tarefas por tags, pesquisar e filtrar registros, ordenar listas, persistir dados e consultar API externa.

Tudo que não estiver neste documento está fora do escopo.

## 3. Requisitos funcionais

### 3.1 Gerenciamento de tarefas

#### RF-001: O sistema deve permitir que o usuário crie uma tarefa

#### RF-002: O sistema deve permitir que o usuário edite o nome e prazo uma tarefa

#### RF-003: O sistema deve permitir que o usuário delete uma tarefa

#### RF-004: O sistema deve permitir que o usuário conclua uma tarefa

#### RF-005: O sistema deve permitir que o usuário associe uma tag existente à tarefa

#### RF-006: O sistema deve permitir que o usuário remova uma tag associada à tarefa

### 3.2 Gerenciamento de tags

#### RF-007: O sistema deve permitir que o usuário crie uma tag

#### RF-008: O sistema deve permitir que o usuário edite uma tag

#### RF-009: O sistema deve permitir que o usuário delete uma tag

### 3.3 Busca e Filtro

#### RF-010: O sistema deve permitir que o usuário busque uma tarefa pelo nome

A busca deve ser feita pela existência no nome e não deve ser CaseSensitive

#### RF-011: O sistema deve permitir que o usuário filtre as tarefas pelo status de conclusão

#### RF-012: O sistema deve permitir que o usuário filtre as tarefas pelo status de não conclusão

#### RF-013: O sistema deve permitir que o usuário filtre as tarefas por apenas uma tag

#### RF-014: O sistema deve permitir que o usuário filtre as tarefas por data

A comparação será feita com a data de prazo da tarefa.

As opções de comparação são:

- Contidas em um mês
- Antes de um mês
- Após um mês
- Entre um mês e outro

#### RF-015: O sistema deve permitir que o usuário filtre as tarefas pela duração de dias até o prazo

### 3.4 Listagem e Ordenação

#### RF-016: O sistema deve permitir que o usuário liste as tarefas sem tags

#### RF-017: O sistema deve permitir que o usuário liste as tarefas com tags

#### RF-018: O sistema deve permitir que o usuário ordene as tarefas por data de adição

Esta ordenação deve ser feita da mais antiga à mais nova

#### RF-019: O sistema deve permitir que o usuário ordene as tarefas por data de adição decrescente

#### RF-020: O sistema deve permitir que o usuário ordene as tarefas por ordem alfabética

#### RF-021: O sistema deve permitir que o usuário ordene as tarefas por ordem alfabética decrescente

#### RF-022: O sistema deve permitir que o usuário ordene as tarefas por prazo

Esta ordenação deve ser feita pelas tarefas mais próximas do prazo até as mais distantes.

#### RF-023: O sistema deve permitir que o usuário ordene as tarefas por prazo decrescente

### 3.5 Persistência

#### RF-024: O sistema deve salvar a lista de tarefas ao encerrar

Em caso de erro, não irá salvar.

#### RF-025: O sistema deve carregar a lista de tarefas ao iniciar

Caso a pasta ou arquivo não exista, será criado na raiz do projeto.

Caso esteja corrompido, será substituido.

### 3.6 Networking

#### RF-026: O sistema deve buscar uma tarefa em uma API

### 3.7 Gerenciamento da lista inicial

#### RF-027: O sistema deve permitir que o usuário altere o tipo de listagem

Este tipo de listagem é: sem ou com tags.

#### RF-028: O sistema deve permitir que o usuário adicione apenas um filtro na lista

#### RF-029: O sistema deve permitir que o usuário altere o filtro da lista

#### RF-030: O sistema deve permitir que o usuário remova o filtro da lista

#### RF-031: O sistema deve permitir que o usuário altere o limite de exibição de tarefas da lista

Este limite deve estar entre 1 (mínimo) e 20 (máximo).

## 4. Regras de Negócio

### RN-001: A adicição de tags nas tarefas são opcionais

**Justificativa:** deve ser possível criar uma tarefa sem tags.

**Requisitos relacionados:** RF-001; RF-007.

### RN-002: Não pode existir tags repetidas

**Observação:** não é CaseSensitive

**Justificativa:** não faz sentido existir tags repetidas já que serão usadas como classificação para as tarefas

**Requisitos relacionados:** RF-008; RF-009.

### RN-003: Tarefas não podem possuir tags repetidas

**Observação:** não é CaseSensitive

**Justificativa:** não faz sentido existir tags repetidas já que serão usadas como classificação para as tarefas

**Requisitos relacionados:** RF-006.

### RN-004: Tarefas buscadas via API devem ser traduzidas quando possível para pt-br

**Justificativa:** o sistema deve recomendar uma tarefa em português.

**Requisitos relacionados:** RF-026.