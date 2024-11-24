package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements PanelNavigator {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    public MainFrame() {
        setTitle("Stock Flow");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(500, 400));

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
        cardPanel.remove(cardPanel.getComponent(cardPanel.getComponentCount() - 1));
        InfoPanel infoPanel = new InfoPanel(this, email);
        cardPanel.add(infoPanel, "info");
        navigateTo("info");
    }
}