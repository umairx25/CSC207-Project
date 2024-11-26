package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotViewModel;
import view.ColourManager;
import view.FontManager;
import view.ImageManager;

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
        headerPanel = new ChatbotHeaderView(
                "BullBot",
                ImageManager.getImage("chatbot_pfp"),
                FontManager.OUTFIT_BOLD_16,
                FontManager.OUTFIT_REGULAR_10,
                ColourManager.DARKER_GRAY
        );
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

        // Send message to backend and handle the response
        String botResponse = controller.handleInput(userMessage); // Assuming this method returns a response

        // Update UI with bot's response
        messagePanel.addMessage(botResponse, false);

        // Re-enable input and hide typing indicator
        inputPanel.setEnabled(true);
        headerPanel.setTypingIndicatorVisible(false);
    }


}
