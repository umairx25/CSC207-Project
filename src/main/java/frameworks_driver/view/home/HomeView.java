package frameworks_driver.view.home;

import app.Builder;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main home view for the application, displaying options such as Portfolio,
 * Explore, Chatbot, and Logout, along with user details and a gradient background.
 */
public class HomeView extends JPanel{

    private JPanel currentView;
    private AnimatedGradientPanel gradientPanel;
    private TopPanel topPanel;
    private BottomPanel bottomPanel;

    /**
     * Constructs a new HomeView with user-specific details and navigation buttons.
     *
     * @param username          the name of the user
     * @param portfolioBalance  the current portfolio balance
     * @param builder           the application builder for navigating between views
     */
    public HomeView(String username, double portfolioBalance, Builder builder) {
        setLayout(new BorderLayout());

        PortfolioButton portfolioButton = new PortfolioButton();
        portfolioButton.addActionListener(e -> builder.showView("signup"));
        ExploreButton exploreButton = new ExploreButton();
        exploreButton.addActionListener(e -> builder.showView("explore"));
        ChatBotButton chatbotButton = new ChatBotButton();
        chatbotButton.addActionListener(e -> builder.showView("chatbot"));
        LogOutButton logOutButton = new LogOutButton();
        logOutButton.addActionListener(e -> builder.showView("login"));

        topPanel = new TopPanel(username, logOutButton);
        currentView = new CenterPanel(portfolioButton, exploreButton, chatbotButton);
        bottomPanel = new BottomPanel(portfolioBalance);

        gradientPanel = new AnimatedGradientPanel();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(topPanel, BorderLayout.NORTH);
        gradientPanel.add(currentView, BorderLayout.CENTER);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(gradientPanel);
        setVisible(true);
    }
}
