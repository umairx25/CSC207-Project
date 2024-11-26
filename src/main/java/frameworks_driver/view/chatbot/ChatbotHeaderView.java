package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;
import view.FontManager;
import view.GridBagHelper;

public class ChatbotHeaderView extends JPanel {
    private final JLabel typingIndicator;

    public ChatbotHeaderView(String botName, ImageIcon botIcon) {
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);

        // Bot icon
        JLabel iconLabel = new JLabel(botIcon);
        GridBagConstraints iconGBC = GridBagHelper.createGBC(0, 0, new Insets(10, 10, 10, 10), GridBagConstraints.WEST, GridBagConstraints.NONE);
        add(iconLabel, iconGBC);

        // Bot name
        JLabel chatbotNameLabel = new JLabel(botName);
        chatbotNameLabel.setFont(FontManager.OUTFIT_BOLD_16);
        chatbotNameLabel.setForeground(Color.WHITE);
        GridBagConstraints nameGBC = GridBagHelper.createGBC(1, 0, new Insets(10, 10, 5, 10), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        add(chatbotNameLabel, nameGBC);

        // Typing indicator
        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(FontManager.OUTFIT_REGULAR_12);
        typingIndicator.setForeground(Color.LIGHT_GRAY);
        typingIndicator.setVisible(false);
        GridBagConstraints typingGBC = GridBagHelper.createGBC(1, 1, new Insets(0, 10, 10, 10), GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        add(typingIndicator, typingGBC);
    }

    public void setTypingIndicatorVisible(boolean isVisible) {
        typingIndicator.setVisible(isVisible);
    }
}
