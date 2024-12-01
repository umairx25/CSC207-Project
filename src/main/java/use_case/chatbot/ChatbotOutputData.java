package use_case.chatbot;

public class ChatbotOutputData {
    private final String responseMessage;

    public ChatbotOutputData(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
