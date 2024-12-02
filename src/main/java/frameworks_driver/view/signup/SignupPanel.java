package frameworks_driver.view.signup;

import com.google.firebase.auth.FirebaseAuthException;
import frameworks_driver.view.style_helpers.*;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignupPanel extends JPanel {
    private final JTextField signUpEmailField;
    private final JTextField signUpUsernameField;
    private final JPasswordField signUpPasswordField;
    private SignupController signupController;
    private SignupViewModel viewModel;

    public SignupPanel(SignupController signupController, SignupViewModel viewModel) {
        this.signupController = signupController;
        this.viewModel = viewModel;

        setPreferredSize(new Dimension(500, 750));
        setLayout(new GridBagLayout());
        setBackground(ColourManager.DARK_BLUE);

        UIHelper.addHeading(this, "Register!");

        // Initialize and add fields
        signUpEmailField = new JTextField(20);
        UIHelper.addLabeledField(this, "Email Address", signUpEmailField);

        signUpUsernameField = new JTextField(20);
        UIHelper.addLabeledField(this, "Username", signUpUsernameField);

        signUpPasswordField = new JPasswordField(20);
        UIHelper.addLabeledField(this, "Password", signUpPasswordField);

        JButton signUpButton = new JButton("Sign up");
        UIHelper.styleButton(signUpButton);
        add(signUpButton, GridBagHelper.signUpButtonsGBC());

        signUpButton.addActionListener(e -> {
            String email = signUpEmailField.getText();
            String username = signUpUsernameField.getText();
            String password = new String(signUpPasswordField.getPassword());

            if (email.isEmpty() || username.isEmpty()) {
                UIHelper.showErrorMessage(this, "Email and Username cannot be empty", "signup");
            } else if (password.length() < 6) {
                UIHelper.showErrorMessage(this, "Password must be at least 6 characters", "signup");
            } else if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")){
                    UIHelper.showErrorMessage(this, "Invalid email address", "signup");
                }

            else {
                try {
                    signupController.execute(email, password, username);
                    SignupState currentState = viewModel.getState();
                    currentState.setEmail(email);
                    currentState.setPassword(password);
                    currentState.setUsername(username);
                    viewModel.setState(currentState);
//                    navigator.navigateTo("login");
                } catch (FirebaseAuthException | IOException ex) {
                    UIHelper.showErrorMessage(this, "Error, Try Again", "signup");
                    System.out.println("Signup failed");
                }
            }
        });

        JButton backButton = new JButton("Return to Login");
        UIHelper.styleButton(backButton);
        add(backButton, GridBagHelper.signUpButtonsGBC());

//        backButton.addActionListener(e -> navigator.navigateTo("login"));
    }

}
