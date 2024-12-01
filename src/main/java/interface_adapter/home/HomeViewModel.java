package interface_adapter.home;

public class HomeViewModel {

    private boolean isChatbotOpened;

    public boolean isChatbotOpened() {
        return isChatbotOpened;
    }

    public void setChatbotOpened(boolean chatbotOpened) {
        this.isChatbotOpened = chatbotOpened;
    }
}
