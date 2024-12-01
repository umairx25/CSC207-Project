package interface_adapter.home;

import frameworks_driver.view.Home.HomeView;
import frameworks_driver.view.chatbot.ChatbotView;
import use_case.home.HomeInteractor;

public class HomeController {

    private final HomeInteractor interactor; // Use case interactor
    private HomeView homeView; // Reference to HomeView for updating views

    public HomeController(HomeInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Sets the HomeView instance for this controller.
     *
     * @param homeView The HomeView instance to be set.
     */
    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }

    /**
     * Handles toggling the chatbot view.
     */
    public void toggleChatbot() {
        interactor.handleChatbotToggle(); // Delegate the business logic to the interactor
        if (homeView != null) {
            homeView.showChatbotView(new ChatbotView(this)); // Update view to show chatbot
        }
    }

    /**
     * Updates the state and restores the HomeView layout.
     */
    public void updateStateForHome() {
        interactor.handleChatbotToggle(); // Example: resetting the chatbot state
        if (homeView != null) {
            homeView.showHomeView(); // Restore the HomeView layout
        }
    }

    /**
     * Updates the state for the Chatbot view.
     */
    public void updateStateForChatbot() {
        interactor.handleChatbotToggle(); // Example: toggling the chatbot state
        // Logic for state updates can be added here if needed
    }
}
