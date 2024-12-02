package frameworks_driver.view.chatbot;

import app.Builder;
import data_access.ChatbotDataAccess;
import interface_adapter.chatbot.ChatbotController;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import use_case.chatbot.ChatbotInteractor;

import javax.swing.*;
import java.awt.*;

public class ChatbotView extends JPanel {

    public ChatbotView(Builder builder) {
        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
        ChatbotViewModel viewModel = new ChatbotViewModel();
        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
        ChatbotController controller = new ChatbotController(interactor);

        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel, builder);

        setLayout(new BorderLayout());
        add(containerView, BorderLayout.CENTER);
    }
}