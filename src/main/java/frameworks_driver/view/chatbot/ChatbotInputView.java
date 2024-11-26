package frameworks_driver.view.chatbot;

import view.ColourManager;
import view.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatbotInputView extends JPanel {
    private final JTextField messageField;

    public ChatbotInputView(ActionListener sendAction, String placeholderText) {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(500, 60));

        // Create and style the text field
        messageField = new JTextField();
        messageField.setForeground(ColourManager.WHITE);
        messageField.setCaretColor(ColourManager.WHITE);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        // Create the send button with an image
        JButton sendButton = new JButton();
        sendButton.setIcon(ImageManager.getImage("send_icon")); // Replace with your actual image key or path
        sendButton.setPreferredSize(new Dimension(60, 60));
        sendButton.setFocusPainted(false);

// Set a custom background color
        sendButton.setBackground(ColourManager.DARKER_GRAY); // Ensure DARKER_GRAY is defined in ColourManager

// Retain button borders and transparency
        sendButton.setOpaque(true); // Ensures the background color is applied
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        sendButton.addActionListener(sendAction);


        // Add components to the panel
        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    public String getMessage(String placeholderText) {
        String message = messageField.getText().trim();
        return message.isEmpty() || message.equals(placeholderText) ? null : message;
    }

    public void resetField(String placeholderText) {
        messageField.setText(placeholderText);
        messageField.setForeground(Color.WHITE);
    }
}
