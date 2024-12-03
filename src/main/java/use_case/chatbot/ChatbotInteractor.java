package use_case.chatbot;

import org.jetbrains.annotations.NotNull;

/**
 * Interactor for the chatbot use case.
 * Handles processing of user input and generating bot responses.
 */
public class ChatbotInteractor implements ChatbotInputBoundary {
    private final ChatbotOutputBoundary outputBoundary;
    private final ChatbotDataAccessInterface dataAccess;

    /**
     * Constructs a ChatbotInteractor with the specified output boundary and data access.
     *
     * @param outputBoundary The presenter responsible for sending results to the UI.
     * @param dataAccess     The data access interface for fetching responses.
     */
    public ChatbotInteractor(ChatbotOutputBoundary outputBoundary, ChatbotDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }

    /**
     * Processes the input data and generates the chatbot's response.
     *
     * @param inputData The input data containing the user message.
     * @return The chatbot's response as a string.
     */
    @Override
    public String processInput(ChatbotInputData inputData) {
        String prompt = getPrompt(inputData);

        try {
            String response = dataAccess.fetchResponse(prompt);

            if ("FALSE".equalsIgnoreCase(response)) {
                response = "Sorry, either the question is not finance-related or it requires real-time data.";
            }

            ChatbotOutputData outputData = new ChatbotOutputData(response);
            outputBoundary.presentOutput(outputData);

            return response;

        } catch (Exception e) {
            String errorMessage = switch (e.getClass().getSimpleName()) {
                case "IOException" -> "Sorry, a network or API error occurred. Please try again later.";
                case "JsonSyntaxException" -> "Sorry, the response from the AI could not be processed. Please contact support.";
                default -> "Sorry, an unexpected error occurred. Try again later.";
            };
            ChatbotOutputData errorData = new ChatbotOutputData(errorMessage);
            outputBoundary.presentOutput(errorData);
            return errorMessage;
        }
    }

    /**
     * Generates a prompt from the user input for processing.
     *
     * @param inputData The user input data.
     * @return A formatted prompt string.
     */
    @NotNull
    public static String getPrompt(ChatbotInputData inputData) {
        String userMessage = inputData.message();
        return "Analyze the following question and provide the appropriate response:\n"
                + "Question: \"" + userMessage + "\"\n"
                + "Instructions:\n"
                + "- You are 'Bullbot', a finance chatbot. If the input is a greeting or asks who you are, respond warmly.\n"
                + "- If the question is not finance-related or requires real-time data, respond with the word 'FALSE.'\n"
                + "- Otherwise, answer the question efficiently and briefly.\n"
                + "- Do NOT include labels like 'Response:', 'Answer:', or similar in your reply.\n"
                + "- Keep the response professional and concise.";
    }

}
