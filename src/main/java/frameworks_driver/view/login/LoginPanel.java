package frameworks_driver.view.login;

import app.Builder;
import entity.CurrentUser;
import frameworks_driver.view.style_helpers.ColourManager;
import frameworks_driver.view.style_helpers.FontManager;
import frameworks_driver.view.style_helpers.GridBagManager;
import frameworks_driver.view.style_helpers.UIHelper;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Represents the login panel of the application, connecting the front-end view
 * with the back-end logic using Clean Architecture principles.
 */
public class LoginPanel extends JPanel {

    private final JLabel errorMessageLabel;

    /**
     * Constructs a new LoginPanel with input fields, buttons, and navigation options.
     *
     * @param loginController the controller responsible for login logic
     * @param loginViewModel  the view model providing login state and data
     * @param builder         the application builder for view navigation
     */
    public LoginPanel(LoginController loginController, LoginViewModel loginViewModel, Builder builder) {

        setLayout(new GridBagLayout());
        setBackground(ColourManager.DARK_BLUE);
        setPreferredSize(new Dimension(500, 750));

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
                updateErrorMessage();
            } else {
                LoginState currentState = loginViewModel.getState();
                currentState.setEmail(email);
                currentState.setPassword(password);
                loginViewModel.setState(currentState);


                try {
                    loginController.execute(email, password);
                    if (!loginViewModel.getState().isLoginError()) {
                        builder.showView("home");
                    }
                    loginViewModel.getState().setLoginError(false);

                } catch (IOException | ExecutionException | InterruptedException | UnsupportedLookAndFeelException |
                         ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
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
                builder.showView("signup");
            }
        });
    }

    /**
     * Updates the error message displayed in the panel.
     */
    private void updateErrorMessage() {
        errorMessageLabel.setText("Fields cannot be empty");
        errorMessageLabel.setVisible(true);
        revalidate();
        repaint();
    }
}
