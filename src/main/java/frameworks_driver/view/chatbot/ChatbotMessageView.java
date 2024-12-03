package frameworks_driver.view.chatbot;

import org.jetbrains.annotations.NotNull;
import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.FontManager;
import frameworks_driver.view.style_helpers.GridBagManager;
import frameworks_driver.view.style_helpers.ImageManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the message display view for the chatbot UI.
 * Manages the display of messages in chat bubbles with timestamps.
 */
public class ChatbotMessageView extends JPanel {
    private final JPanel messagePanel;

    /**
     * Constructs the ChatbotMessageView with a scrollable message area.
     */
    public ChatbotMessageView() {
        setLayout(new BorderLayout());
        setBackground(ColourManager.WHITE);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        // Directly add messagePanel
        add(messagePanel, BorderLayout.CENTER);
    }


    /**
     * Adds a message to the view in a styled bubble format.
     *
     * @param message  The message text to display.
     * @param isSender Indicates if the message is from the user (true) or the chatbot (false).
     */
    public void addMessage(String message, boolean isSender) {
        JPanel bubbleContainer = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 10));
        bubbleContainer.setOpaque(false);

        JPanel bubblePanel = new JPanel(new BorderLayout());
        Color backgroundColor = isSender ? ColourManager.INNER_BOX_BLUE : ColourManager.INNER_BOX_GREEN;
        Color borderColor = isSender ? ColourManager.OUTER_BOX_BlUE : ColourManager.OUTER_BOX_GREEN;

        bubblePanel.setBackground(backgroundColor);
        bubblePanel.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1),
                GridBagManager.INPUT_BORDER
        ));

        JTextArea messageLabel = createMessageTextArea(message);
        bubblePanel.add(messageLabel, BorderLayout.CENTER);

        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);
        timestampLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timestampLabel.setBorder(GridBagManager.TIME_STAMP_BORDER);
        bubblePanel.add(timestampLabel, BorderLayout.SOUTH);

        if (!isSender) {
            JLabel profileIcon = new JLabel();
            profileIcon.setIcon(ImageManager.getImage("chatbot_pfp"));
            bubbleContainer.add(profileIcon);
        }

        int width = 450;
        bubblePanel.setPreferredSize(new Dimension(width, bubblePanel.getPreferredSize().height));
        bubblePanel.setMaximumSize(new Dimension(width, bubblePanel.getPreferredSize().height));

        bubbleContainer.add(bubblePanel);
        messagePanel.add(bubbleContainer);

        messagePanel.revalidate();
        messagePanel.repaint();
    }

    /**
     * Creates a JTextArea styled for displaying a message.
     *
     * @param message The message text to display.
     * @return A styled JTextArea containing the message text.
     */
    @NotNull
    private static JTextArea createMessageTextArea(String message) {
        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setOpaque(false);
        messageLabel.setFont(FontManager.OUTFIT_REGULAR_12);

        int width = 450;
        messageLabel.setSize(new Dimension(width, Integer.MAX_VALUE));
        messageLabel.setPreferredSize(new Dimension(width, messageLabel.getPreferredSize().height));
        return messageLabel;
    }
}