package data_access.chatbot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import use_case.chatBot.ChatbotDataAccessInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatbotDataAccess implements ChatbotDataAccessInterface {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GPT_API_KEY");
    private static final String BASE_URL = dotenv.get("GPT_BASE_URL");

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private String cleanResponse(String response) {
        response = response.replaceAll("\\*\\*", "").replaceAll("_", "")
                .replaceAll("`", "").replaceAll("```.*?```", "")
                .replaceAll("(?m)^\\s*[-*]\\s+", "- ")
                .replaceAll("(?m)^\\s*\\d+\\.\\s+", "1. ")
                .replaceAll("\n{2,}", "\n\n").trim();
        return response;
    }

    @Override
    public String fetchResponse(String prompt) throws Exception {
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
                throw new IOException("Unexpected code " + response.code());
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
    }
}
