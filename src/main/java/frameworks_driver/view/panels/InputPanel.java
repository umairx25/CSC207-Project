package frameworks_driver.view.panels;

import javax.swing.*;
import java.awt.*;

public class InputPanel {
    private JPanel inputPanel;
    private JTextField messageField;
    private JButton sendButton;

    public InputPanel() {
        inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(32, 31, 29));

        messageField = new JTextField();
        messageField.setForeground(Color.WHITE);
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageField.setBackground(new Color(50, 50, 50));

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(60, 60));
        sendButton.setBackground(new Color(32, 31, 29));
        sendButton.setForeground(Color.WHITE);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
    }

    public JPanel getPanel() {
        return inputPanel;
    }

    public JTextField getMessageField() {
        return messageField;
    }

    public JButton getSendButton() {
        return sendButton;
    }
}
