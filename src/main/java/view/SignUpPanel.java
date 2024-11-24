package view;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import frameworks_driver.Login;
import frameworks_driver.NewUser;
import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignUpPanel extends JPanel {
    private final JTextField signUpEmailField;
    private final JTextField signUpUsernameField;
    private final JPasswordField signUpPasswordField;

    public SignUpPanel(PanelNavigator navigator) {
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
            } else if (Login.preexisting_email(email)) {
                UIHelper.showErrorMessage(this, "Email already exists", "signup");
            } else {
                try {
                    Dotenv dotenv = Dotenv.load();
                    Login.initialize_firebase(dotenv.get("FIREBASE_INFO"));
                    NewUser.signup(email, password, username, FirestoreClient.getFirestore());
                    navigator.navigateTo("login");
                } catch (FirebaseAuthException | IOException ex) {
                    UIHelper.showErrorMessage(this, "Error, Try Again", "signup");
                }
            }
        });

        JButton backButton = new JButton("Return to Login");
        UIHelper.styleButton(backButton);
        add(backButton, GridBagHelper.signUpButtonsGBC());

        backButton.addActionListener(e -> navigator.navigateTo("login"));
    }
}

