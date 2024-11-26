package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import view.FontManager;
import view.GridBagHelper;

public class ChatbotInputView extends JPanel {
    private final JTextField messageField;
    private final JButton sendButton;

    public ChatbotInputView(ActionListener sendAction, String placeholderText) {
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(500, 60));

        // Text field
        messageField = new JTextField();
        messageField.setFont(FontManager.OUTFIT_REGULAR_12);
        messageField.setText(placeholderText);
        messageField.setForeground(Color.GRAY);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageField.setBackground(Color.WHITE);

        // Placeholder behavior
        messageField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (messageField.getText().equals(placeholderText)) {
                    messageField.setText("");
                    messageField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(placeholderText);
                    messageField.setForeground(Color.GRAY);
                }
            }
        });

        // Send button
        sendButton = new JButton("Send");
        sendButton.setFont(FontManager.OUTFIT_BOLD_16);
        sendButton.addActionListener(sendAction);

        // Add components
        GridBagConstraints fieldGBC = GridBagHelper.createGBC(0, 0, new Insets(5, 10, 5, 5), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        add(messageField, fieldGBC);

        GridBagConstraints buttonGBC = GridBagHelper.createGBC(1, 0, new Insets(5, 5, 5, 10), GridBagConstraints.EAST, GridBagConstraints.NONE);
        add(sendButton, buttonGBC);
    }

    public String getMessage(String placeholderText) {
        String message = messageField.getText().trim();
        return message.equals(placeholderText) ? "" : message;
    }

    public void resetField(String placeholderText) {
        messageField.setText(placeholderText);
        messageField.setForeground(Color.GRAY);
    }
}
