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
        setLayout(new BorderLayout()); // Use BorderLayout for positioning

        // Chatbot content
        JPanel chatbotContent = new JPanel();
        chatbotContent.setLayout(new BoxLayout(chatbotContent, BoxLayout.Y_AXIS));
        ChatbotContainerView containerView = new ChatbotContainerView(
                new ChatbotController(
                        new ChatbotInteractor(
                                new ChatbotPresenter(new ChatbotViewModel()),
                                new ChatbotDataAccess()
                        )
                ),
                new ChatbotViewModel()
        );
        chatbotContent.add(containerView);

        add(chatbotContent, BorderLayout.CENTER);

        // Add "Back to Home" button
        JButton backButton = new JButton("Back to Home");
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> controller.updateStateForHome()); // Trigger navigation
        add(backButton, BorderLayout.SOUTH);
    }
}
