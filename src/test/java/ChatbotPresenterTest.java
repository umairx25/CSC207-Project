import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import use_case.chatBot.ChatbotOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for ChatbotPresenter.
 */
class ChatbotPresenterTest {
    private ChatbotPresenter presenter;
    private ChatbotViewModel viewModel;

    /**
     * Sets up the ViewModel and Presenter for testing.
     */
    @BeforeEach
    void setUp() {
        viewModel = new ChatbotViewModel();
        presenter = new ChatbotPresenter(viewModel);
    }

    /**
     * Tests presenting output data to the ViewModel.
     */
    @Test
    void testPresentOutput() {
        final String[] updatedResponse = {null};
        viewModel.setResponseHandler(response -> updatedResponse[0] = response);

        presenter.presentOutput(new ChatbotOutputData("Test Response"));
        assertEquals("Test Response", updatedResponse[0], "Expected ViewModel to receive 'Test Response'");
    }
}
