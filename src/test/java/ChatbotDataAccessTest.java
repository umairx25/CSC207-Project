import data_access.chatbot.ChatbotDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.chatBot.ChatbotDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;

class ChatbotDataAccessTest {
    private ChatbotDataAccessInterface dataAccess;

    @BeforeEach
    void setUp() {
        // Instantiate the ChatbotDataAccess
        dataAccess = new ChatbotDataAccess();
    }

    @Test
    void testFetchValidResponse() {
        try {
            // Valid input scenario
            String prompt = "Hello";
            String response = dataAccess.fetchResponse(prompt);

            // Assert the response is not null or empty
            assertNotNull(response, "Response should not be null for a valid input");
            assertFalse(response.isEmpty(), "Response should not be empty for a valid input");

        } catch (Exception e) {
            fail("Exception should not be thrown for valid input: " + e.getMessage());
        }
    }

    @Test
    void testFetchInvalidResponse() {
        assertThrows(Exception.class, () -> {
            // Invalid input scenario (simulate a null or malformed prompt)
            String prompt = null;
            dataAccess.fetchResponse(prompt);
        }, "Expected an exception for invalid input (null prompt)");
    }
}
