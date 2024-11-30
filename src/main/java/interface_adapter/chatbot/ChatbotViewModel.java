package interface_adapter.chatbot;

import java.util.function.Consumer;

/**
 * ViewModel for the chatbot UI.
 * Stores and provides data for the view components.
 */
public class ChatbotViewModel {
    private Consumer<String> responseHandler;

    /**
     * Sets the response handler for updating the UI with new chatbot responses.
     *
     * @param responseHandler A consumer function to handle chatbot responses.
     */
    public void setResponseHandler(Consumer<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    /**
     * Updates the UI with the chatbot's response using the response handler.
     *
     * @param response The chatbot's response message.
     */
    public void updateResponse(String response) {
        if (responseHandler != null) {
            responseHandler.accept(response);
        }
    }
}
