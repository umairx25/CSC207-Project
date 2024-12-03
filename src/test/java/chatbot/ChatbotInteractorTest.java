package chatbot;

import org.junit.jupiter.api.Test;
import use_case.chatbot.ChatbotInputData;
import use_case.chatbot.ChatbotInteractor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChatbotInteractorTest {
    @Test
    void testValidResponsePath() throws Exception {
        // Arrange
        TestChatbotOutputBoundary outputBoundary = new TestChatbotOutputBoundary();
        TestChatbotDataAccess dataAccess = new TestChatbotDataAccess();
        dataAccess.setResponseToReturn("This is a valid response.");
        ChatbotInteractor interactor = new ChatbotInteractor(outputBoundary, dataAccess);

        ChatbotInputData inputData = new ChatbotInputData("Hello!");

        // Act
        String response = interactor.processInput(inputData);

        // Assert
        assertEquals("This is a valid response.", response);
        assertEquals("This is a valid response.", outputBoundary.getLastOutput().response());
    }

    @Test
    void testFalseResponsePath() throws Exception {
        // Arrange
        TestChatbotOutputBoundary outputBoundary = new TestChatbotOutputBoundary();
        TestChatbotDataAccess dataAccess = new TestChatbotDataAccess();
        dataAccess.setResponseToReturn("FALSE");
        ChatbotInteractor interactor = new ChatbotInteractor(outputBoundary, dataAccess);

        ChatbotInputData inputData = new ChatbotInputData("Is this a finance question?");

        // Act
        String response = interactor.processInput(inputData);

        // Assert
        assertEquals("Sorry, either the question is not finance-related or it requires real-time data.", response);
        assertEquals("Sorry, either the question is not finance-related or it requires real-time data.", outputBoundary.getLastOutput().response());
    }

    @Test
    void testIOExceptionHandling() throws Exception {
        // Arrange
        TestChatbotOutputBoundary outputBoundary = new TestChatbotOutputBoundary();
        TestChatbotDataAccess dataAccess = new TestChatbotDataAccess();
        dataAccess.setExceptionToThrow(new IOException("Network error"));
        ChatbotInteractor interactor = new ChatbotInteractor(outputBoundary, dataAccess);

        ChatbotInputData inputData = new ChatbotInputData("Fetch this!");

        // Act
        String response = interactor.processInput(inputData);

        // Assert
        assertEquals("Sorry, a network or API error occurred. Please try again later.", response);
        assertEquals("Sorry, a network or API error occurred. Please try again later.", outputBoundary.getLastOutput().response());
    }

    @Test
    void testGenericExceptionHandling() throws Exception {
        // Arrange
        TestChatbotOutputBoundary outputBoundary = new TestChatbotOutputBoundary();
        TestChatbotDataAccess dataAccess = new TestChatbotDataAccess();
        dataAccess.setExceptionToThrow(new RuntimeException("Unexpected error"));
        ChatbotInteractor interactor = new ChatbotInteractor(outputBoundary, dataAccess);

        ChatbotInputData inputData = new ChatbotInputData("Something unexpected");

        // Act
        String response = interactor.processInput(inputData);

        // Assert
        assertEquals("Sorry, an unexpected error occurred. Try again later.", response);
        assertEquals("Sorry, an unexpected error occurred. Try again later.", outputBoundary.getLastOutput().response());
    }

    @Test
    void testPromptGeneration() {
        // Arrange
        ChatbotInputData inputData = new ChatbotInputData("What is 2 + 2?");

        // Act
        String prompt = ChatbotInteractor.getPrompt(inputData);

        // Assert
        String expectedPrompt = """
            Analyze the following question and provide the appropriate response:
            Question: "What is 2 + 2?"
            If it is a greeting or asking who you are, respond with something along the lines of that your name is Bullbot and you're here to help with finance questions.
            If the question is not finance-related or requires real-time data, respond with the word FALSE.
            Else: answer the question efficiently and briefly.""";
        assertEquals(expectedPrompt, prompt);
    }
}
