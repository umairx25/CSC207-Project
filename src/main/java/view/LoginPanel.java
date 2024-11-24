package view;

import frameworks_driver.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
    private final JLabel errorMessageLabel;

    public LoginPanel(PanelNavigator navigator) {
        setLayout(new GridBagLayout());
        setBackground(ColourManager.DARK_BLUE);

        UIHelper.addHeading(this, "Welcome!!");

        JTextField loginEmailField = new JTextField(20);
        UIHelper.addLabeledField(this, "Email Address", loginEmailField);

        JPasswordField loginPasswordField = new JPasswordField(20);
        UIHelper.addLabeledField(this, "Password", loginPasswordField);

        errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(ColourManager.ERROR_RED);
        errorMessageLabel.setFont(FontManager.ITALIC_SEGOE_FONT_10);
        errorMessageLabel.setVisible(false);
        add(errorMessageLabel, GridBagHelper.errorMsgGBC(5, 120));

        JButton loginButton = new JButton("Login");
        UIHelper.styleButton(loginButton);
        add(loginButton, GridBagHelper.loginButtonGBC());

        loginButton.addActionListener(e -> {
            String email = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                errorMessageLabel.setText("Fields cannot be empty");
                errorMessageLabel.setVisible(true);
            } else if (!Login.preexisting_email(email)) {
                errorMessageLabel.setText("Email does not exist");
                errorMessageLabel.setVisible(true);
            } else {
                try {
                    String idToken = Login.login(email, password);

                    if (idToken == null || !Login.verify_login(idToken)) {
                        errorMessageLabel.setText("Incorrect Password");
                        errorMessageLabel.setVisible(true);
                    } else {
                        errorMessageLabel.setVisible(false);

                        // Navigate to InfoPanel and display email
                        if (navigator instanceof MainFrame) {
                            ((MainFrame) navigator).navigateToInfoPanel(email);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    errorMessageLabel.setText("Error, Try Again");
                    errorMessageLabel.setVisible(true);
                }
            }
            this.revalidate();
            this.repaint();
        });

        JLabel signUpLabel = new JLabel("Don’t have an account yet? Sign up.");
        signUpLabel.setFont(FontManager.SEGOE_FONT_10);
        signUpLabel.setForeground(ColourManager.INFO_TEXT);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(signUpLabel, GridBagHelper.signUpLabelGBC());

        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navigator.navigateTo("signup");
            }
        });
    }
}
