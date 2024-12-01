// File: HomeInteractor.java
package use_case.home;

import interface_adapter.home.HomePresenter;

public class HomeInteractor implements HomeInputBoundary {

    private final HomeOutputBoundary outputBoundary;

    public HomeInteractor(HomeOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void handleChatbotToggle() {
        boolean isChatbotOpened = !((HomePresenter) outputBoundary).getViewModel().isChatbotOpened();
        outputBoundary.presentChatbotNavigation(isChatbotOpened);
    }

    public boolean isChatbotOpened() {
        return ((HomePresenter) outputBoundary).getViewModel().isChatbotOpened();
    }
}
