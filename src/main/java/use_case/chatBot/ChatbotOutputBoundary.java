package use_case.chatBot;

/**
 * Output boundary for the chatbot use case.
 * Defines the contract for presenting output data.
 */
public interface ChatbotOutputBoundary {
    void presentOutput(ChatbotOutputData outputData);
}
