package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the button for accessing the explore feature.
 */
public class ExploreButton extends JButton {

    /**
     * Constructs a new ExploreButton with predefined styling.
     */
    public ExploreButton() {
        super("Explore");
        setPreferredSize(new Dimension(150, 60));
        setFont(new Font("Arial", Font.BOLD, 16));
        setBackground(Color.WHITE);
        setForeground(new Color(6, 26, 64));
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(new Color(6, 26, 64), 2));
        HoverEffectUtility.applyHoverEffect(this);
    }
}
