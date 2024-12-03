package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the button for logging out of the application.
 */
public class LogOutButton extends JButton {

    /**
     * Constructs a new LogOutButton with predefined styling.
     */
    public LogOutButton() {
        super("Logout");
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
