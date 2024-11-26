package interface_adapter.chatbot;

/**
 * View model for the chatbot UI.
 * Stores data to be displayed on the interface.
 */
public class ChatbotViewModel {
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
