package app;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;

import data_access.*;
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
import interface_adapter.home.HomeController;
import use_case.home.HomeInteractor;
import interface_adapter.home.HomePresenter;
import frameworks_driver.view.home.HomeView;

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
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // Chart
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(chartViewModel);
    final ChartInputBoundary chartInteractor = new ChartInteractor(stockDataAccess,
            (ChartPresenter) chartOutputBoundary);
    final ChartController chartController = new ChartController(chartInteractor);
    ChartView chartView = new ChartView(chartViewModel, chartController, chartViewModel.getState());

    //Signup
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final SignupState signupState = new SignupState();
    private final UserDataAccess userDataAccess = new UserDataAccess(signupState.getEmail(), signupState.getPassword(), signupState.getUsername());
    final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
    final SignupInteractor signupInteractor = new SignupInteractor(userDataAccess,
            signupOutputBoundary);

    //Login
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private LoginOutputBoundary LoginOutputBoundary;
    private final LoginUserDataAccess loginUserDataAccess = new LoginUserDataAccess();
    private  final LoginInteractor loginInteractor = new LoginInteractor(loginUserDataAccess, LoginOutputBoundary);


    public Builder() {
        cardPanel.setLayout(cardLayout);
    }

    public void initialize_firebase(String file) throws IOException {
        Dotenv dotenv = Dotenv.load();
        if (FirebaseApp.getApps().isEmpty()) { // Check if no FirebaseApp instances exist
            FileInputStream serviceAccount = new FileInputStream(file);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(dotenv.get("PROJECT_ID"))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }

    public Builder addExploreView() {
        final ExploreViewModel exploreViewModel = new ExploreViewModel();
        final ExploreDataAccess exploreDataAccess = new ExploreDataAccess();
        final ExploreOutputBoundary exploreOutputBoundary = new ExplorePresenter(exploreViewModel, this);
        final ExploreInputBoundary exploreInteractor = new ExploreInteractor(exploreDataAccess, exploreOutputBoundary);
        final ExploreController exploreController = new ExploreController(exploreInteractor);
        ExploreView exploreView = new ExploreView(exploreController, exploreViewModel, chartView);
        cardPanel.add(exploreView, exploreView.getViewName()); // view name is 'explore'
        return this;
    }

    public Builder addChatbotView() {
        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
        ChatbotViewModel viewModel = new ChatbotViewModel();
        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
        ChatbotController controller = new ChatbotController(interactor);

        // Pass Builder to ChatbotContainerView
        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel, this);

        cardPanel.add(containerView, "chatbot");
        return this;
    }


    public void showView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

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

    public Builder addLoginView() throws IOException {
        // Initialize the LoginOutputBoundary with a LoginPresenter and LoginViewModel
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(loginViewModel);

        // Create the LoginInteractor with the LoginUserDataAccess and LoginOutputBoundary
        final LoginInteractor loginInteractor = new LoginInteractor(loginUserDataAccess, loginOutputBoundary);

        // Create the LoginController with the LoginInteractor
        final LoginController loginController = new LoginController(loginInteractor);

        // Instantiate the LoginPanel, passing necessary dependencies
        final LoginPanel loginPanel = new LoginPanel( loginController, loginViewModel, this);

        // Create the right panel for layout consistency
        final RightPanel rightPanel = new RightPanel();

        // Combine the LoginPanel and RightPanel in a main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(loginPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add the main panel to the card layout
        cardPanel.add(mainPanel, "login");
        return this;
    }

    public Builder addHomeView() {
        HomeController controller = new HomeController(new HomeInteractor(new HomePresenter()));
        HomeView homeView = new HomeView("User", 12345.67, controller, this); // Pass 'this' as the Builder
        cardPanel.add(homeView.getContentPane(), "home");
        return this;
    }


    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     *
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Stock Flow");
        Image icon = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        application.setIconImage(icon);

        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        application.setSize(1300, 600); // frame will have a set size
        application.setMinimumSize(new Dimension(1500, 1000));
        application.setExtendedState(JFrame.MAXIMIZED_BOTH); // frame will open maximized
        application.pack(); // sizes the frame so that all its contents are at or above their preferred sizes
        application.add(cardPanel);

        return application;
    }
}