package frameworks_driver.view.chatbot;

import data_access.chatbot.ChatbotDataAccess;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import use_case.chatBot.ChatbotInteractor;

import javax.swing.*;

public class ChatbotView {

    private JFrame frame; // Store the frame as an instance variable

    public ChatbotView() {
        // Initialize the chatbot frame
        frame = new JFrame("AI Chat Application");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ensure only this frame closes
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);

        // Add chatbot container view
        ChatbotContainerView containerView = new ChatbotContainerView(
                new ChatbotController(
                        new ChatbotInteractor(
                                new ChatbotPresenter(new ChatbotViewModel()),
                                new ChatbotDataAccess()
                        )
                ),
                new ChatbotViewModel()
        );

        frame.add(containerView);
        frame.setVisible(true);
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose(); // Dispose of the JFrame
            frame = null;    // Set frame to null to avoid reuse
        }
    }
}
