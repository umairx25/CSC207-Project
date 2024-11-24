package ai;

import com.google.gson.JsonSyntaxException;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GenerativeAi implements AiClient {

    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GPT_API_KEY");
    private static final String BASE_URL = dotenv.get("GPT_BASE_URL");

    // Static OkHttpClient for better resource management
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Increase connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Increase read timeout
            .writeTimeout(30, TimeUnit.SECONDS)    // Increase write timeout
            .build();

    // Helper method to clean up formatting codes
    private String cleanResponse(String response) {
        response = response.replaceAll("\\*\\*", ""); // Remove bold
        response = response.replaceAll("_", "");     // Remove italics
        response = response.replaceAll("`", "");     // Remove inline code/backticks
        response = response.replaceAll("```.*?```", ""); // Remove code blocks
        response = response.replaceAll("(?m)^\\s*[-*]\\s+", "- "); // Normalize unordered lists
        response = response.replaceAll("(?m)^\\s*\\d+\\.\\s+", "1. "); // Normalize ordered lists
        response = response.replaceAll("\n{2,}", "\n\n"); // Collapse excessive newlines
        return response.trim(); // Trim leading/trailing whitespace
    }

    // Modified sendPrompt method for OpenAI GPT-4 API
    public String sendPrompt(String prompt) throws Exception {
        int retryCount = 0;
        int maxRetries = 3;
        int retryDelay = 2000; // 2 seconds

        while (retryCount < maxRetries) {
            try {
                JsonObject message = new JsonObject();
                message.addProperty("role", "user");
                message.addProperty("content", prompt);

                JsonObject payload = new JsonObject();
                payload.add("model", new Gson().toJsonTree("gpt-4o-mini"));
                payload.add("messages", new Gson().toJsonTree(new JsonObject[]{message}));
                payload.addProperty("temperature", 0.7);

                String jsonPayload = new Gson().toJson(payload);

                Request request = new Request.Builder()
                        .url(BASE_URL)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + API_KEY)
                        .post(RequestBody.create(jsonPayload, MediaType.parse("application/json")))
                        .build();

                try (Response response = CLIENT.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        if (response.code() == 503) {
                            retryCount++;
                            Thread.sleep(retryDelay);
                            continue; // Retry the request
                        }
                        throw new IOException("Unexpected code " + response + "\nBody: " + response.body().string());
                    }

                    String responseBody = response.body().string();
                    JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                    String rawText = jsonResponse
                            .getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("message")
                            .get("content").getAsString();

                    return cleanResponse(rawText);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Retry interrupted", e);
            }
        }
        throw new IOException("Maximum retry attempts reached. The service is currently unavailable.");
    }

    // Method to evaluate the question and get the result
    public String evaluateAndRespond(String question) {
        String prompt = "Analyze the following question and provide the appropriate response:\n"
                + "Question: \"" + question + "\"\n"
                + "If the question is not finance-related (Make sure it is an appropriate Finance question) or if it requires real-time data, respond with the word FALSE.\n"
                + "Else: answer the question efficiently and briefly.";

        try {
            String response = sendPrompt(prompt);
            if (response.equalsIgnoreCase("FALSE")) {
                return "Sorry, either the question is not finance-related or it requires real-time data.";
            } else {
                return response;
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Network or API error: " + e.getMessage());
            return "Sorry, a network or API error occurred. Please try again later.";
        } catch (JsonSyntaxException e) {
            System.err.println("[ERROR] JSON parsing error: " + e.getMessage());
            return "Sorry, the response from the AI could not be processed. Please contact support.";
        } catch (Exception e) {
            System.err.println("[ERROR] Unexpected error: " + e.getMessage());
            return "Sorry, an unexpected error occurred. Try again later.";
        }
    }

    public static void main(String[] args) {
        GenerativeAi aiClient = new GenerativeAi();
        System.out.println("Welcome to the AI System! Type your question, or type 'exit' to quit.");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter your question: ");
                String userQuestion = scanner.nextLine().trim();

                if (userQuestion.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (userQuestion.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid question.");
                    continue;
                }

                String response = aiClient.evaluateAndRespond(userQuestion);
                System.out.println(response);
            }
        }
    }
}