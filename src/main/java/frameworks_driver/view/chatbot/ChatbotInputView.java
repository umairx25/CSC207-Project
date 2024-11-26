package frameworks_driver.view.chatbot;

import view.ColourManager;
import view.GridBagManager;
import view.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatbotInputView extends JPanel {
    private final JTextField messageField;

    public ChatbotInputView(ActionListener sendAction, String placeholderText) {
        setLayout(new BorderLayout());
        setBackground(ColourManager.MEDIUM_GRAY);
        setPreferredSize(GridBagManager.INPUT_SIZE);

        // Create and style the text field
        messageField = new JTextField();
        messageField.setForeground(ColourManager.WHITE);
        messageField.setCaretColor(ColourManager.WHITE);
        messageField.setBorder(GridBagManager.INPUT_BORDER);
        messageField.setBackground(ColourManager.DARK_GRAY);
        messageField.setText(placeholderText);

        messageField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(placeholderText)) {
                    messageField.setText("");
                    messageField.setForeground(ColourManager.WHITE);
                }
            }

            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(placeholderText);
                }
            }
        });

        JButton sendButton = new JButton();
        sendButton.setIcon(ImageManager.getImage("send_icon")); // Replace with your actual image key or path
        sendButton.setPreferredSize(GridBagManager.SEND_MSG_SIZE);
        sendButton.setFocusPainted(false);

        sendButton.setBackground(ColourManager.DARKER_GRAY); // Ensure DARKER_GRAY is defined in ColourManager

        sendButton.setOpaque(true); // Ensures the background color is applied
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        sendButton.addActionListener(sendAction);

        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    public String getMessage(String placeholderText) {
        String message = messageField.getText().trim();
        return message.isEmpty() || message.equals(placeholderText) ? null : message;
    }

    public void resetField(String placeholderText) {
        messageField.setText(placeholderText);
        messageField.setForeground(ColourManager.WHITE);
    }
}
