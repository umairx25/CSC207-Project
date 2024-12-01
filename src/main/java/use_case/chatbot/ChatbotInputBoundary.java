package use_case.chatbot;

/**
 * Input boundary for the chatbot use case.
 * Defines the contract for processing user input.
 */
public interface ChatbotInputBoundary {
    /**
     * Processes the user input and returns the chatbot's response.
     *
     * @param inputData The encapsulated user input data.
     * @return The chatbot's response as a string.
     */
    String processInput(ChatbotInputData inputData);
}
