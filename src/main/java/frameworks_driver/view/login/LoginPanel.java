package frameworks_driver.view.login;

import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.FontManager;
import frameworks_driver.view.style_helpers.GridBagManager;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The LoginPanel class connects the front end with the back-end Clean Architecture components.
 */
public class LoginPanel extends JPanel {
    private final JLabel errorMessageLabel;
    private final LoginController loginController;
    private final LoginViewModel loginViewModel;

    public LoginPanel(PanelNavigator navigator, LoginController loginController, LoginViewModel loginViewModel) {
        this.loginController = loginController;
        this.loginViewModel = loginViewModel;

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
        add(errorMessageLabel, GridBagManager.errorMsgGBC(5, 120));

        JButton loginButton = new JButton("Login");
        UIHelper.styleButton(loginButton);
        add(loginButton, GridBagManager.loginButtonGBC());

        loginButton.addActionListener(e -> {
            String email = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                updateErrorMessage("Fields cannot be empty");
            } else {
                // Update the state in the ViewModel
                LoginState currentState = loginViewModel.getState();
                currentState.setEmail(email);
                currentState.setPassword(password);
                loginViewModel.setState(currentState);

                // Call the controller to handle login logic
                try {
                    loginController.execute(email, password);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // Handle ViewModel state updates
                handleViewModelUpdate(navigator);
            }
        });

        JLabel signUpLabel = new JLabel("Don’t have an account yet? Sign up.");
        signUpLabel.setFont(FontManager.SEGOE_FONT_10);
        signUpLabel.setForeground(ColourManager.INFO_TEXT);
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(signUpLabel, GridBagManager.signUpLabelGBC());

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                navigator.navigateTo("signup");
            }
        });
    }

    private void updateErrorMessage(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);
        revalidate();
        repaint();
    }

    private void handleViewModelUpdate(PanelNavigator navigator) {
        LoginState state = loginViewModel.getState();

        // Use the boolean `loginError` to determine success or failure
        if (Boolean.TRUE.equals(state.getLoginError())) {
            updateErrorMessage("Login failed. Please check your credentials.");
        } else if (state.getEmail() != null && !state.getEmail().isEmpty()) {
            errorMessageLabel.setVisible(false);
            revalidate();
            repaint();

            // Navigate to InfoPanel upon successful login
            if (navigator instanceof MainFrame) {
                ((MainFrame) navigator).navigateToInfoPanel(state.getEmail());
            }
        }
    }


}