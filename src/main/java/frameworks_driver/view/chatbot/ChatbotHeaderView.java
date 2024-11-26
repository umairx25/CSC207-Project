package frameworks_driver.view.chatbot;

import view.ColourManager;
import view.FontManager;
import view.GridBagManager;
import view.ImageManager;

import javax.swing.*;
import java.awt.*;

public class ChatbotHeaderView extends JPanel {
    private final JLabel typingIndicator;

    public ChatbotHeaderView() {
        setLayout(new BorderLayout());
        setBackground(ColourManager.DARKER_GRAY);
        setPreferredSize(new Dimension(GridBagManager.HEADER_SIZE));

        // Initialize the bot's icon
        ImageIcon botIcon = ImageManager.getImage("chatbot_pfp");
        JLabel iconLabel = new JLabel(botIcon);
        iconLabel.setBorder(GridBagManager.ICON_BORDER);
        add(iconLabel, BorderLayout.WEST);

        // Create a panel for the bot name and typing indicator
        JPanel nameAndTypingPanel = new JPanel(GridBagManager.HEADER_FLOW_LAYOUT);
        nameAndTypingPanel.setOpaque(false);

        // Bot name
        JLabel chatbotNameLabel = new JLabel("BullBot");
        chatbotNameLabel.setFont(FontManager.OUTFIT_BOLD_22);
        chatbotNameLabel.setForeground(ColourManager.WHITE);

        // Typing indicator
        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(FontManager.ITALIC_SEGOE_FONT_12);
        typingIndicator.setForeground(ColourManager.WHITE);
        typingIndicator.setVisible(false);
        typingIndicator.setBorder(GridBagManager.TYPING_BORDER);

        // Add components to the name and typing panel
        nameAndTypingPanel.add(chatbotNameLabel);
        nameAndTypingPanel.add(typingIndicator);

        // Add the panel to the center
        add(nameAndTypingPanel, BorderLayout.CENTER);
    }

    public void setTypingIndicatorVisible(boolean isVisible) {
        typingIndicator.setVisible(isVisible);
    }
}
