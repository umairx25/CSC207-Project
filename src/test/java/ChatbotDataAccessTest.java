import data_access.chatbot.ChatbotDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatBot.ChatbotDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ChatbotDataAccess.
 */
class ChatbotDataAccessTest {
    private ChatbotDataAccessInterface dataAccess;

    /**
     * Sets up the ChatbotDataAccess for testing.
     */
    @BeforeEach
    void setUp() {
        dataAccess = new ChatbotDataAccess();
    }

    /**
     * Tests fetching a valid response from the data access layer.
     */
    @Test
    void testFetchValidResponse() {
        try {
            String prompt = "Hello";
            String response = dataAccess.fetchResponse(prompt);

            assertNotNull(response, "Response should not be null for a valid input");
            assertFalse(response.isEmpty(), "Response should not be empty for a valid input");
        } catch (Exception e) {
            fail("Exception should not be thrown for valid input: " + e.getMessage());
        }
    }

    /**
     * Tests handling of invalid input when fetching a response.
     */
    @Test
    void testFetchInvalidResponse() {
        assertThrows(Exception.class, () -> {
            String prompt = null;
            dataAccess.fetchResponse(prompt);
        }, "Expected an exception for invalid input (null prompt)");
    }
}
