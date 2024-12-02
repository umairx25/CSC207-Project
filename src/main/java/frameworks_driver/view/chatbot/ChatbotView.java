package frameworks_driver.view.chatbot;

import data_access.ChatbotDataAccess;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import interface_adapter.home.HomeController;
import use_case.chatbot.ChatbotInteractor;

import javax.swing.*;
import java.awt.*;

public class ChatbotView extends JPanel {

    public ChatbotView(HomeController controller) {
        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
        ChatbotViewModel viewModel = new ChatbotViewModel();
        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
        ChatbotController controller2 = new ChatbotController(interactor);

        // Initialize frontend components
        ChatbotContainerView containerView = new ChatbotContainerView(controller2, viewModel);

        // Create main application frame
        JFrame frame = new JFrame("AI Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.add(containerView);

        // Display the frame
        frame.setVisible(true);

        // Add "Back to Home" button
        JButton backButton = new JButton("Back to Home");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> controller.updateStateForHome()); // Trigger navigation
        add(backButton, BorderLayout.SOUTH);
    }
}
