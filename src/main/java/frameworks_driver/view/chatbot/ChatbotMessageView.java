package frameworks_driver.view.chatbot;

import view.ColourManager;
import view.FontManager;

import javax.swing.*;
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
        JPanel bubbleContainer = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 10));
        bubbleContainer.setOpaque(false);

        // Create a stylized bubble
        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(isSender ? ColourManager.INNER_BOX_BLUE : ColourManager.INNER_BOX_GREEN);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        messageLabel.setFont(FontManager.OUTFIT_REGULAR_12);

        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);
        bubbleContainer.add(timestampLabel);

        bubbleContainer.add(messageLabel);

        messagePanel.add(bubbleContainer);
        messagePanel.revalidate();
        messagePanel.repaint();

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

}
