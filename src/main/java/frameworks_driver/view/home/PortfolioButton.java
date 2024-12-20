package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the button for accessing the portfolio feature.
 */
public class PortfolioButton extends JButton {

    /**
     * Constructs a new PortfolioButton with predefined styling.
     */
    public PortfolioButton() {
        super("Portfolio");
        ImageIcon originalIcon = new ImageIcon("assets/portfolio.png");

        // Scale the icon to an appropriate size
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Adjust size as needed
        setIcon(new ImageIcon(scaledImage));

        // Remove button visual elements to make it appear as only the icon
        setText("");                 // Remove text
        setContentAreaFilled(false); // No background
        setBorderPainted(false);     // No border
        setFocusPainted(false);      // No focus outline
        setOpaque(false);            // Fully transparent

        // Optional: Add hover or click effects if needed
        HoverEffectUtility.applyHoverEffect(this);
        addActionListener(actionListener);
    }
}