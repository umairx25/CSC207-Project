package app;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;

import data_access.*;
import frameworks_driver.view.home.HomeView;
import frameworks_driver.view.login.LoginPanel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import io.github.cdimascio.dotenv.Dotenv;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

// Common
import interface_adapter.ViewManagerModel;

// Chatbot
import org.jetbrains.annotations.NotNull;
import use_case.chatbot.ChatbotInteractor;
import interface_adapter.chatbot.ChatbotPresenter;
import interface_adapter.chatbot.ChatbotViewModel;
import frameworks_driver.view.chatbot.ChatbotContainerView;
import interface_adapter.chatbot.ChatbotController;

// Explore
import frameworks_driver.view.explore.ExploreView;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExplorePresenter;
import interface_adapter.explore.ExploreViewModel;
import use_case.explore.ExploreInputBoundary;
import use_case.explore.ExploreInteractor;
import use_case.explore.ExploreOutputBoundary;

// Chart
import frameworks_driver.view.chart.ChartView;
import interface_adapter.chart.ChartController;
import interface_adapter.chart.ChartPresenter;
import interface_adapter.chart.ChartViewModel;
import use_case.chart.ChartInputBoundary;
import use_case.chart.ChartInteractor;
import use_case.chart.ChartOutputBoundary;

// Home
//import interface_adapter.home.HomeController;
//import use_case.home.HomeInteractor;
//import interface_adapter.home.HomePresenter;
//import interface_adapter.home.HomeViewModel;
//import frameworks_driver.view.home.HomeView;

// SignUp
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupState;
import use_case.login.LoginInteractor;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import interface_adapter.signup.SignupViewModel;
import frameworks_driver.view.signup.RightPanel;
import frameworks_driver.view.signup.SignupPanel;

import use_case.login.LoginOutputBoundary;

/**
 * Builder class for assembling the application's components and views
 * in accordance with the Clean Architecture principles.
 */
public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(chartViewModel);
    final ChartInputBoundary chartInteractor = new ChartInteractor(stockDataAccess, (ChartPresenter) chartOutputBoundary);
    final ChartController chartController = new ChartController(chartInteractor);
    ChartView chartView = new ChartView(chartViewModel, chartController, chartViewModel.getState());

    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final SignupState signupState = new SignupState();
    private final UserDataAccess userDataAccess = new UserDataAccess(signupState.getEmail(), signupState.getPassword(), signupState.getUsername());
    final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
    final SignupInteractor signupInteractor = new SignupInteractor(userDataAccess, signupOutputBoundary);

    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final LoginUserDataAccess loginUserDataAccess = new LoginUserDataAccess();

    public Builder() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Initializes Firebase with the provided service account file.
     *
     * @param file Path to the service account JSON file.
     * @throws IOException If an error occurs while accessing the file.
     */
    public void initialize_firebase(String file) throws IOException {
        Dotenv dotenv = Dotenv.load();
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream(file);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(dotenv.get("PROJECT_ID"))
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }

    /**
     * Adds the Explore view to the card panel.
     *
     * @return Builder instance for chaining.
     */
    public Builder addExploreView() {
        final ExploreViewModel exploreViewModel = new ExploreViewModel();
        final ExploreDataAccess exploreDataAccess = new ExploreDataAccess();
        final ExploreOutputBoundary exploreOutputBoundary = new ExplorePresenter(exploreViewModel, this);
        final ExploreInputBoundary exploreInteractor = new ExploreInteractor(exploreDataAccess, exploreOutputBoundary);
        final ExploreController exploreController = new ExploreController(exploreInteractor);
        ExploreView exploreView = new ExploreView(exploreController, exploreViewModel, chartView);
        cardPanel.add(exploreView, "explore");
        return this;
    }

    /**
     * Adds the Chatbot view to the card panel.
     *
     * @return Builder instance for chaining.
     */
    public Builder addChatbotView() {
        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
        ChatbotViewModel viewModel = new ChatbotViewModel();
        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
        ChatbotController controller = new ChatbotController(interactor);

        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel, this);
        cardPanel.add(containerView, "chatbot");
        return this;
    }

    /**
     * Switches the current view displayed in the card panel.
     *
     * @param viewName Name of the view to display.
     */
    public void showView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }

    /**
     * Adds the Signup view to the card panel.
     *
     * @return Builder instance for chaining.
     * @throws IOException If an error occurs while loading the view.
     */
    public Builder addSignupView() throws IOException {
        final SignupController signupController = new SignupController(signupInteractor);
        final SignupPanel signupPanel = new SignupPanel(signupController, signupViewModel, this);
        final RightPanel rightPanel = new RightPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(signupPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        cardPanel.add(mainPanel, "signup");
        return this;
    }

    /**
     * Adds the Login view to the card panel.
     *
     * @return Builder instance for chaining.
     * @throws IOException If an error occurs while loading the view.
     */
    public Builder addLoginView() throws IOException {
        final LoginPanel loginPanel = getLoginPanel();
        final RightPanel rightPanel = new RightPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loginPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        cardPanel.add(mainPanel, "login");
        return this;
    }

    /**
     * Creates and configures the LoginPanel.
     *
     * @return Configured LoginPanel instance.
     */
    @NotNull
    private LoginPanel getLoginPanel() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loginViewModel);
        final LoginInteractor loginInteractor = new LoginInteractor(loginUserDataAccess, loginOutputBoundary);
        final LoginController loginController = new LoginController(loginInteractor);
        return new LoginPanel(loginController, loginViewModel, this);
    }

    /**
     * Adds the Home view to the card panel.
     *
     * @return Builder instance for chaining.
     */
    public Builder addHomeView() {
//        HomeController controller = new HomeController(new HomeInteractor(new HomePresenter(new HomeViewModel())));
        HomeView homeView = new HomeView("User", 12345.67, this);
        cardPanel.add(homeView, "home");
        return this;
    }

    /**
     * Builds the main application frame and sets initial configurations.
     *
     * @return Configured JFrame instance.
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
        Image icon = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        application.setIconImage(icon);

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setMinimumSize(new Dimension(1500, 1000));
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);
        application.pack();
        application.add(cardPanel);

        viewManagerModel.setState(chartView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
