import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatBot.ChatbotInputBoundary;
import use_case.chatBot.ChatbotInputData;
import interface_adapter.chatbot.ChatbotController;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatbotControllerTest {
    private ChatbotController controller;

    @BeforeEach
    void setUp() {
        // Create a manual mock for ChatbotInputBoundary
        ChatbotInputBoundary mockBoundary = inputData -> {
            if (inputData.message().equals("Hello")) {
                return "Mocked Response";
            } else {
                return "Unknown input";
            }
        };

        controller = new ChatbotController(mockBoundary);
    }

    @Test
    void testHandleValidInput() {
        String response = controller.handleInput("Hello");
        assertEquals("Mocked Response", response, "Expected 'Mocked Response' for input 'Hello'");
    }

    @Test
    void testHandleInvalidInput() {
        String response = controller.handleInput("Unknown");
        assertEquals("Unknown input", response, "Expected 'Unknown input' for invalid input");
    }
}
