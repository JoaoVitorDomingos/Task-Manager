package com.domingos.jv.task_manager.api;

import com.domingos.jv.task_manager.enums.ApiStatus;
import com.domingos.jv.task_manager.model.TaskApi;
import com.domingos.jv.task_manager.model.Translation;
import com.domingos.jv.task_manager.model.TranslationResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class TaskApiClient {

    private HttpClient client;

    private HttpRequest request;

    private HttpResponse<String> response;

    private Gson gson;

    private final String DEEPL_KEY = System.getenv("DEEPL_API_KEY");

    public TaskApiClient() {
        client = HttpClient.newBuilder().build();

        gson = new Gson();
    }

    public ApiResponse<TaskApi> getRandomTask() {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://dummyjson.com/todos/random"))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() >= 500)
                return ApiResponse.failure("500: Erro no servidor!", null);

            TaskApi task = gson.fromJson(response.body(), TaskApi.class);

            ApiResponse<String> translateResponse = translateDescription(task.getTodo());

            if(translateResponse.getStatus() == ApiStatus.SUCESS) {
                task.setTranslatedTodo(translateResponse.getData());
            }

            return ApiResponse.success(task);

        } catch(URISyntaxException ex) {
            return ApiResponse.failure("400: Erro na comunicacao com o serivor!", ex);

        } catch (InterruptedException ex) {
            return ApiResponse.failure("Operacao interrompida!", ex);

        } catch (IOException ex) {
            return ApiResponse.failure("Erro: " + ex, ex);
        }
    }

    private ApiResponse<String> translateDescription(String description) {

        Map<String, Object> requestBody = Map.of(
                "text", List.of(description),
                "target_lang", "PT-BR"
        );

        String body = gson.toJson(requestBody);

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api-free.deepl.com/v2/translate"))
                    .header("Authorization", "DeepL-Auth-Key " + DEEPL_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() >= 500)
                return ApiResponse.failure("500: Erro no servidor!", null);
            else if(response.statusCode() >= 400)
                return ApiResponse.failure(response.body(), null);

            TranslationResponse translations = gson.fromJson(response.body(), TranslationResponse.class);

            return ApiResponse.success(translations.getTranslations().getFirst().getText());

        } catch(URISyntaxException ex) {
            return ApiResponse.failure("400: Erro na comunicacao com o serivor!", ex);

        } catch (InterruptedException ex) {
            return ApiResponse.failure("Operacao interrompida!", ex);

        } catch (IOException ex) {
            return ApiResponse.failure("Erro: " + ex, ex);
        }
    }
}
