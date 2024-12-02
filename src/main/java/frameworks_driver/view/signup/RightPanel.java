package frameworks_driver.view.signup;

import frameworks_driver.view.style_helpers.GradientPanel;
import frameworks_driver.view.style_helpers.ImageManager;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the right panel in the signup view, displaying the application's logo
 * with a gradient background.
 */
public class RightPanel extends GradientPanel {

    /**
     * Constructs a new RightPanel with the application's logo centered within the panel.
     */
    public RightPanel() {
        setLayout(new BorderLayout());
        JLabel logoLabel = new JLabel(ImageManager.getImage("logo"));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.CENTER);
    }
}
