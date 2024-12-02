package frameworks_driver.view.chatbot;

import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.FontManager;
import frameworks_driver.view.style_helpers.GridBagManager;
import frameworks_driver.view.style_helpers.ImageManager;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the header view for the chatbot UI.
 * Displays the chatbot icon, name, and typing indicator.
 */
public class ChatbotHeaderView extends JPanel {
    private final JLabel typingIndicator;

    /**
     * Constructs the ChatbotHeaderView with a bot icon, name, and typing indicator.
     */
    public ChatbotHeaderView() {
        setLayout(new BorderLayout());
        setBackground(ColourManager.DARKER_GRAY);
        setPreferredSize(new Dimension(GridBagManager.HEADER_SIZE));

        ImageIcon botIcon = ImageManager.getImage("chatbot_pfp");
        JLabel iconLabel = new JLabel(botIcon);
        iconLabel.setBorder(GridBagManager.ICON_BORDER);
        add(iconLabel, BorderLayout.WEST);

        JPanel nameAndTypingPanel = new JPanel(GridBagManager.HEADER_FLOW_LAYOUT);
        nameAndTypingPanel.setOpaque(false);

        JLabel chatbotNameLabel = new JLabel("BullBot");
        chatbotNameLabel.setFont(FontManager.OUTFIT_BOLD_22);
        chatbotNameLabel.setForeground(ColourManager.WHITE);

        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(FontManager.ITALIC_SEGOE_FONT_12);
        typingIndicator.setForeground(ColourManager.WHITE);
        typingIndicator.setVisible(false);
        typingIndicator.setBorder(GridBagManager.TYPING_BORDER);

        nameAndTypingPanel.add(chatbotNameLabel);
        nameAndTypingPanel.add(typingIndicator);

        add(nameAndTypingPanel, BorderLayout.CENTER);
    }

    /**
     * Updates the visibility of the typing indicator.
     *
     * @param isVisible true to make the typing indicator visible, false to hide it.
     */
    public void setTypingIndicatorVisible(boolean isVisible) {
        typingIndicator.setVisible(isVisible);
    }
}
