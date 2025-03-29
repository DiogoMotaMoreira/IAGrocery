package com.project.aigrocery.AI;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;

public class OpenAIEmbeddings {
    private static final String API_KEY = "eyJhbGciOiJFUzUxMiIsImtpZCI6IjhhNmVjNWFmLThlNWEtNDQxOS04NmM4LWRkMDkxN2U1YWNlMSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibm92YXB1YmxpYyJdLCJleHAiOjE3NDU4MzM1ODgsIm5iZiI6MTc0MzI0MTU4MywiaWF0IjoxNzQzMjQxNTg4LCJzdWIiOiIyM2VmMGViMy0yZTMwLTQ1MjMtODc3NS1hMWVkZjAzZjAxYWUiLCJlbWFpbCI6ImRyb2RyaWd1ZXNAc2luZ2xlc3RvcmUuY29tIiwiaWRwSUQiOiJiNmQ2YTZiZC04NjYyLTQzYjItYjlkZS1hZjNhMjdlMGZhYzgiLCJlbWFpbFZlcmlmaWVkIjp0cnVlLCJzc29TdWJqZWN0IjoiMjNlZjBlYjMtMmUzMC00NTIzLTg3NzUtYTFlZGYwM2YwMWFlIiwidmFsaWRGb3JQb3J0YWwiOmZhbHNlLCJyZWFkT25seSI6ZmFsc2UsIm5vdmFBcHBJbmZvIjp7InNlcnZpY2VJRCI6Ijg0YjQ3OWM0LTdmOWEtNDJkZi04NDBhLWUwMTkwNmFmYjc5MCIsImFwcElEIjoiZjk5YmVlMmItNjUyMy00YjlkLTk0NjUtY2UwMDRkZWUwYjUzIiwiYXBwVHlwZSI6Ik1vZGVsQXNBU2VydmljZSJ9fQ.AM_EXZqu9P-M26sTJdGxOrGT-jmW4zD5nFIz3jBUK4lkUe5cmESXcP8r-WsnjN7-a5qjLChynM6DSisHrKOwR6x0AJVMzRJoXPOzka358W9iUguq8msoH0sLQukbIjx3x8WO7C84HY4VQ8lYXAqGH8hSG_2lJKlBG3YiduYNoufgcDaw"; // Substituir pela tua chave real
    private static final String API_URL = "https://apps.aws-london-novaprd1.svc.singlestore.com:8000/modelasaservice/84b479c4-7f9a-42df-840a-e01906afb790/v1";

    public static void main(String[] args) throws Exception {
        String texto = "O que são embeddings?";
        double[] embedding = getEmbedding(texto);
        
        System.out.println("Embedding gerado: " + java.util.Arrays.toString(embedding));
    }

    private static double[] getEmbedding(String input) throws Exception {
        URL url = new URI(API_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String requestBody = new JSONObject()
            .put("model", "text-embedding-ada-002")
            .put("input", input)
            .toString();

        try (OutputStream os = connection.getOutputStream()) {
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
        }

        InputStream responseStream = connection.getInputStream();
        Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
        String response = scanner.hasNext() ? scanner.next() : "";

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.getJSONArray("data").getJSONObject(0).getJSONArray("embedding")
                .toList().stream().mapToDouble(o -> (double) o).toArray();
    }

}