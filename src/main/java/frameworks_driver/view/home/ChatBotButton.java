package frameworks_driver.view.home;
import javax.swing.*;
import java.awt.*;

public class ChatBotButton extends JButton {

    public ChatBotButton() {
        super("Chatbot");
        setPreferredSize(new Dimension(150, 60));
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(Color.WHITE);
        setForeground(new Color(6, 26, 64));
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(new Color(6, 26, 64), 2));
        HoverEffectUtility.applyHoverEffect(this);

        // Add action listener to toggle ChatbotView
        addActionListener(actionListener);
    }
}