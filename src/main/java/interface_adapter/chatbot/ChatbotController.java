package interface_adapter.chatbot;

import use_case.chatBot.ChatbotInputBoundary;
import use_case.chatBot.ChatbotInputData;

/**
 * Controller for the chatbot use case.
 * Handles user input from the UI and invokes the interactor to process it.
 */
public class ChatbotController {
    private final ChatbotInputBoundary inputBoundary;

    /**
     * Constructs a ChatbotController with the specified input boundary.
     *
     * @param inputBoundary The interactor responsible for processing user input.
     */
    public ChatbotController(ChatbotInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    /**
     * Processes the user input message and returns the chatbot's response.
     *
     * @param userMessage The message entered by the user.
     * @return The chatbot's response.
     */
    public String handleInput(String userMessage) {
        ChatbotInputData inputData = new ChatbotInputData(userMessage);
        return inputBoundary.processInput(inputData);
    }
}
