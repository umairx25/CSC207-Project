package interface_adapter.chatbot;

import java.util.function.Consumer;

/**
 * ViewModel for the chatbot UI.
 * Stores and provides data to the view.
 */
public class ChatbotViewModel {
    private Consumer<String> responseHandler;

    public void setResponseHandler(Consumer<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void updateResponse(String response) {
        if (responseHandler != null) {
            responseHandler.accept(response);
        }
    }
}