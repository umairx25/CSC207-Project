package use_case.chatBot;

/**
 * Interactor for the chatbot use case.
 * Handles the main logic for processing input and generating output.
 */
public class ChatbotInteractor implements ChatbotInputBoundary {
    private final ChatbotOutputBoundary outputBoundary;
    private final ChatbotDataAccessInterface dataAccess;

    public ChatbotInteractor(ChatbotOutputBoundary outputBoundary, ChatbotDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }

    @Override
    public void processInput(ChatbotInputData inputData) {
        try {
            String response = dataAccess.fetchResponse(inputData.getMessage());
            ChatbotOutputData outputData = new ChatbotOutputData(response);
            outputBoundary.presentOutput(outputData);
        } catch (Exception e) {
            ChatbotOutputData errorData = new ChatbotOutputData("Error processing your request.");
            outputBoundary.presentOutput(errorData);
        }
    }
}
