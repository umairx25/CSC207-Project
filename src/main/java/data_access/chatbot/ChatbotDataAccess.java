package data_access.chatbot;

import use_case.chatBot.ChatbotDataAccessInterface;
import okhttp3.*;
import java.io.IOException;

/**
 * Implementation of ChatbotDataAccessInterface.
 * Handles communication with external data sources (e.g., APIs).
 */
public class ChatbotDataAccess implements ChatbotDataAccessInterface {
    private static final String BASE_URL = "https://api.example.com/chat"; // Replace with actual API URL
    private static final String API_KEY = "your-api-key-here"; // Replace with your API key

    private final OkHttpClient client;

    public ChatbotDataAccess() {
        this.client = new OkHttpClient();
    }

    @Override
    public String fetchResponse(String message) throws Exception {
        // Create the request payload
        RequestBody body = RequestBody.create(
                MediaType.get("application/json"),
                "{\"message\":\"" + message + "\"}"
        );

        // Build the HTTP request
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        // Execute the request and handle the response
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code());
            }
            return response.body().string(); // Parse and return the response
        }
    }
}
