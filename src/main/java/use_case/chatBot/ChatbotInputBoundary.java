package use_case.chatBot;

/**
 * Input boundary for the chatbot use case.
 * Defines the contract for processing user input.
 */
public interface ChatbotInputBoundary {
    String processInput(ChatbotInputData inputData);
}

