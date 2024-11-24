package frameworks_driver.view.home;



import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel(String username, LogOutButton logOutButton) {
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent for gradient background

        JLabel welcomeLabel = new JLabel("Welcome " + username, SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Segue UI", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(welcomeLabel, BorderLayout.WEST);
        add(logOutButton, BorderLayout.EAST);
    }
}
