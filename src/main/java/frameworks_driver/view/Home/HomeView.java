package frameworks_driver.view.Home;

import interface_adapter.home.HomeController;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JFrame {

    public HomeView(String username, double portfolioBalance, HomeController controller) {
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create components
        LogOutButton logOutButton = new LogOutButton();
        PortfolioButton portfolioButton = new PortfolioButton();
        ExploreButton exploreButton = new ExploreButton();
        ChatBotButton chatBotButton = new ChatBotButton(e -> controller.toggleChatbot());

        // Create panels
        TopPanel topPanel = new TopPanel(username, logOutButton);
        CenterPanel centerPanel = new CenterPanel(portfolioButton, exploreButton, chatBotButton);
        BottomPanel bottomPanel = new BottomPanel(portfolioBalance);

        // Add components to AnimatedGradientPanel
        AnimatedGradientPanel gradientPanel = new AnimatedGradientPanel();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(topPanel, BorderLayout.NORTH);
        gradientPanel.add(centerPanel, BorderLayout.CENTER);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add gradientPanel to the frame
        add(gradientPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeController controller = new HomeController();
            new HomeView("User", 12345.67, controller);
        });
    }
}
