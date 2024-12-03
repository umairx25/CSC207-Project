package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the top panel of the home view, displaying a welcome message and logout button.
 */
public class TopPanel extends JPanel {

    /**
     * Constructs a new TopPanel with a welcome message and logout button.
     *
     * @param username      the name of the user to display in the welcome message
     * @param logOutButton  the button for logging out
     */
    public TopPanel(String username, LogOutButton logOutButton) {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome " + username, SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(welcomeLabel, BorderLayout.WEST);
        add(logOutButton, BorderLayout.EAST);
    }
}
