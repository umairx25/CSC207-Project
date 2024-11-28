package interface_adapter.home;

import use_case.home.HomeInputBoundary;

public class HomeController {

    private HomeInputBoundary interactor = null;

    public HomeController() {
        this.interactor = interactor;
    }

    public void navigateToChatbot() {
        interactor.handleChatbotNavigation();
    }
}
