package use_case.chatBot;

import org.jetbrains.annotations.NotNull;

/**
 * Interactor for the chatbot use case.
 * Handles processing of user input and generating bot responses.
 */
public class ChatbotInteractor implements ChatbotInputBoundary {
    private final ChatbotOutputBoundary outputBoundary; // Presenter for sending results to the UI
    private final ChatbotDataAccessInterface dataAccess; // Handles data access (API calls, etc.)

    public ChatbotInteractor(ChatbotOutputBoundary outputBoundary, ChatbotDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }

    @Override
    public void processInput(ChatbotInputData inputData) {
        String prompt = getPrompt(inputData);

        try {
            // Fetch response from the Data Access layer
            String response = dataAccess.fetchResponse(prompt);

            // Process the response
            if ("FALSE".equalsIgnoreCase(response)) {
                response = "Sorry, either the question is not finance-related or it requires real-time data.";
            }

            // Prepare output data
            ChatbotOutputData outputData = new ChatbotOutputData(response);

            // Send output to presenter
            outputBoundary.presentOutput(outputData);

        } catch (Exception e) {
            // Handle errors and send a fallback error message to the presenter
            String errorMessage = switch (e.getClass().getSimpleName()) {
                case "IOException" -> "Sorry, a network or API error occurred. Please try again later.";
                case "JsonSyntaxException" -> "Sorry, the response from the AI could not be processed. Please contact support.";
                default -> "Sorry, an unexpected error occurred. Try again later.";
            };
            ChatbotOutputData errorData = new ChatbotOutputData(errorMessage);
            outputBoundary.presentOutput(errorData);
        }
    }

    @NotNull
    private static String getPrompt(ChatbotInputData inputData) {
        String userMessage = inputData.message();

        // Logic for evaluating and responding
        return "Analyze the following question and provide the appropriate response:\n"
                + "Question: \"" + userMessage + "\"\n"
                + "If it is a greeting or asking who you are respond with something along the lines of that your name is Bullbot and you're here to help with finance questions.\n"
                + "If the question is not finance-related (Make sure it is an appropriate Finance question) or if it requires real-time data, respond with the word FALSE.\n"
                + "Else: answer the question efficiently and briefly.";
    }
}
