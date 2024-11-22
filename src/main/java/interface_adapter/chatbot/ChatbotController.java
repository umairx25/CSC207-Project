package interface_adapter.chatbot;

import use_case.chatbot.ChatbotInputData;
import use_case.chatbot.ChatbotInputBoundary;

public class ChatbotController {
    private final ChatbotInputBoundary inputBoundary;

    public ChatbotController(ChatbotInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void handleUserMessage(String message) {
        ChatbotInputData inputData = new ChatbotInputData(message);
        inputBoundary.processMessage(inputData);
    }
}
