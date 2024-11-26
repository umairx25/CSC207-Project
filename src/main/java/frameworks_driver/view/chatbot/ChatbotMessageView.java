package frameworks_driver.view.chatbot;

import view.ColourManager;
import view.FontManager;
import view.ImageManager;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatbotMessageView extends JPanel {
    private final JPanel messagePanel;
    private final JScrollPane scrollPane;

    public ChatbotMessageView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addMessage(String message, boolean isSender) {
        // Bubble container with proper alignment
        JPanel bubbleContainer = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 10));
        bubbleContainer.setOpaque(false);

        // Bubble panel for the message
        JPanel bubblePanel = new JPanel(new BorderLayout());
        Color backgroundColor = isSender ? ColourManager.INNER_BOX_BLUE : ColourManager.INNER_BOX_GREEN;
        Color borderColor = isSender ? ColourManager.OUTER_BOX_BlUE : ColourManager.OUTER_BOX_GREEN;

        bubblePanel.setBackground(backgroundColor);
        bubblePanel.setBorder(new CompoundBorder(
                new LineBorder(borderColor, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Message content
        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setOpaque(false);
        messageLabel.setFont(FontManager.OUTFIT_REGULAR_12);

        // Dynamically calculate the height based on text content
        messageLabel.setSize(new Dimension(450, Integer.MAX_VALUE)); // Max width
        messageLabel.setPreferredSize(new Dimension(450, messageLabel.getPreferredSize().height));
        bubblePanel.add(messageLabel, BorderLayout.CENTER);

        // Timestamp
        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);
        timestampLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timestampLabel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Add 5px padding on top
        bubblePanel.add(timestampLabel, BorderLayout.SOUTH);

        // Add profile icon for the receiver
        if (!isSender) {
            JLabel profileIcon = new JLabel();
            profileIcon.setIcon(ImageManager.getImage("chatbot_pfp"));
            bubbleContainer.add(profileIcon);
        }

        // Ensure dynamic height for the bubble panel
        bubblePanel.setPreferredSize(new Dimension(450, bubblePanel.getPreferredSize().height));
        bubblePanel.setMaximumSize(new Dimension(450, bubblePanel.getPreferredSize().height));

        // Add bubble panel to the container
        bubbleContainer.add(bubblePanel);
        messagePanel.add(bubbleContainer);

        messagePanel.revalidate();
        messagePanel.repaint();

        // Scroll to the latest message
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

}
