package ai;

import javax.swing.SwingUtilities;

public class ChatService {
    private final AiClient aiClient;

    public ChatService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    // Process the user message and notify the callback with results
    public void processMessage(String userMessage, ChatCallback callback) {
        new Thread(() -> {
            try {
                String aiResponse = aiClient.evaluateAndRespond(userMessage);
                SwingUtilities.invokeLater(() -> callback.onSuccess(aiResponse));
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> callback.onError("Error processing your request."));
            }
        }).start();
    }

    // Callback interface for notifying results
    public interface ChatCallback {
        void onSuccess(String aiResponse);

        void onError(String errorMessage);
    }
}
