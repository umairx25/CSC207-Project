package ui;// InfoPanel.java (no changes needed from previous implementation)
import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel(String email) {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        JLabel emailLabel = new JLabel("Email: " + email);

        Font font = new Font("Segoe UI", Font.PLAIN, 18);
        emailLabel.setFont(font);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(emailLabel, gbc);
        gbc.gridy++;

    }
}
