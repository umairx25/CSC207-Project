// File: HomePresenter.java
package interface_adapter.home;

import use_case.home.HomeOutputBoundary;

public class HomePresenter implements HomeOutputBoundary {

    private final HomeViewModel viewModel;

    public HomePresenter(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChatbotNavigation(boolean isChatbotOpened) {
/*        viewModel.setChatbotOpened(isChatbotOpened);*/
    }

    public HomeViewModel getViewModel() {
        return viewModel; // Expose the ViewModel for state checks
    }
}
