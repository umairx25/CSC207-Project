package frameworks_driver.view.signup;



import frameworks_driver.view.style_helpers.GradientPanel;
import frameworks_driver.view.style_helpers.ImageManager;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends GradientPanel {
    public RightPanel() {
        setLayout(new BorderLayout());
        JLabel logoLabel = new JLabel(ImageManager.getImage("logo"));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(logoLabel, BorderLayout.CENTER);
    }

}