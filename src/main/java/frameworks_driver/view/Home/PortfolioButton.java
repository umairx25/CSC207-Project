package frameworks_driver.view.Home;

import javax.swing.*;
import java.awt.*;

public class PortfolioButton extends JButton {

    public PortfolioButton() {
        super("Portfolio");
        setPreferredSize(new Dimension(150, 60));
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(Color.WHITE); // Set background to white
        setForeground(new Color(6, 26, 64)); // Set font color to dark blue
        setOpaque(true); // Ensure the background is painted
        setBorder(BorderFactory.createLineBorder(new Color(6, 26, 64), 2)); // Dark blue border
        addHoverEffect(); // Add hover effects
    }

    private void addHoverEffect() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(220, 220, 220)); // Light gray on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(Color.WHITE); // Revert to white on exit
            }
        });
    }
}
