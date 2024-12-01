package frameworks_driver.view.panels;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatPanel {
    private JPanel chatPanel;
    private JScrollPane scrollPane;

    public ChatPanel() {
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void addMessage(String message, boolean isUser) {
        JPanel messagePanel = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setOpaque(false);

        JPanel bubble = new JPanel();
        bubble.setLayout(new BorderLayout());
        bubble.setBackground(isUser ? new Color(173, 216, 230) : new Color(144, 238, 144));
        bubble.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel messageLabel = new JLabel("<html><body style='width: 200px'>" + message + "</body></html>");
        bubble.add(messageLabel, BorderLayout.CENTER);

        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        bubble.add(timestampLabel, BorderLayout.SOUTH);

        messagePanel.add(bubble);
        chatPanel.add(messagePanel);
        chatPanel.revalidate();
        chatPanel.repaint();

        // Scroll to the bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
}
