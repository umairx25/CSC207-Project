package interface_adapter.home;

public class HomeViewModel {
    private boolean isChatbotOpened = false;

    public boolean isChatbotOpened() {
        return isChatbotOpened;
    }

    public void setChatbotOpened(boolean chatbotOpened) {
        isChatbotOpened = chatbotOpened;
    }
}
