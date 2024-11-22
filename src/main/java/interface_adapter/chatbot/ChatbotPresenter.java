
package interface_adapter.chatbot;

import interface_adapter.chatbot.ChatbotViewModel;
import use_case.chatbot.ChatbotOutputBoundary;
import use_case.chatbot.ChatbotOutputData;

public class ChatbotPresenter implements ChatbotOutputBoundary {
    private final ChatbotViewModel viewModel;

    public ChatbotPresenter(ChatbotViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentResponse(ChatbotOutputData outputData) {
        // Update the ViewModel with the response
        viewModel.setResponse(outputData.getResponseMessage());
    }
}
