package chatbot;

import use_case.chatbot.ChatbotOutputBoundary;
import use_case.chatbot.ChatbotOutputData;

public class TestChatbotOutputBoundary implements ChatbotOutputBoundary {
    private ChatbotOutputData lastOutput;

    @Override
    public void presentOutput(ChatbotOutputData outputData) {
        this.lastOutput = outputData;
    }

    public ChatbotOutputData getLastOutput() {
        return lastOutput;
    }
}
