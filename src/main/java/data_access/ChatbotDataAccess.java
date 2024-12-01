package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import use_case.chatBot.ChatbotDataAccessInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Handles interaction with the GPT-based API for chatbot responses.
 * Implements the ChatbotDataAccessInterface.
 */
public class ChatbotDataAccess implements ChatbotDataAccessInterface {
    // Load environment variables
    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GPT_API_KEY");
    private static final String BASE_URL = dotenv.get("GPT_BASE_URL");

    // HTTP client configuration
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    /**
     * Cleans the API response to remove unnecessary formatting elements.
     *
     * @param response The raw API response string.
     * @return A cleaned-up version of the response string.
     */
    private String cleanResponse(String response) {
        response = response.replaceAll("\\*\\*", "")
                .replaceAll("_", "")
                .replaceAll("`", "")
                .replaceAll("```.*?```", "")
                .replaceAll("(?m)^\\s*[-*]\\s+", "- ")
                .replaceAll("(?m)^\\s*\\d+\\.\\s+", "1. ")
                .replaceAll("\n{2,}", "\n\n").trim();
        return response;
    }

    /**
     * Fetches a chatbot response from the API using the provided prompt.
     *
     * @param prompt The user prompt to send to the chatbot API.
     * @return The chatbot's response as a cleaned string.
     * @throws Exception If an error occurs during API communication or response processing.
     */
    @Override
    public String fetchResponse(String prompt) throws Exception {
        // Create the message payload
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);

        // Create the full API request payload
        JsonObject payload = new JsonObject();
        payload.add("model", new Gson().toJsonTree("gpt-4o-mini"));
        payload.add("messages", new Gson().toJsonTree(new JsonObject[]{message}));
        payload.addProperty("temperature", 0.7);

        String jsonPayload = new Gson().toJson(payload);

        // Build the API request
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(RequestBody.create(jsonPayload, MediaType.parse("application/json")))
                .build();

        // Execute the request and process the response
        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code());
            }

            assert response.body() != null;
            String responseBody = response.body().string();
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

            String rawText = jsonResponse
                    .getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

            return cleanResponse(rawText);
        }
    }
}
