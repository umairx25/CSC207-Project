package interface_adapter.home;

import frameworks_driver.view.chatbot.ChatbotView;

import javax.swing.*;

public class HomePresenter {

    public void presentChatbotNavigation() {
        SwingUtilities.invokeLater(ChatbotView::new);
    }
}
