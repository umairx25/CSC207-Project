package frameworks_driver.view.chatbot;

import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.GridBagManager;
import frameworks_driver.view.style_helpers.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents the input view for the chatbot UI.
 * Contains a text field for user input and a send button.
 */
public class ChatbotInputView extends JPanel {
    private final JTextField messageField;

    /**
     * Constructs the ChatbotInputView with a text field and send button.
     *
     * @param sendAction      The action listener triggered when the send button is pressed.
     * @param placeholderText The placeholder text displayed in the input field.
     */
    public ChatbotInputView(ActionListener sendAction, String placeholderText) {
        setLayout(new BorderLayout());
        setBackground(ColourManager.NAVY_BLUE);

        messageField = new JTextField();
        messageField.setForeground(ColourManager.WHITE);
        messageField.setCaretColor(ColourManager.WHITE);
        messageField.setBorder(GridBagManager.INPUT_BORDER);
        messageField.setBackground(ColourManager.DARK_GRAY);
        messageField.setText(placeholderText);

        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(placeholderText)) {
                    messageField.setText("");
                    messageField.setForeground(ColourManager.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(placeholderText);
                }
            }
        });

        JButton sendButton = new JButton();
        sendButton.setIcon(ImageManager.getImage("send_icon"));
        sendButton.setPreferredSize(GridBagManager.SEND_MSG_SIZE);
        sendButton.setFocusPainted(false);
        sendButton.setBackground(ColourManager.DARKER_GRAY);
        sendButton.setOpaque(true);
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        sendButton.addActionListener(sendAction);

        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    /**
     * Retrieves the user's input message.
     *
     * @param placeholderText The placeholder text to compare against.
     * @return The user-entered message, or null if the input is empty or matches the placeholder text.
     */
    public String getMessage(String placeholderText) {
        String message = messageField.getText().trim();
        return message.isEmpty() || message.equals(placeholderText) ? null : message;
    }

    /**
     * Resets the input field to display the placeholder text.
     *
     * @param placeholderText The placeholder text to reset the field with.
     */
    public void resetField(String placeholderText) {
        messageField.setText(placeholderText);
        messageField.setForeground(ColourManager.WHITE);
    }
}