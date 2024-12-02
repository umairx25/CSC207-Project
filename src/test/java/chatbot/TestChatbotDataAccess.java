package chatbot;

import use_case.chatbot.ChatbotDataAccessInterface;

public class TestChatbotDataAccess implements ChatbotDataAccessInterface {
    private String responseToReturn;
    private boolean throwException;
    private Exception exceptionToThrow;

    public void setResponseToReturn(String response) {
        this.responseToReturn = response;
    }

    public void setExceptionToThrow(Exception exception) {
        this.throwException = true;
        this.exceptionToThrow = exception;
    }

    @Override
    public String fetchResponse(String message) throws Exception {
        if (throwException) {
            throw exceptionToThrow;
        }
        return responseToReturn;
    }
}
