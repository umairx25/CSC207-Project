package frameworks_driver.view.home;

import interface_adapter.home.HomeController;
import interface_adapter.home.HomePresenter;
import interface_adapter.home.HomeViewModel;
import use_case.home.HomeInteractor;

import javax.swing.*;
import java.awt.*;

public class HomeView extends JFrame {

    private JPanel currentView; // Store reference to the current panel
    private AnimatedGradientPanel gradientPanel;
    private TopPanel topPanel; // Keep references to original panels
    private BottomPanel bottomPanel;
    private final HomeController controller; // Store the controller reference

    public HomeView(String username, double portfolioBalance, HomeController controller) {
        this.controller = controller; // Initialize the controller
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create components
        LogOutButton logOutButton = new LogOutButton();
        PortfolioButton portfolioButton = new PortfolioButton();
        ExploreButton exploreButton = new ExploreButton();
        ChatBotButton chatBotButton = new ChatBotButton(e -> controller.toggleChatbot());

        // Initialize panels
        topPanel = new TopPanel(username, logOutButton);
        currentView = new CenterPanel(portfolioButton, exploreButton, chatBotButton);
        bottomPanel = new BottomPanel(portfolioBalance);

        // Add components to AnimatedGradientPanel
        gradientPanel = new AnimatedGradientPanel();
        gradientPanel.setLayout(new BorderLayout());
        gradientPanel.add(topPanel, BorderLayout.NORTH);
        gradientPanel.add(currentView, BorderLayout.CENTER);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add gradientPanel to the frame
        add(gradientPanel);

        setVisible(true);
    }

    /**
     * Displays the Chatbot view by delegating to the controller.
     */
    public void showChatbotView(JPanel chatbotView) {
        // Delegate to controller for state update or logic if required
        controller.updateStateForChatbot(); // Example: Controller manages state

        // Remove all components from the frame
        gradientPanel.removeAll();

        // Add the chatbot panel as the only content
        currentView = chatbotView; // Update current view
        gradientPanel.add(currentView, BorderLayout.CENTER);

        // Refresh the layout
        gradientPanel.revalidate();
        gradientPanel.repaint();
    }

    /**
     * Restores the HomeView layout by delegating to the controller.
     */
    public void showHomeView() {
        // Delegate to controller for state update or logic if required
//        controller.updateStateForHome(); // Example: Controller manages state

        // Remove all components from the frame
        gradientPanel.removeAll();

        // Restore the original layout
        gradientPanel.add(topPanel, BorderLayout.NORTH);
        currentView = new CenterPanel(
                new PortfolioButton(),
                new ExploreButton(),
                new ChatBotButton(e -> controller.toggleChatbot()) // Ensure correct action is passed
        );
        gradientPanel.add(currentView, BorderLayout.CENTER);
        gradientPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Refresh the layout
        gradientPanel.revalidate();
        gradientPanel.repaint();
    }

}




