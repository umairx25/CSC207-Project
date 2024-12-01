package frameworks_driver.view.login;

import view.FontManager;
import view.GridBagManager;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel(PanelNavigator navigator, String email) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setFont(FontManager.SEGOE_FONT_16);

        add(emailLabel, GridBagManager.labelGBC());

        JButton logoutButton = new JButton("Logout");
        UIHelper.styleButton(logoutButton);
        add(logoutButton, GridBagManager.loginButtonGBC());

        logoutButton.addActionListener(e -> {
            // Navigate back to login panel
            navigator.navigateTo("login");
        });
    }
}


