package use_case.chatbot;

/**
 * Output boundary for the chatbot use case.
 * Defines the contract for presenting output data.
 */
public interface ChatbotOutputBoundary {
    /**
     * Presents the output data to the UI.
     *
     * @param outputData The encapsulated chatbot output data.
     */
    void presentOutput(ChatbotOutputData outputData);
}
