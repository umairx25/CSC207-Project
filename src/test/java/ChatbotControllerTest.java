import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatbot.ChatbotInputBoundary;
import interface_adapter.chatbot.ChatbotController;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for ChatbotController.
 */
class ChatbotControllerTest {
    private ChatbotController controller;

    /**
     * Sets up a mock ChatbotInputBoundary for testing.
     */
    @BeforeEach
    void setUp() {
        ChatbotInputBoundary mockBoundary = inputData -> {
            if (inputData.message().equals("Hello")) {
                return "Mocked Response";
            } else {
                return "Unknown input";
            }
        };

        controller = new ChatbotController(mockBoundary);
    }

    /**
     * Tests handling of valid input.
     */
    @Test
    void testHandleValidInput() {
        String response = controller.handleInput("Hello");
        assertEquals("Mocked Response", response, "Expected 'Mocked Response' for input 'Hello'");
    }

    /**
     * Tests handling of invalid input.
     */
    @Test
    void testHandleInvalidInput() {
        String response = controller.handleInput("Unknown");
        assertEquals("Unknown input", response, "Expected 'Unknown input' for invalid input");
    }
}
