package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;

import app.Builder;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotViewModel;

/**
 * Main container view for the chatbot UI.
 * Combines the header, message display, and input components.
 */

public class ChatbotContainerView extends JPanel {
    private final ChatbotMessageView messagePanel;
    private final ChatbotInputView inputPanel;
    private final ChatbotController controller;

    public ChatbotContainerView(ChatbotController controller, ChatbotViewModel viewModel, Builder builder) {
        this.controller = controller;

        setLayout(new BorderLayout());

        // Header with Back button
        ChatbotHeaderView headerPanel = new ChatbotHeaderView(builder);
        add(headerPanel, BorderLayout.NORTH);

        // Message display
        messagePanel = new ChatbotMessageView();
        messagePanel.setBorder(BorderFactory.createLineBorder(Color.RED)); // Debug border
        add(messagePanel, BorderLayout.CENTER);

        // Input field
        inputPanel = new ChatbotInputView(e -> sendMessage(), "Type your message here...");
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Debug border
        add(inputPanel, BorderLayout.SOUTH);

        viewModel.setResponseHandler(response -> messagePanel.addMessage(response, false));
    }

    private void sendMessage() {
        String placeholderText = "Type your message here...";
        String userMessage = inputPanel.getMessage(placeholderText);

        if (userMessage == null || userMessage.isEmpty()) return;

        messagePanel.addMessage(userMessage, true);
        inputPanel.resetField(placeholderText);
        inputPanel.setEnabled(false);

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                return controller.handleInput(userMessage);
            }

            @Override
            protected void done() {
                inputPanel.setEnabled(true);
            }
        };

        worker.execute();
    }
}
