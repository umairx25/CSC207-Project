package use_case.chatbot;

public class ChatbotInputData {
    private final String userMessage;

    public ChatbotInputData(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
