package ai;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import view.ColourManager;
import view.FontManager;
import view.ImageManager;

public class MessageBubble extends JPanel {
    public MessageBubble(String message, boolean isSender) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Set bubble background and border colors based on sender type
        Color backgroundColor = isSender ? ColourManager.INNER_BOX_BLUE : ColourManager.OUTER_BOX_BLUE;
        Color borderColor = isSender ? ColourManager.INNER_BOX_GREEN : ColourManager.OUTER_BOX_GREEN;

        // Message bubble panel
        JPanel bubblePanel = new JPanel(new BorderLayout());
        bubblePanel.setBackground(backgroundColor);
        bubblePanel.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Text area for the message content
        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setOpaque(false);
        messageLabel.setFont(FontManager.OUTFIT_REGULAR_12);

        // Adjust bubble size dynamically
        messageLabel.setSize(new Dimension(450, Integer.MAX_VALUE));
        int preferredHeight = messageLabel.getPreferredSize().height;
        messageLabel.setPreferredSize(new Dimension(450, preferredHeight));
        bubblePanel.add(messageLabel, BorderLayout.CENTER);

        // Timestamp label
        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);
        timestampLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        bubblePanel.add(timestampLabel, BorderLayout.SOUTH);

        // Add a profile icon for the receiver
        if (!isSender) {
            JLabel profileIcon = new JLabel();
            profileIcon.setIcon(ImageManager.getImage("chatbot_pfp"));
            add(profileIcon, BorderLayout.WEST);
        }

        // Add the bubble panel
        int padding = 10;
        bubblePanel.setPreferredSize(new Dimension(450, preferredHeight + 30 + padding));
        bubblePanel.setMaximumSize(new Dimension(450, preferredHeight + 30 + padding));
        add(bubblePanel, BorderLayout.CENTER);
    }
}
