package ai;

import javax.swing.*;
import java.awt.*;

import view.FontManager;
import view.ColourManager;
import view.ImageManager;

public class ChatBotPanel extends JPanel {
    private final ChatPanel chatPanel;
    private final HeaderPanel headerPanel;
    private final InputPanel inputPanel;
    private final MessageProcessor messageProcessor;

    public ChatBotPanel(AiClient aiClient) {
        ChatService chatService = new ChatService(aiClient);
        messageProcessor = new MessageProcessor(chatService);
        setLayout(new BorderLayout());

        headerPanel = new HeaderPanel(
                "BullBot",
                ImageManager.getImage("chatbot_pfp"),
                FontManager.OUTFIT_BOLD_16,
                FontManager.OUTFIT_REGULAR_10,
                ColourManager.DARKER_GRAY
        );
        chatPanel = new ChatPanel();
        inputPanel = new InputPanel(e -> sendMessage(), "Type Message Here...");

        add(headerPanel, BorderLayout.NORTH);
        add(chatPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String placeholderText = "Type Message Here...";
        String message = retrieveMessage(placeholderText);

        if (message == null) return;

        displayUserMessage(message);
        disableInputAndShowTyping();

        // Use MessageProcessor for business logic
        messageProcessor.processMessage(message, new ChatService.ChatCallback() {
            @Override
            public void onSuccess(String aiResponse) {
                handleAiResponse(aiResponse);
            }

            @Override
            public void onError(String errorMessage) {
                handleError(errorMessage);
            }
        });
    }

    private String retrieveMessage(String placeholderText) {
        return inputPanel.getMessage(placeholderText);
    }

    private void displayUserMessage(String message) {
        chatPanel.addMessage(message, true);
        inputPanel.resetField("Type Message Here...");
    }

    private void disableInputAndShowTyping() {
        inputPanel.setInputEnabled(false);
        updateTypingIndicator(true);
    }

    private void handleAiResponse(String aiResponse) {
        updateTypingIndicator(false);
        chatPanel.addMessage(aiResponse, false);
        inputPanel.setInputEnabled(true);
    }

    private void handleError(String errorMessage) {
        updateTypingIndicator(false);
        chatPanel.addMessage(errorMessage, false);
        inputPanel.setInputEnabled(true);
    }

    private void updateTypingIndicator(boolean isVisible) {
        headerPanel.setTypingIndicatorVisible(isVisible);
    }
}
