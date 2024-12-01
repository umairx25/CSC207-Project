package data_access;

import okhttp3.*;
import java.io.IOException;

public class ChatbotDataAccess {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "YOUR_API_URL";
    private static final String API_KEY = "YOUR_API_KEY";

    public String getAIResponse(String prompt) {
        RequestBody body = RequestBody.create(
                "{\"prompt\":\"" + prompt + "\", \"model\":\"gpt-4\"}",
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error fetching AI response.";
        }
    }
}
