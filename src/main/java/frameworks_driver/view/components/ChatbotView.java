package frameworks_driver.view.components;

import frameworks_driver.view.panels.ChatPanel;
import frameworks_driver.view.panels.InputPanel;

import javax.swing.*;
import java.awt.*;

public class ChatbotView {
    private JFrame frame;
    private ChatPanel chatPanel;
    private InputPanel inputPanel;

    public ChatbotView() {
        frame = new JFrame("AI Chat Application");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Initialize components
        chatPanel = new ChatPanel();
        inputPanel = new InputPanel();

        frame.add(chatPanel.getScrollPane(), BorderLayout.CENTER);
        frame.add(inputPanel.getPanel(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public InputPanel getInputPanel() {
        return inputPanel;
    }
}
