package interface_adapter.chatbot;

import use_case.chatBot.ChatbotOutputBoundary;
import use_case.chatBot.ChatbotOutputData;

/**
 * Presenter for the chatbot use case.
 * Prepares output data for the UI layer.
 */
public class ChatbotPresenter implements ChatbotOutputBoundary {
    private final ChatbotViewModel viewModel;

    public ChatbotPresenter(ChatbotViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentOutput(ChatbotOutputData outputData) {
        // Format the response (if necessary) and update the view model
        String formattedResponse = outputData.getResponse(); // Apply any formatting here
        viewModel.setResponse(formattedResponse);
    }
}
