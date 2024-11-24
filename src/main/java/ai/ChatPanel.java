package ai;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private final JPanel messagePanel;
    private final JScrollPane scrollPane;

    public ChatPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create the inner message panel
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        // Wrap it in a scroll pane
        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        // Customize the scrollbar appearance
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY; // Replace with ColourManager if needed
            }
        });

        add(scrollPane, BorderLayout.CENTER);

        // Add the default introduction message
        addDefaultMessage();
    }

    // Method to add the default introduction message
    private void addDefaultMessage() {
        String defaultMessage = "Hey there! This is BullBot, StockFlow's own chatbot powered by AI. "
                + "Feel free to ask me questions about anything finance-related. "
                + "Please note that I do not have access to real-time information.";
        addMessage(defaultMessage, false); // Add the default message as an AI message
    }

    // Method to add a message bubble
    public void addMessage(String message, boolean isSender) {
        MessageBubble bubble = new MessageBubble(message, isSender);
        JPanel bubbleContainer = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 15));
        bubbleContainer.setOpaque(false);
        bubbleContainer.add(bubble);

        messagePanel.add(bubbleContainer);
        messagePanel.revalidate();
        messagePanel.repaint();

        scrollToBottom();
    }

    // Scroll to the bottom of the chat
    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });
    }
}
