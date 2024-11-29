package interface_adapter.home;

import frameworks_driver.view.chatbot.ChatbotView;

public class HomeController {

    private ChatbotView chatbotView = null; // Reference to ChatbotView instance
    private boolean isChatbotVisible = false; // Track visibility of ChatbotView

    public void toggleChatbot() {
        if (isChatbotVisible) {
            // If ChatbotView is visible, close it
            if (chatbotView != null) {
                chatbotView.dispose(); // Call dispose method to close the window
            }
            chatbotView = null;
            isChatbotVisible = false;
        } else {
            // If ChatbotView is not visible, open it
            chatbotView = new ChatbotView();
            isChatbotVisible = true;
        }
    }
}
