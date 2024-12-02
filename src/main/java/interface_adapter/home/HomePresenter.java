// File: HomePresenter.java
package interface_adapter.home;

import use_case.home.HomeOutputBoundary;

public class HomePresenter implements HomeOutputBoundary {

    public HomePresenter() {
    }

    @Override
    public void presentChatbotNavigation(boolean isChatbotOpened) {
/*        viewModel.setChatbotOpened(isChatbotOpened);*/
    }
}
