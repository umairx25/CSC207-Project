package use_case.chatbot;

import data_access.ChatbotDataAccess;

public class ChatbotInteractor implements ChatbotInputBoundary {
    private final ChatbotDataAccess dataAccess;
    private final ChatbotOutputBoundary outputBoundary;

    public ChatbotInteractor(ChatbotDataAccess dataAccess, ChatbotOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void processMessage(ChatbotInputData inputData) {
        // Call the data access layer to get the AI response
        String response = dataAccess.getAIResponse(inputData.getUserMessage());

        // Prepare output data
        ChatbotOutputData outputData = new ChatbotOutputData(response);

        // Pass the output to the presenter
        outputBoundary.presentResponse(outputData);
    }
}
