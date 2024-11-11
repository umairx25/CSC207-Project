package ui;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import frameworks_driver.Login;
import frameworks_driver.NewUser;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    private String[] loginInfo;
    private String[] signUpInfo;
    private final JPanel cardPanel;
    private InfoPanel infoPanel;


    // Class-level variables for fonts, borders, and grid constraints
    private final Font segueFont16;
    private final Font segueFont14;
    private final Font segueFont12;
    private final Font segueFont10;
    private final Font italicSegueFont10;
    private final Font segueBoldFont24;

    private final Border normalBorder;
    private final Border focusedBorder;

    private final GridBagConstraints gbc;
    private JLabel errorMessageLabel;




    public MainFrame() {
        // Initialize fonts, borders, and grid constraints
        segueFont16 = new Font("Segue UI", Font.PLAIN, 16);
        segueFont14 = new Font("Segue UI", Font.PLAIN, 14);
        segueFont12 = new Font("Segue UI", Font.PLAIN, 12);
        segueFont10 = new Font("Segue UI", Font.PLAIN, 10);
        italicSegueFont10 = new Font("Segue UI", Font.ITALIC, 10);
        segueBoldFont24 = new Font("Segue UI", Font.BOLD, 24);

        normalBorder = new LineBorder(Color.DARK_GRAY, 2);
        focusedBorder = new LineBorder(Color.DARK_GRAY, 4);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Set up the frame
        setTitle("Stock Flow");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left Panel setup
        cardPanel = new JPanel(new CardLayout());
        cardPanel.setPreferredSize(new Dimension(500, 400));

        JPanel loginPanel = createLoginPanel();
        JPanel signUpPanel = createSignUpPanel();

        cardPanel.add(loginPanel, "login");
        cardPanel.add(signUpPanel, "signup");

        // Create the InfoPanel but set it as initially invisible
        infoPanel = new InfoPanel("");
        infoPanel.setVisible(false); // Only show when login is clicked
        add(infoPanel, BorderLayout.CENTER); // Place it on top of the existing layout

        add(cardPanel, BorderLayout.WEST);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setFont(italicSegueFont10);
        errorMessageLabel.setVisible(false);


        // Right Panel setup with embedded gradient
        JPanel rightPanel = getRightPanel();
        ImageIcon logoIcon = new ImageIcon("images/stock flow_logo.png");
        Image scaledLogoImage = logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(logoLabel, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showErrorMessage(JPanel panel, String message, boolean isLogin) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setVisible(true);

        // Set GridBagConstraints based on login or signup panel
        gbc.gridx = 1;
        gbc.gridwidth = 2;

        if (isLogin) {
            gbc.insets = new Insets(5, 10, 0, 0);
            gbc.gridy = 5; // Assuming error message label goes below the login button in login panel
        } else {
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 10, 0, 0);
            gbc.gridy = 11;
        }

        // Add the error message label to the panel
        if (panel.getComponentZOrder(errorMessageLabel) == -1) {
            panel.add(errorMessageLabel, gbc);
        }

        panel.revalidate();
        panel.repaint();
    }

    private static void applyButtonStyle(JButton button) {
        button.setBorder(new LineBorder(Color.DARK_GRAY, 4)); // Initial 4-pixel dark gray border
        button.setBackground(Color.WHITE); // Set initial background color to white
        button.setOpaque(true); // Ensure the background is painted

        // Add MouseListener for press and release effect
        borderWhenClicked(button);
    }

    private static void borderWhenClicked(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Change border to 5 pixels and background to gray while pressed
                button.setBorder(new LineBorder(Color.DARK_GRAY, 5));
                button.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Revert border and background to original when released
                button.setBorder(new LineBorder(Color.DARK_GRAY, 4));
                button.setBackground(Color.WHITE);
            }
        });
    }

    private static void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40)); // Set preferred width and taller height
        button.setBackground(Color.WHITE); // Set background color to white
        button.setOpaque(true); // Ensure the background is painted
        button.setBorderPainted(true); // Ensure the border is painted
        applyButtonStyle(button);
    }

    private void addFocusListener(JTextField textField, Border normalBorder, Border focusedBorder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(focusedBorder); // Set to focused border on focus
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(normalBorder); // Revert to normal border on focus lost
            }
        });
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField, GridBagConstraints gbc, Font labelFont, Font fieldFont, Border normalBorder, Border focusedBorder) {
        // Label for the field
        gbc.gridy++;
        gbc.insets = new Insets(5, 10, 2, 10);
        gbc.gridwidth = 2;

        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(255, 255, 255));
        label.setFont(labelFont);
        panel.add(label, gbc);

        // Configure the text field
        textField.setFont(fieldFont);
        textField.setBorder(normalBorder);
        textField.setMargin(new Insets(0, 10, 0, 0));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 25));

        // Use the reusable focus listener
        addFocusListener(textField, normalBorder, focusedBorder);

        gbc.gridy++;
        gbc.insets = new Insets(2, 10, 5, 10);
        panel.add(textField, gbc);
    }

    private void heading(JPanel panel, JLabel label) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10); // Increase top and bottom inset to push heading higher
        label.setFont(segueBoldFont24);
        label.setForeground(new Color(255, 255, 255));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, gbc);
        gbc.insets = new Insets(5, 10, 10, 10); // Reset insets for other components
    }


    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(6, 26, 64));

        JLabel welcomeLabel = new JLabel("Welcome!");
        heading(loginPanel, welcomeLabel);

//        // Google Login Button with Resized Icon
//        JButton googleButton = getGoogleButton(segueFont14);
//
//        // Add to layout
//        gbc.gridy++;
//        gbc.gridwidth = 2;
//        loginPanel.add(googleButton, gbc);

        // In createLoginPanel

        // Add email field
        JTextField loginEmailField = new JTextField(20);
        addLabeledField(loginPanel, "Email Address", loginEmailField, gbc, segueFont14, segueFont12, normalBorder, focusedBorder);

        // Add password field
        JPasswordField loginPasswordField = new JPasswordField(20);
        addLabeledField(loginPanel, "Password", loginPasswordField, gbc, segueFont14, segueFont12, normalBorder, focusedBorder);


        // Remember Me Checkbox
        JCheckBox rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setForeground(new Color(255,255,255));

        rememberMeCheckBox.setFont(segueFont10);
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(15, 10, 10, 10);
        loginPanel.add(rememberMeCheckBox, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(segueFont16);
        styleButton(loginButton); // Apply styling to the login button

        gbc.gridy++;
        gbc.insets = new Insets(15, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Modify gbc settings to center the button
        gbc.gridy++;
        gbc.gridwidth = 2; // Take up two columns to center
        gbc.anchor = GridBagConstraints.CENTER; // Center the button in its grid cell

        // Add the button to the layout
        loginPanel.add(loginButton, gbc);

        // Add ActionListener to Login Button
        loginButton.addActionListener(e -> {
            // Store email and password in an array
            loginInfo = new String[]{loginEmailField.getText(), loginPasswordField.getText()};
            System.out.println("loginInfo = " + java.util.Arrays.toString(loginInfo));

            String email = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());

            // Your login button action logic
            if (!frameworks_driver.Login.preexisting_email(email)) {
                showErrorMessage(loginPanel, "Email does not exist.", true);
            } else {
                try {
                    String idToken = frameworks_driver.Login.login(email, password);

                    if (idToken == null || !frameworks_driver.Login.verify_login(idToken)) {
                        showErrorMessage(loginPanel, "Incorrect Password.", true);
                    } else {
                        errorMessageLabel.setVisible(false);
                        showInfoPanel(email);
                    }

                    loginPanel.revalidate();
                    loginPanel.repaint();
                } catch (Exception g) {
                    g.printStackTrace();
                    errorMessageLabel.setText("An error occurred. Please try again.");
                    errorMessageLabel.setVisible(true); // Show the error message for exceptions
                    loginPanel.revalidate();
                    loginPanel.repaint();
                }
            }
        });



        // Sign Up Prompt Label
        JLabel signUpLabel = new JLabel("Don’t have an account yet? Sign up.");
        signUpLabel.setFont(segueFont10);
        signUpLabel.setForeground(new Color(212, 255, 255));
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Modify gbc settings to center the label
        gbc.gridy++;
        gbc.gridwidth = 2; // Take up two columns to center
        gbc.insets = new Insets(5, 35, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center the label in its grid cell

        // Add the label to the layout
        loginPanel.add(signUpLabel, gbc);

        // Add a MouseListener to the "Sign up" label
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "signup");
            }
        });


        return loginPanel;
    }

    private void showInfoPanel(String email) {
        // Remove all components from the frame
        getContentPane().removeAll();

        // Initialize and add the InfoPanel
        infoPanel = new InfoPanel(email);
        add(infoPanel, BorderLayout.CENTER);

        // Refresh the frame to display the new panel
        revalidate();
        repaint();
    }

    // Google Button
//    @NotNull
//    private static JButton getGoogleButton(Font segueFont14) {
//        // Google Button
//        ImageIcon googleIcon = new ImageIcon("images/google_logo.png"); // Adjust path as needed
//        Image googleImg = googleIcon.getImage().getScaledInstance(40, 20, Image.SCALE_SMOOTH); // Resize image
//        ImageIcon resizedGoogleIcon = new ImageIcon(googleImg);
//        JButton googleButton = new JButton("Log in with Google", resizedGoogleIcon);
//        googleButton.setFont(segueFont14);
//        styleButton(googleButton); // Apply styling to the Google button
//
//        return googleButton;
//    }


    // Signup Panel
    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        signUpPanel.setBackground(new Color(6, 26, 64));

        JLabel registerLabel = new JLabel("Register!");
        heading(signUpPanel, registerLabel);

        // Add email field
        JTextField signUpEmailField = new JTextField(20);
        addLabeledField(signUpPanel, "Email Address", signUpEmailField, gbc, segueFont14, segueFont12, normalBorder, focusedBorder);

        // Add username field
        JTextField signUpUsernameField = new JTextField(20);
        addLabeledField(signUpPanel, "Username", signUpUsernameField, gbc, segueFont14, segueFont12, normalBorder, focusedBorder);

        // Add password field
        JTextField signUpPasswordField = new JPasswordField(20);
        addLabeledField(signUpPanel, "Password", signUpPasswordField, gbc, segueFont14, segueFont12, normalBorder, focusedBorder);

        // sign up button
        JButton signUpButton = new JButton("Sign up");
        buttonStyle(signUpPanel, signUpButton);

        // Add ActionListener to Sign Up Button
        signUpButton.addActionListener(e -> {
            // Store email, password, and username in an array
            signUpInfo = new String[]{
                    signUpEmailField.getText(),
                    signUpUsernameField.getText(),
                    signUpPasswordField.getText()
            };
            String username = signUpUsernameField.getText();
            String email = signUpEmailField.getText();
            String password = signUpPasswordField.getText();

            if (email.length() < 1 || username.length() < 1) {
                System.out.println(email);
                System.out.println(username);
                showErrorMessage(signUpPanel, "Email and Username cannot be empty.", false);
            }
            else if (password.length() < 6) {
                showErrorMessage(signUpPanel, "Password must be at least 6 characters.", false);
            }
            else if (frameworks_driver.Login.preexisting_email(email)){
                showErrorMessage(signUpPanel, "Email already exists.", false);
            }
            else{
                try {
                    NewUser.signup(email, password, username, FirestoreClient.getFirestore());
                } catch (FirebaseAuthException ex) {
                    throw new RuntimeException(ex);
                }
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "login");
                System.out.println("signUpInfo = " + java.util.Arrays.toString(signUpInfo));
            }
        });

        // back button
        JButton backButton = new JButton("Return to Login");
        buttonStyle(signUpPanel, backButton);

        // Add ActionListener to Sign Up Button
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) cardPanel.getLayout();
            cl.show(cardPanel, "login");
        });

        return signUpPanel;
    }

    @NotNull
    private JPanel getRightPanel() {
        JPanel rightPanel = new JPanel() {
            private float gradientOffset = 0; // Offset for animation
            private boolean forward = true;   // Controls the direction of the gradient

            private final Timer timer = new Timer(30, e -> {
                // Move the gradient offset in the current direction
                if (forward) {
                    gradientOffset += 0.008f;
                    if (gradientOffset >= 1) {
                        gradientOffset = 1;
                        forward = false; // Reverse direction
                    }
                } else {
                    gradientOffset -= 0.008f;
                    if (gradientOffset <= 0) {
                        gradientOffset = 0;
                        forward = true; // Reverse direction
                    }
                }
                repaint();
            });

            {
                timer.start(); // Start the animation
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Calculate animated start and end points for the gradient
                int x1 = (int) (width * gradientOffset);
                int y1 = (int) (height * gradientOffset);
                int x2 = width - x1;
                int y2 = height - y1;

                Color color1 = new Color(252, 114, 166, 255); // Pink color
                Color color2 = new Color(63, 64, 192, 255);   // Purple color

                // Create a diagonal gradient paint
                GradientPaint gradientPaint = new GradientPaint(x1, y1, color1, x2, y2, color2);
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, width, height);
            }
        };

        rightPanel.setLayout(new BorderLayout());

        // Add logo in the center of the animated panel
        ImageIcon logoIcon = new ImageIcon("images/stock_flow_logo.png");
        Image scaledLogoImage = logoIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledLogoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(logoLabel, BorderLayout.CENTER);

        return rightPanel;
    }

    private void buttonStyle(JPanel signUpPanel, JButton backButton) {
        backButton.setFont(new Font("segue UI", Font.PLAIN, 16));
        styleButton(backButton);

        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        signUpPanel.add(backButton, gbc);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
