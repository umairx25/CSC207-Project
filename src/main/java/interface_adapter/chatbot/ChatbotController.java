package interface_adapter.chatbot;

import use_case.chatBot.ChatbotInputBoundary;
import use_case.chatBot.ChatbotInputData;

/**
 * Controller for the chatbot use case.
 * Handles input from the UI and invokes the interactor.
 */
public class ChatbotController {
    private final ChatbotInputBoundary inputBoundary;

    public ChatbotController(ChatbotInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void handleInput(String userMessage) {
        ChatbotInputData inputData = new ChatbotInputData(userMessage);
        inputBoundary.processInput(inputData);
    }
}
