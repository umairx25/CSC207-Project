package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

        ChatbotHeaderView headerPanel = new ChatbotHeaderView(builder);
        add(headerPanel, BorderLayout.NORTH);

        messagePanel = new ChatbotMessageView();
        JScrollPane messageScrollPane = new JScrollPane(messagePanel);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(messageScrollPane, BorderLayout.CENTER);

        inputPanel = new ChatbotInputView(_ -> sendMessage(), "Type your message here...");
        add(inputPanel, BorderLayout.SOUTH);

        viewModel.setResponseHandler(response -> messagePanel.addMessage(response, false));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    private void sendMessage() {
        String placeholderText = "Type your message here...";
        String userMessage = inputPanel.getMessage(placeholderText);

        if (userMessage == null || userMessage.isEmpty()) return;

        messagePanel.addMessage(userMessage, true);

        inputPanel.resetField(placeholderText);
        inputPanel.setEnabled(false);

        ChatbotHeaderView headerPanel = (ChatbotHeaderView) getComponent(0);
        headerPanel.setTypingIndicatorVisible(true);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                controller.handleInput(userMessage);
                return null;
            }

            @Override
            protected void done() {
                headerPanel.setTypingIndicatorVisible(false);
                inputPanel.setEnabled(true);
            }
        };

        worker.execute();
    }
}
