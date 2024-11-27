package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotViewModel;

/**
 * Main container view for the chatbot UI.
 * Combines the header, message display, and input components.
 */
public class ChatbotContainerView extends JPanel {
    private final ChatbotHeaderView headerPanel;
    private final ChatbotMessageView messagePanel;
    private final ChatbotInputView inputPanel;
    private final ChatbotController controller;

    /**
     * Constructs the chatbot container view.
     *
     * @param controller The controller to handle user input.
     * @param viewModel  The view model to manage chatbot state and responses.
     */
    public ChatbotContainerView(ChatbotController controller, ChatbotViewModel viewModel) {
        this.controller = controller;

        setLayout(new BorderLayout());

        headerPanel = new ChatbotHeaderView();
        add(headerPanel, BorderLayout.NORTH);

        messagePanel = new ChatbotMessageView();
        add(messagePanel, BorderLayout.CENTER);

        viewModel.setResponseHandler(response -> messagePanel.addMessage(response, false));

        inputPanel = new ChatbotInputView(e -> sendMessage(), "Type your message here...");
        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles sending a user message and updating the UI with the response.
     */
    private void sendMessage() {
        String placeholderText = "Type your message here...";
        String userMessage = inputPanel.getMessage(placeholderText);

        if (userMessage == null || userMessage.isEmpty()) return;

        messagePanel.addMessage(userMessage, true);
        inputPanel.resetField(placeholderText);
        inputPanel.setEnabled(false);
        headerPanel.setTypingIndicatorVisible(true);

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return controller.handleInput(userMessage);
            }

            @Override
            protected void done() {
                try {
                    String botResponse = get();
                    messagePanel.addMessage(botResponse, false);
                } catch (Exception e) {
                    messagePanel.addMessage("Error: Could not process response.", false);
                } finally {
                    inputPanel.setEnabled(true);
                    headerPanel.setTypingIndicatorVisible(false);
                }
            }
        };

        worker.execute();
    }
}
