package ai;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatbotUI {

    private static GenerativeAi aiClient;
    private static JLabel typingIndicator;

    public static void main(String[] args) {
        // Initialize the AI backend
        aiClient = new GenerativeAi();

        // Load the Outfit-Regular font from the assets folder
        try {
            Font outfitFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Outfit-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(outfitFont);

            // Set default font for all UI components
            UIManager.put("Label.font", outfitFont.deriveFont(Font.PLAIN, 14));
            UIManager.put("Button.font", outfitFont.deriveFont(Font.BOLD, 14));
            UIManager.put("TextField.font", outfitFont.deriveFont(Font.PLAIN, 14));
            UIManager.put("TextArea.font", outfitFont.deriveFont(Font.PLAIN, 14));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load Outfit-Regular font. Using default font.");
        }

        JFrame frame = new JFrame("AI Chat Application");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Add the header panel at the top
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(32, 31, 29, 255)); // Same dark grey as input panel
        headerPanel.setPreferredSize(new Dimension(500, 80));

        // Chatbot icon
        JLabel iconLabel = new JLabel();
        ImageIcon chatbotIcon = new ImageIcon("assets/chatbot_icon.png"); // Replace with actual chatbot icon image path
        Image scaledIcon = chatbotIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale the icon
        iconLabel.setIcon(new ImageIcon(scaledIcon));
        iconLabel.setBorder(new EmptyBorder(15, 15, 15, 10)); // Padding
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Panel for chatbot name and typing indicator
        JPanel nameAndTypingPanel = new JPanel();
        nameAndTypingPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 30)); // Align left, small gap
        nameAndTypingPanel.setOpaque(false); // Transparent background

        // Chatbot name
        JLabel chatbotNameLabel = new JLabel("BullBot");
        chatbotNameLabel.setFont(new Font("Outfit-Regular", Font.BOLD, 20)); // Font for chatbot name
        chatbotNameLabel.setForeground(Color.WHITE);

        // Typing indicator
        typingIndicator = new JLabel("Typing...");
        typingIndicator.setFont(new Font("Outfit-Regular", Font.PLAIN, 12)); // Match your font styling
        typingIndicator.setForeground(Color.WHITE); // White text for typing indicator
        typingIndicator.setVisible(false); // Initially hidden

        // Add chatbot name and typing indicator to the sub-panel
        nameAndTypingPanel.add(chatbotNameLabel);
        nameAndTypingPanel.add(typingIndicator);

        // Add the sub-panel to the header
        headerPanel.add(nameAndTypingPanel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH); // Add header to the top of the frame

        // Chat panel with vertical layout
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        // Add default chatbot message
        String defaultMessage = "Hey There! This is BullBot, StockFlow's own Chatbot powered by AI. "
                + "Feel free to ask me questions about anything finance-related. "
                + "Please note that I do not have access to real-time information.";
        addMessage(chatPanel, defaultMessage, false);

        // Wrap the chat panel in a scroll pane
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        // Customizing scrollbar
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(100, 149, 237); // Cornflower Blue
            }
        });

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(32, 31, 29, 255)); // Dark grey for input panel
        inputPanel.setPreferredSize(new Dimension(500, 60));

        JTextField messageField = new JTextField();
        messageField.setForeground(Color.WHITE);
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        messageField.setBackground(new Color(50, 50, 50)); // Darker grey

        // Placeholder text implementation
        String placeholderText = "Type Message Here...";
        messageField.setText(placeholderText);
        messageField.setForeground(Color.GRAY);

        messageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (messageField.getText().equals(placeholderText)) {
                    messageField.setText("");
                    messageField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (messageField.getText().isEmpty()) {
                    messageField.setText(placeholderText);
                    messageField.setForeground(Color.GRAY);
                }
            }
        });

        // Load the image for the send button
        ImageIcon sendIcon = new ImageIcon("assets/sendMessageImg.png");
        JButton sendButton = new JButton(sendIcon);
        sendButton.setPreferredSize(new Dimension(60, 60));
        sendButton.setBackground(new Color(32, 31, 29, 255)); // Match input panel background
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder());

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Send message on button click or Enter key press
        sendButton.addActionListener(e -> sendMessage(chatPanel, scrollPane, messageField, placeholderText));
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(chatPanel, scrollPane, messageField, placeholderText);
                }
            }
        });
    }


    private static void sendMessage(JPanel chatPanel, JScrollPane scrollPane, JTextField messageField, String placeholderText) {
        String message = messageField.getText().trim();
        if (!message.isEmpty() && !message.equals(placeholderText)) {
            addMessage(chatPanel, message, true); // Add user's message
            messageField.setText("");
            messageField.setForeground(Color.GRAY);
            messageField.setText(placeholderText);

            // Show the typing indicator and disable the input panel
            typingIndicator.setVisible(true);
            messageField.setEnabled(false); // Disable input field

            // Process message with backend AI
            new Thread(() -> {
                try {
                    String aiResponse = aiClient.evaluateAndRespond(message);

                    // Hide the typing indicator and add AI's response
                    SwingUtilities.invokeLater(() -> {
                        typingIndicator.setVisible(false);
                        addMessage(chatPanel, aiResponse, false);

                        // Re-enable the input panel
                        messageField.setEnabled(true);
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        typingIndicator.setVisible(false);
                        addMessage(chatPanel, "Error processing your request.", false);

                        // Re-enable the input panel even on error
                        messageField.setEnabled(true);
                    });
                    e.printStackTrace();
                }
            }).start();

            // Dynamically adjust chatPanel height
            chatPanel.revalidate();
            chatPanel.repaint();

            // Scroll to the bottom
            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
        }
    }



    private static void addMessage(JPanel chatPanel, String message, boolean isSender) {
        JPanel messagePanel = new JPanel(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 15)); // Maintain vertical gap
        messagePanel.setOpaque(false);

        // Add profile icon for the receiver (e.g., chatbot)
        if (!isSender) {
            JLabel profileIcon = new JLabel();
            ImageIcon icon = new ImageIcon("assets/chatbot_icon.png"); // Replace with the actual image path
            Image scaledIcon = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Scale the image
            profileIcon.setIcon(new ImageIcon(scaledIcon));
            messagePanel.add(profileIcon);
        }

        // Create the message bubble
        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BorderLayout());
        bubblePanel.setBackground(isSender ? new Color(173, 216, 230) : new Color(144, 238, 144)); // Different colors for sender/receiver
        bubblePanel.setBorder(new CompoundBorder(
                new LineBorder(isSender ? new Color(100, 149, 237) : new Color(34, 139, 34), 1), // Border color for sender/receiver
                new EmptyBorder(10, 10, 10, 10)
        ));

        JTextArea messageLabel = new JTextArea(message);
        messageLabel.setLineWrap(true);
        messageLabel.setWrapStyleWord(true);
        messageLabel.setEditable(false);
        messageLabel.setOpaque(false);

        // Dynamically calculate the height of the JTextArea based on its content
        messageLabel.setSize(new Dimension(450, Integer.MAX_VALUE)); // Temporary size to calculate preferred height
        int preferredHeight = messageLabel.getPreferredSize().height;
        messageLabel.setPreferredSize(new Dimension(450, preferredHeight));

        bubblePanel.add(messageLabel, BorderLayout.CENTER);

        // Add timestamp
        JLabel timestampLabel = new JLabel(new SimpleDateFormat("hh:mm a").format(new Date()));
        timestampLabel.setFont(UIManager.getFont("Label.font").deriveFont(Font.ITALIC, 10));
        timestampLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        bubblePanel.add(timestampLabel, BorderLayout.SOUTH);

        // Explicitly set the preferred size for the bubblePanel
        int padding = 10; // Additional padding to prevent text cutoff
        bubblePanel.setPreferredSize(new Dimension(450, preferredHeight + 30 + padding)); // 30px for timestamp + extra padding
        bubblePanel.setMaximumSize(new Dimension(450, preferredHeight + 30 + padding));

        messagePanel.add(bubblePanel);
        chatPanel.add(messagePanel);

        chatPanel.revalidate();
        chatPanel.repaint();
    }
}