package com.domingos.jv.task_manager.api;

import com.domingos.jv.task_manager.model.TaskApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TaskApiClient {

    private HttpClient client;

    private HttpRequest request;

    private HttpResponse<String> response;

    private Gson gson;

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

            if(response.statusCode() == 500)
                return ApiResponse.failure("500: Erro no servidor!", null);
            else
                return ApiResponse.success(gson.fromJson(response.body(), TaskApi.class));

        } catch(URISyntaxException ex) {
            return ApiResponse.failure("400: Erro na comunicacao com o serivor!", ex);

        } catch (InterruptedException ex) {
            return ApiResponse.failure("Operacao interrompida!", ex);

        } catch (IOException ex) {
            return ApiResponse.failure("Erro: " + ex, ex);
        }
    }
}
