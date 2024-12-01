package frameworks_driver.view.login;

import view.FontManager;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel(PanelNavigator navigator, String email) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setFont(FontManager.SEGOE_FONT_16);

        add(emailLabel, GridBagHelper.labelGBC());

        JButton logoutButton = new JButton("Logout");
        UIHelper.styleButton(logoutButton);
        add(logoutButton, GridBagHelper.loginButtonGBC());

        logoutButton.addActionListener(e -> {
            // Navigate back to login panel
            navigator.navigateTo("login");
        });
    }
}
