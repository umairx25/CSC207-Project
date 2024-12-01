package frameworks_driver.view.login;

import data_access.LoginUserDataAccess;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginInputBoundary;
import interface_adapter.login.LoginPresenter;

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

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setPreferredSize(new Dimension(500, 400));

        // Instantiate the Clean Architecture components
        LoginViewModel loginViewModel = new LoginViewModel();
        LoginUserDataAccess userDataAccess = new LoginUserDataAccess();
        LoginOutputBoundary loginPresenter = new LoginPresenter(loginViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccess, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        // Create the panels
        LoginPanel loginPanel = new LoginPanel(this, loginController, loginViewModel);
//        SignUpPanel signUpPanel = new SignUpPanel(this);
        InfoPanel infoPanel = new InfoPanel(this, "Default Email");
        RightPanel rightPanel = new RightPanel();

        // Add panels to the card layout
        cardPanel.add(loginPanel, "login");
//        cardPanel.add(signUpPanel, "signup");
        cardPanel.add(infoPanel, "info");

        // Add panels to the frame
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