package interface_adapter.chatbot;

import use_case.chatbot.ChatbotOutputBoundary;
import use_case.chatbot.ChatbotOutputData;

/**
 * Presenter for the chatbot use case.
 * Prepares the chatbot output data for the UI layer.
 */
public class ChatbotPresenter implements ChatbotOutputBoundary {
    private final ChatbotViewModel viewModel;

    /**
     * Constructs a ChatbotPresenter with the specified ViewModel.
     *
     * @param viewModel The ViewModel used to update the UI.
     */
    public ChatbotPresenter(ChatbotViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the ViewModel with the processed output data.
     *
     * @param outputData The output data generated by the interactor.
     */
    @Override
    public void presentOutput(ChatbotOutputData outputData) {
        viewModel.updateResponse(outputData.response());
    }
}