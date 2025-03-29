package com.project.aigrocery.AI;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIEmbeddings {

    private static final String API_KEY = "eyJhbGciOiJFUzUxMiIsImtpZCI6IjhhNmVjNWFmLThlNWEtNDQxOS04NmM4LWRkMDkxN2U1YWNlMSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibm92YXB1YmxpYyJdLCJleHAiOjE3NDU4MzM1ODgsIm5iZiI6MTc0MzI0MTU4MywiaWF0IjoxNzQzMjQxNTg4LCJzdWIiOiIyM2VmMGViMy0yZTMwLTQ1MjMtODc3NS1hMWVkZjAzZjAxYWUiLCJlbWFpbCI6ImRyb2RyaWd1ZXNAc2luZ2xlc3RvcmUuY29tIiwiaWRwSUQiOiJiNmQ2YTZiZC04NjYyLTQzYjItYjlkZS1hZjNhMjdlMGZhYzgiLCJlbWFpbFZlcmlmaWVkIjp0cnVlLCJzc29TdWJqZWN0IjoiMjNlZjBlYjMtMmUzMC00NTIzLTg3NzUtYTFlZGYwM2YwMWFlIiwidmFsaWRGb3JQb3J0YWwiOmZhbHNlLCJyZWFkT25seSI6ZmFsc2UsIm5vdmFBcHBJbmZvIjp7InNlcnZpY2VJRCI6Ijg0YjQ3OWM0LTdmOWEtNDJkZi04NDBhLWUwMTkwNmFmYjc5MCIsImFwcElEIjoiZjk5YmVlMmItNjUyMy00YjlkLTk0NjUtY2UwMDRkZWUwYjUzIiwiYXBwVHlwZSI6Ik1vZGVsQXNBU2VydmljZSJ9fQ.AM_EXZqu9P-M26sTJdGxOrGT-jmW4zD5nFIz3jBUK4lkUe5cmESXcP8r-WsnjN7-a5qjLChynM6DSisHrKOwR6x0AJVMzRJoXPOzka358W9iUguq8msoH0sLQukbIjx3x8WO7C84HY4VQ8lYXAqGH8hSG_2lJKlBG3YiduYNoufgcDaw'"; // Substituir pela chave real
    private static final String API_URL = "https://apps.aws-london-novaprd1.svc.singlestore.com:8000/modelasaservice/84b479c4-7f9a-42df-840a-e01906afb790/v1/embeddings";


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Perguntar ao usuário qual pergunta ele deseja fazer
        System.out.print("Digite a pergunta para gerar os embeddings: ");
        String pergunta = scanner.nextLine();

        // Executar o comando curl com a pergunta dinâmica
        String response = runCurlCommand(pergunta);
        System.out.println("Resposta da API: " + response);
    }

    private static String runCurlCommand(String pergunta) throws Exception {
        // Comando curl para execução com a pergunta
        String command = String.format(
            "curl 'https://apps.aws-london-novaprd1.svc.singlestore.com:8000/modelasaservice/84b479c4-7f9a-42df-840a-e01906afb790/v1/embeddings?authToken=%s' -X POST " +
            "-H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:136.0) Gecko/20100101 Firefox/136.0' " +
            "-H 'Accept: application/json' " +
            "-H 'Accept-Language: en-US,en;q=0.5' " +
            "-H 'Accept-Encoding: gzip, deflate, br, zstd' " +
            "-H 'Content-Type: application/json' " +
            "-H 'Origin: https://portal.singlestore.com' " +
            "-H 'Connection: keep-alive' " +
            "-H 'Sec-Fetch-Dest: empty' " +
            "-H 'Sec-Fetch-Mode: cors' " +
            "-H 'Sec-Fetch-Site: same-site' " +
            "-H 'Priority: u=0' " +
            "-H 'TE: trailers' --data-raw $'{\"model\":\"Salesforce/SFR-Embedding-2_R\",\"input\":\"%s\",\"input_type\":\"query\"}'",
            API_KEY, pergunta
        );

        // Executar o comando curl no Windows
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Lê a resposta
        StringBuilder responseBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
        }

        // Espera o processo terminar
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Erro ao executar o comando curl. Código de saída: " + exitCode);
        }

        return responseBuilder.toString();
    }
}