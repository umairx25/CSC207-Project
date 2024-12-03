package frameworks_driver.view.signup;

import app.Builder;
import com.google.firebase.auth.FirebaseAuthException;
import frameworks_driver.view.style_helpers.UIHelper;
import frameworks_driver.view.style_helpers.*;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Represents the signup panel in the application, allowing users to register
 * by providing their email, username, and password.
 */
public class SignupPanel extends JPanel {

    private final JTextField signUpEmailField;
    private final JTextField signUpUsernameField;
    private final JPasswordField signUpPasswordField;

    /**
     * Constructs a new SignupPanel with input fields for email, username, and password,
     * along with buttons for submitting the signup form or returning to the login view.
     *
     * @param signupController the controller responsible for handling signup logic
     * @param viewModel        the view model providing signup state and data
     * @param builder          the application builder for navigating between views
     */
    public SignupPanel(SignupController signupController, SignupViewModel viewModel, Builder builder) {
        setPreferredSize(new Dimension(500, 750));
        setLayout(new GridBagLayout());
        setBackground(ColourManager.DARK_BLUE);

        // Add heading
        UIHelper.addHeading(this, "Register!");

        // Initialize and add input fields
        signUpEmailField = new JTextField(20);
        UIHelper.addLabeledField(this, "Email Address", signUpEmailField);

        signUpUsernameField = new JTextField(20);
        UIHelper.addLabeledField(this, "Username", signUpUsernameField);

        signUpPasswordField = new JPasswordField(20);
        UIHelper.addLabeledField(this, "Password", signUpPasswordField);

        // Signup button
        JButton signUpButton = new JButton("Sign up");
        UIHelper.styleButton(signUpButton);
        add(signUpButton, GridBagManager.signUpButtonsGBC());

        signUpButton.addActionListener(e -> {
            String email = signUpEmailField.getText();
            String username = signUpUsernameField.getText();
            String password = new String(signUpPasswordField.getPassword());

            if (email.isEmpty() || username.isEmpty()) {
                UIHelper.showErrorMessage(this, "Email and Username cannot be empty", "signup");
            } else if (password.length() < 6) {
                UIHelper.showErrorMessage(this, "Password must be at least 6 characters", "signup");
            } else if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                UIHelper.showErrorMessage(this, "Invalid email address", "signup");
            } else {
                try {
                    signupController.execute(email, password, username);

                    SignupState currentState = viewModel.getState();
                    currentState.setEmail(email);
                    currentState.setPassword(password);
                    currentState.setUsername(username);
                    viewModel.setState(currentState);

                    builder.showView("home");
                } catch (FirebaseAuthException | IOException ex) {
                    UIHelper.showErrorMessage(this, "Error, Try Again", "signup");
                    System.out.println("Signup failed: " + ex.getMessage());
                }
            }
        });

        // Back button
        JButton backButton = new JButton("Return to Login");
        UIHelper.styleButton(backButton);
        add(backButton, GridBagManager.signUpButtonsGBC());

        backButton.addActionListener(e -> builder.showView("login"));
    }

}
