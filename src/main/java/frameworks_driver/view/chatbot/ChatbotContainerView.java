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

    public ChatbotContainerView(ChatbotController controller, ChatbotViewModel viewModel) {
        this.controller = controller; // Connects the view to the backend

        setLayout(new BorderLayout());

        // Create and add header panel
        headerPanel = new ChatbotHeaderView();
        add(headerPanel, BorderLayout.NORTH);

        // Create and add message panel
        messagePanel = new ChatbotMessageView();
        add(messagePanel, BorderLayout.CENTER);

        // Bind ViewModel to update messages
        viewModel.setResponseHandler(response -> messagePanel.addMessage(response, false));

        // Create and add input panel
        inputPanel = new ChatbotInputView(e -> sendMessage(), "Type your message here...");
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String placeholderText = "Type your message here...";
        String userMessage = inputPanel.getMessage(placeholderText);

        if (userMessage == null || userMessage.isEmpty()) return;

        // Display user message
        messagePanel.addMessage(userMessage, true);

        // Clear input field
        inputPanel.resetField(placeholderText);

        // Disable input while waiting for the response
        inputPanel.setEnabled(false);
        headerPanel.setTypingIndicatorVisible(true);

        // Background task for handling the response
        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                // Process the user's input and get the bot's response
                return controller.handleInput(userMessage);
            }

            @Override
            protected void done() {
                try {
                    // Get the bot's response
                    String botResponse = get();

                } catch (Exception e) {
                    // Handle errors (e.g., logging)
                    messagePanel.addMessage("Error: Could not process response.", false);
                } finally {
                    // Re-enable input and hide typing indicator
                    inputPanel.setEnabled(true);
                    headerPanel.setTypingIndicatorVisible(false);
                }
            }
        };

        // Start the background task
        worker.execute();
    }

}
