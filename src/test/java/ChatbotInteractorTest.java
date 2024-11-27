import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatBot.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for ChatbotInteractor.
 */
class ChatbotInteractorTest {
    private ChatbotInteractor interactor;

    /**
     * Sets up mock dependencies for ChatbotInteractor.
     */
    @BeforeEach
    void setUp() {
        ChatbotOutputBoundary mockPresenter = outputData -> {
            if (outputData.response().equals("Mock API Response")) {
                assertEquals("Mock API Response", outputData.response());
            } else {
                assertEquals("Sorry, an unexpected error occurred. Try again later.", outputData.response());
            }
        };

        ChatbotDataAccessInterface mockDataAccess = message -> {
            if (message.contains("Hello")) {
                return "Mock API Response";
            } else {
                throw new IllegalArgumentException("Invalid input");
            }
        };

        interactor = new ChatbotInteractor(mockPresenter, mockDataAccess);
    }

    /**
     * Tests processing of valid input.
     */
    @Test
    void testProcessValidInput() {
        String response = interactor.processInput(new ChatbotInputData("Hello"));
        assertEquals("Mock API Response", response, "Expected 'Mock API Response' for valid input");
    }

    /**
     * Tests processing of invalid input.
     */
    @Test
    void testProcessInvalidInput() {
        String response = interactor.processInput(new ChatbotInputData("Invalid"));
        assertEquals("Sorry, an unexpected error occurred. Try again later.", response,
                "Expected fallback error message for invalid input");
    }
}
