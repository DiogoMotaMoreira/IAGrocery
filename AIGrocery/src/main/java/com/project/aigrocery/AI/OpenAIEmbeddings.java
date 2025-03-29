package com.project.aigrocery.AI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import ch.qos.logback.core.boolex.Matcher;

public class OpenAIEmbeddings {

    private static final String API_KEY = "eyJhbGciOiJFUzUxMiIsImtpZCI6IjhhNmVjNWFmLThlNWEtNDQxOS04NmM4LWRkMDkxN2U1YWNlMSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibm92YXB1YmxpYyJdLCJleHAiOjE3NDU4MzM1ODgsIm5iZiI6MTc0MzI0MTU4MywiaWF0IjoxNzQzMjQxNTg4LCJzdWIiOiIyM2VmMGViMy0yZTMwLTQ1MjMtODc3NS1hMWVkZjAzZjAxYWUiLCJlbWFpbCI6ImRyb2RyaWd1ZXNAc2luZ2xlc3RvcmUuY29tIiwiaWRwSUQiOiJiNmQ2YTZiZC04NjYyLTQzYjItYjlkZS1hZjNhMjdlMGZhYzgiLCJlbWFpbFZlcmlmaWVkIjp0cnVlLCJzc29TdWJqZWN0IjoiMjNlZjBlYjMtMmUzMC00NTIzLTg3NzUtYTFlZGYwM2YwMWFlIiwidmFsaWRGb3JQb3J0YWwiOmZhbHNlLCJyZWFkT25seSI6ZmFsc2UsIm5vdmFBcHBJbmZvIjp7InNlcnZpY2VJRCI6Ijg0YjQ3OWM0LTdmOWEtNDJkZi04NDBhLWUwMTkwNmFmYjc5MCIsImFwcElEIjoiZjk5YmVlMmItNjUyMy00YjlkLTk0NjUtY2UwMDRkZWUwYjUzIiwiYXBwVHlwZSI6Ik1vZGVsQXNBU2VydmljZSJ9fQ.AM_EXZqu9P-M26sTJdGxOrGT-jmW4zD5nFIz3jBUK4lkUe5cmESXcP8r-WsnjN7-a5qjLChynM6DSisHrKOwR6x0AJVMzRJoXPOzka358W9iUguq8msoH0sLQukbIjx3x8WO7C84HY4VQ8lYXAqGH8hSG_2lJKlBG3YiduYNoufgcDaw"; 
    private static final String API_URL = "https://apps.aws-london-novaprd1.svc.singlestore.com:8000/modelasaservice/84b479c4-7f9a-42df-840a-e01906afb790/v1/embeddings";


     public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite a primeira pergunta: ");
        String pergunta1 = scanner.nextLine();
        
        System.out.print("Digite a segunda pergunta: ");
        String pergunta2 = scanner.nextLine();
        
        double[] embedding1 = getEmbedding(pergunta1);
        double[] embedding2 = getEmbedding(pergunta2);
        
        if (embedding1 != null && embedding2 != null) {
            double similarity = cosineSimilarity(embedding1, embedding2);
            System.out.printf("Similaridade do cosseno: %.4f\n", similarity);
        } else {
            System.out.println("Erro ao obter embeddings.");
        }
    }

    private static double[] getEmbedding(String input) throws IOException {
        URL url = new URL(API_URL + "?authToken=" + API_KEY);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = String.format(
            "{\"model\":\"Salesforce/SFR-Embedding-2_R\",\"input\":\"%s\",\"input_type\":\"query\"}", 
            input.replace("\"", "\\\"")
        );

        try (OutputStream os = connection.getOutputStream()) {
            byte[] inputBytes = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(inputBytes, 0, inputBytes.length);           
        }

        try (BufferedReader br = new BufferedReader(
            new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            System.out.println("----------------------------------------------------------------------------------------------");
            return parseEmbedding(response.toString());
        }
        
    }

    private static double[] parseEmbedding(String jsonResponse) {
        Pattern pattern = Pattern.compile("\"embedding\":\\[(.*?)\\]");
        java.util.regex.Matcher matcher = pattern.matcher(jsonResponse);
        
        if (matcher.find()) {
            String[] parts = matcher.group(1).split(",");
            double[] embedding = new double[parts.length];
            
            for (int i = 0; i < parts.length; i++) {
                try {
                    embedding[i] = Double.parseDouble(parts[i].trim());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return embedding;
        }
        return null;
    }

    private static double cosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0;
        double normVec1 = 0.0;
        double normVec2 = 0.0;
        
        for (int i = 0; i < vec1.length; i++) {
            dotProduct += vec1[i] * vec2[i];
            normVec1 += Math.pow(vec1[i], 2);
            normVec2 += Math.pow(vec2[i], 2);
        }
        
        return dotProduct / (Math.sqrt(normVec1) * Math.sqrt(normVec2));
    }
}