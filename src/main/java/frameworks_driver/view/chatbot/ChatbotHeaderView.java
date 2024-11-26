package frameworks_driver.view.chatbot;

import view.ColourManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatbotHeaderView extends JPanel {
    private final JLabel typingIndicator;

    public ChatbotHeaderView(String botName, ImageIcon botIcon, Font botNameFont, Font typingFont, Color backgroundColor) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(500, 80));

        JLabel iconLabel = new JLabel(botIcon);
        iconLabel.setBorder(new EmptyBorder(15, 15, 15, 10));
        add(iconLabel, BorderLayout.WEST);

        JPanel nameAndTypingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 30));
        nameAndTypingPanel.setOpaque(false);

        JLabel chatbotNameLabel = new JLabel(botName);
        chatbotNameLabel.setFont(botNameFont);
        chatbotNameLabel.setForeground(ColourManager.WHITE);

        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(typingFont);
        typingIndicator.setForeground(ColourManager.WHITE);
        typingIndicator.setVisible(false);

        nameAndTypingPanel.add(chatbotNameLabel);
        nameAndTypingPanel.add(typingIndicator);

        add(nameAndTypingPanel, BorderLayout.CENTER);
    }

    public void setTypingIndicatorVisible(boolean isVisible) {
        typingIndicator.setVisible(isVisible);
    }
}
