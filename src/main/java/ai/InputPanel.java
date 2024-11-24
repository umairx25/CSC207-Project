package ai;

import view.ColourManager;
import view.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputPanel extends JPanel {
    private final JTextField messageField;
    private final JButton sendButton;

    public InputPanel(ActionListener sendAction, String placeholderText) {
        setLayout(new BorderLayout());
        setBackground(ColourManager.DARKER_GRAY);
        setPreferredSize(new Dimension(500, 60));

        // Create the text field
        messageField = new JTextField();
        messageField.setForeground(Color.WHITE);
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageField.setBackground(ColourManager.MEDIUM_GRAY);
        messageField.setText(placeholderText);
        messageField.setForeground(Color.GRAY);

        // Placeholder text handling
        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(placeholderText)) {
                    messageField.setText("");
                    messageField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(placeholderText);
                    messageField.setForeground(Color.GRAY);
                }
            }
        });

        // Create the send button
        sendButton = new JButton(ImageManager.getImage("send_icon"));
        sendButton.setPreferredSize(new Dimension(60, 60));
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder());

        // Add action listeners
        sendButton.addActionListener(sendAction);
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendAction.actionPerformed(null);
                }
            }
        });

        // Add components to the panel
        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    // Method to reset the message field to the placeholder
    public void resetField(String placeholderText) {
        messageField.setText("");
        messageField.setForeground(Color.GRAY);
        messageField.setText(placeholderText);
    }

    // Method to get the user-entered message
    public String getMessage(String placeholderText) {
        String message = messageField.getText().trim();
        if (message.isEmpty() || message.equals(placeholderText)) {
            return null;
        }
        return message;
    }

    // Disable the input field (useful while waiting for a response)
    public void setInputEnabled(boolean isEnabled) {
        messageField.setEnabled(isEnabled);
    }
}
