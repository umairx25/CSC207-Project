package use_case.home;

import interface_adapter.home.HomePresenter;

public class HomeInteractor implements HomeInputBoundary {

    private final HomePresenter presenter;

    public HomeInteractor(HomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void handleChatbotNavigation() {
        presenter.presentChatbotNavigation();
    }
}
