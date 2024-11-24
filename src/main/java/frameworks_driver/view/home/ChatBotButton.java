package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

public class ChatBotButton extends JButton {

    public ChatBotButton() {
        super("Chatbot");
        setPreferredSize(new Dimension(150, 60));
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(Color.WHITE); // Set background to white
        setForeground(new Color(6, 26, 64)); // Set font color to dark blue
        setOpaque(true); // Ensure the background is painted
        setBorder(BorderFactory.createLineBorder(new Color(6, 26, 64), 2)); // Dark blue border
        HoverEffectUtility.applyHoverEffect(this); // Add hover effect
    }
}
