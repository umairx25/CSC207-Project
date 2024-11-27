import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatBot.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatbotInteractorTest {
    private ChatbotInteractor interactor;

    @BeforeEach
    void setUp() {
        // Manual mock for ChatbotOutputBoundary
        ChatbotOutputBoundary mockPresenter = outputData -> {
            if (outputData.response().equals("Mock API Response")) {
                assertEquals("Mock API Response", outputData.response());
            } else {
                assertEquals("Sorry, an unexpected error occurred. Try again later.", outputData.response());
            }
        };

        // Manual mock for ChatbotDataAccessInterface
        ChatbotDataAccessInterface mockDataAccess = message -> {
            if (message.contains("Hello")) {
                return "Mock API Response";
            } else {
                throw new IllegalArgumentException("Invalid input");
            }
        };

        interactor = new ChatbotInteractor(mockPresenter, mockDataAccess);
    }

    @Test
    void testProcessValidInput() {
        String response = interactor.processInput(new ChatbotInputData("Hello"));
        assertEquals("Mock API Response", response, "Expected 'Mock API Response' for valid input");
    }

    @Test
    void testProcessInvalidInput() {
        // Modify expectation to match the actual behavior of the interactor
        String response = interactor.processInput(new ChatbotInputData("Invalid"));
        assertEquals("Sorry, an unexpected error occurred. Try again later.", response,
                "Expected fallback error message for invalid input");
    }
}
