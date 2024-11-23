package view;

import javax.swing.*;
import java.awt.*;

public class TempMain extends JFrame implements PanelNavigator {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public TempMain() {
        setTitle("Stock Flow");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(500, 400));

        // Add panels to the CardLayout
        LoginPanel loginPanel = new LoginPanel(this);
        SignUpPanel signUpPanel = new SignUpPanel(this);
        InfoPanel infoPanel = new InfoPanel(this, "Default Email");
        RightPanel rightPanel = new RightPanel();

        cardPanel.add(loginPanel, "login");
        cardPanel.add(signUpPanel, "signup");
        cardPanel.add(infoPanel, "info");

        add(cardPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void navigateTo(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void navigateToInfoPanel(String email) {
        // Remove existing info panel if present and add a new one with updated email
        cardPanel.remove(cardPanel.getComponent(cardPanel.getComponentCount() - 1));
        InfoPanel infoPanel = new InfoPanel(this, email);
        cardPanel.add(infoPanel, "info");
        navigateTo("info");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TempMain::new);
    }
}
