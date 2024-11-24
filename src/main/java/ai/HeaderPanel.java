package ai;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JLabel typingIndicator;

    public HeaderPanel(String botName, ImageIcon botIcon, Font botNameFont, Font typingFont, Color backgroundColor) {
        setLayout(new BorderLayout());
        setBackground(backgroundColor);
        setPreferredSize(new Dimension(500, 80));

        // Bot icon
        JLabel iconLabel = new JLabel(botIcon);
        iconLabel.setBorder(new EmptyBorder(15, 15, 15, 10));
        add(iconLabel, BorderLayout.WEST);

        // Bot name and typing indicator
        JPanel nameAndTypingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 30));
        nameAndTypingPanel.setOpaque(false);

        JLabel chatbotNameLabel = new JLabel(botName);
        chatbotNameLabel.setFont(botNameFont);
        chatbotNameLabel.setForeground(Color.WHITE);

        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(typingFont);
        typingIndicator.setForeground(Color.WHITE);
        typingIndicator.setVisible(false);

        nameAndTypingPanel.add(chatbotNameLabel);
        nameAndTypingPanel.add(typingIndicator);

        add(nameAndTypingPanel, BorderLayout.CENTER);
    }

    public void setTypingIndicatorVisible(boolean isVisible) {
        typingIndicator.setVisible(isVisible);
    }
}
