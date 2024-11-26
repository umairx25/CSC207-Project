package frameworks_driver.view.chatbot;

import javax.swing.*;
import java.awt.*;

import view.FontManager;
import view.GridBagHelper;

public class ChatbotScreenView extends JPanel {
    private final JPanel messagePanel;
    private final JScrollPane scrollPane;

    public ChatbotScreenView() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        // Message panel
        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        // Scroll pane
        scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        // Add scroll pane
        GridBagConstraints scrollPaneGBC = GridBagHelper.createGBC(0, 0, new Insets(10, 10, 10, 10), GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        add(scrollPane, scrollPaneGBC);
    }

    public void addMessage(String message, boolean isSender) {
        JPanel bubbleContainer = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 10));
        bubbleContainer.setOpaque(false);

        JLabel messageBubble = new JLabel(message);
        messageBubble.setOpaque(true);
        messageBubble.setBackground(isSender ? Color.CYAN : Color.LIGHT_GRAY);
        messageBubble.setFont(FontManager.OUTFIT_REGULAR_12);
        messageBubble.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        bubbleContainer.add(messageBubble);

        messagePanel.add(bubbleContainer);
        messagePanel.revalidate();
        messagePanel.repaint();
    }
}
