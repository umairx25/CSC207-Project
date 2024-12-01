package use_case.chatBot;

import data_access.ChatbotDataAccess;
import use_case.chatBot.ChatbotInputBoundary;

public class ChatbotInteractor implements use_case.chatBot.ChatbotInputBoundary {
    private final ChatbotDataAccess dataAccess;
    private final use_case.chatBot.ChatbotOutputBoundary outputBoundary;

    public ChatbotInteractor(ChatbotDataAccess dataAccess, use_case.chatBot.ChatbotOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void processMessage(use_case.chatBot.ChatbotInputData inputData) {
        // Call the data access layer to get the AI response
        String response = dataAccess.getAIResponse(inputData.getUserMessage());

        // Prepare output data
        use_case.chatBot.ChatbotOutputData outputData = new ChatbotOutputData(response);

        // Pass the output to the presenter
        outputBoundary.presentResponse(outputData);
    }
}
