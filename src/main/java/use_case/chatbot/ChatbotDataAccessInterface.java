package use_case.chatbot;

/**
 * Interface for chatbot data access.
 * Provides methods for interacting with external data sources.
 */
public interface ChatbotDataAccessInterface {
    /**
     * Fetches a response from the external data source based on the provided message.
     *
     * @param message The user message to process.
     * @return The response from the external data source.
     * @throws Exception If an error occurs during data retrieval.
     */
    String fetchResponse(String message) throws Exception;
}