package use_case.chatBot;

/**
 * Data structure for encapsulating chatbot input data.
 */
public class ChatbotInputData {
    private final String message;

    public ChatbotInputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
