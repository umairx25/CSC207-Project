package use_case.chatBot;

/**
 * Data structure for encapsulating chatbot output data.
 */
public class ChatbotOutputData {
    private final String response;

    public ChatbotOutputData(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
