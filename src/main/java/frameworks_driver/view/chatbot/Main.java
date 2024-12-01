
package frameworks_driver.view.chatbot;

import javax.swing.*;
import data_access.ChatbotDataAccess;
import use_case.chatBot.ChatbotInteractor;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import interface_adapter.chatbot.ChatbotController;

/**
 * Entry point for the chatbot application.
 * Integrates the backend with the frontend and launches the application.
 */
public class Main {
    public static void main(String[] args) {
        // Initialize backend components
        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
        ChatbotViewModel viewModel = new ChatbotViewModel();
        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
        ChatbotController controller = new ChatbotController(interactor);

        // Initialize frontend components
        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel);

        // Create main application frame
        JFrame frame = new JFrame("AI Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        frame.add(containerView);

        // Display the frame
        frame.setVisible(true);
    }
}
