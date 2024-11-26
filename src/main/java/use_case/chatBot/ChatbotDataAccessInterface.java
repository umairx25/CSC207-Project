package use_case.chatBot;

/**
 * Interface for chatbot data access.
 * Provides methods for interacting with external data sources.
 */
public interface ChatbotDataAccessInterface {
    String fetchResponse(String message) throws Exception;
}
