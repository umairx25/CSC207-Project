package app;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.*;

// Explore
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
//import data_access.ExploreDataAccess;
//import frameworks_driver.view.chatbot.ChatbotContainerView;
//import frameworks_driver.view.explore.ExploreView;
//import frameworks_driver.view.home.HomeView;
//import frameworks_driver.view.signup.RightPanel;
//import frameworks_driver.view.signup.SignupPanel;
//import interface_adapter.explore.ExploreController;
//import interface_adapter.explore.ExplorePresenter;
//import interface_adapter.explore.ExploreViewModel;
//import interface_adapter.home.HomeController;
//import interface_adapter.home.HomePresenter;
//import interface_adapter.home.HomeViewModel;
//import interface_adapter.signup.SignupController;
//import interface_adapter.signup.SignupPresenter;
//import interface_adapter.signup.SignupState;
//import interface_adapter.signup.SignupViewModel;
import data_access.InMemoryPortfolioUserDataAccess;
import io.github.cdimascio.dotenv.Dotenv;
//import use_case.explore.ExploreInputBoundary;
//import use_case.explore.ExploreInteractor;
//import use_case.explore.ExploreOutputBoundary;

// Chart
import data_access.StockDataAccess;
//import frameworks_driver.view.chart.ChartView;
//import interface_adapter.chart.ChartController;
//import interface_adapter.chart.ChartPresenter;
//import interface_adapter.chart.ChartViewModel;
//import use_case.chart.ChartInputBoundary;
//import use_case.chart.ChartInteractor;
//import use_case.chart.ChartOutputBoundary;
//import data_access.ChatbotDataAccess;
//import use_case.chatbot.ChatbotInteractor;
//import interface_adapter.chatbot.ChatbotPresenter;
//import interface_adapter.chatbot.ChatbotViewModel;
//import interface_adapter.chatbot.ChatbotController;
//import use_case.home.HomeInteractor;
//import use_case.signup.SignupInteractor;
//import use_case.signup.SignupOutputBoundary;

//Portfolio
import interface_adapter.portfolio.PortfolioViewModel;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInteractor;
import frameworks_driver.view.Portfolio.PortfolioView;
import use_case.portfolio.PortfolioOutputBoundary;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */

public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // Explore
//    private final ExploreViewModel exploreViewModel = new ExploreViewModel();
//    private final ExploreDataAccess exploreDataAccess = new ExploreDataAccess();
//    final ExploreOutputBoundary exploreOutputBoundary = new ExplorePresenter(exploreViewModel);
//    final ExploreInputBoundary exploreInteractor = new ExploreInteractor(exploreDataAccess, exploreOutputBoundary);
//    final ExploreController exploreController = new ExploreController(exploreInteractor);
//    ExploreView exploreView = new ExploreView(exploreController, exploreViewModel);
//
//    // Chart
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    //    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
//    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(chartViewModel);
//    final ChartInputBoundary chartInteractor = new ChartInteractor(stockDataAccess,
//            (ChartPresenter) chartOutputBoundary);
//    final ChartController chartController = new ChartController(chartInteractor);
//    ChartView chartView = new ChartView(chartViewModel, chartController, chartViewModel.getState());
//
//    //Signup
//    private final SignupViewModel signupViewModel = new SignupViewModel();
//    private final SignupState signupState = new SignupState();
//    private final UserDataAccess userDataAccess = new UserDataAccess(signupState.getEmail(), signupState.getPassword(), signupState.getUsername());
//    final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(signupViewModel);
//    final SignupInteractor signupInteractor = new SignupInteractor(userDataAccess,
//            signupOutputBoundary);

    // Portfolio


    public Builder() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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

//    public Builder addExploreView() {
//        cardPanel.add(exploreView);
//        return this;
//    }
//
//    public Builder addChartView() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        ChartViewModel chartViewModel = new ChartViewModel();
//
//        // Initialize and set the SignupController
//        final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(
//                chartViewModel);
//        final ChartInputBoundary chartInteractor = new ChartInteractor(
//                stockDataAccess, (ChartPresenter) chartOutputBoundary);
//        final ChartController controller = new ChartController(chartInteractor);
//
//        ChartView chartView = new ChartView(chartViewModel, controller, chartViewModel.getState());
//
//        cardPanel.add(chartView, chartView.getViewName());
//
//        return this;
//    }

//    public Builder addChatbotView() {
//        // Initialize backend components
//        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
//        ChatbotViewModel viewModel = new ChatbotViewModel();
//        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
//        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
//        ChatbotController controller = new ChatbotController(interactor);
//
//        // Initialize frontend components
//        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel);
//
//        cardPanel.add(containerView, "Chatbot");
//
//        // Create main application frame
////        JFrame frame = new JFrame("AI Chat Application");
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setSize(500, 700);
////        frame.setLocationRelativeTo(null);
////        frame.add(containerView);
////
////        // Display the frame
////        frame.setVisible(true);
//        return this;
//    }

//    public Builder addChatbotView() {
//        ChatbotDataAccess dataAccess = new ChatbotDataAccess();
//        ChatbotViewModel viewModel = new ChatbotViewModel();
//        ChatbotPresenter presenter = new ChatbotPresenter(viewModel);
//        ChatbotInteractor interactor = new ChatbotInteractor(presenter, dataAccess);
//        ChatbotController controller = new ChatbotController(interactor);
//
//        // Initialize frontend components
//        ChatbotContainerView containerView = new ChatbotContainerView(controller, viewModel);
//
//        // Create main application frame
//        JFrame frame = new JFrame("AI Chat Application");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500, 700);
//        frame.setLocationRelativeTo(null);
//        frame.add(containerView);
//
//        // Display the frame
//        frame.setVisible(true);
//        return this;
//    }
//
//    public Builder addSignupView() throws IOException {
//        final SignupController signupController = new SignupController(signupInteractor);
//        final SignupPanel signupPanel = new SignupPanel(signupController, signupViewModel);
//        final RightPanel rightPanel = new RightPanel();
//
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.add(signupPanel, BorderLayout.WEST);
//        mainPanel.add(rightPanel, BorderLayout.CENTER);
//
//        // Add the mainPanel to the cardPanel for proper navigation
//        cardPanel.add(mainPanel, "signup");
//        return this;
//    }
//
//    public Builder addHomeView() {
//        SwingUtilities.invokeLater(() -> {
//            HomeController controller = new HomeController(new HomeInteractor(new HomePresenter(new HomeViewModel())));
//            HomeView homeView = new HomeView("User", 12345.67, controller);
//            controller.setHomeView(homeView); // Set HomeView after creation
//        });
//        return this;
//    }

    public Builder addPortfolioView() {
        // Initialize PortfolioViewModel
        PortfolioViewModel portfolioViewModel = new PortfolioViewModel();

        // Use Firestore integration for portfolio data access (assuming InMemoryPortfolioUserDataAccess is connected to Firestore)
        InMemoryPortfolioUserDataAccess portfolioDataAccess = new InMemoryPortfolioUserDataAccess("11@gmail.com");

        // Output boundary and presenter
        final PortfolioOutputBoundary portfolioOutputBoundary = new PortfolioPresenter(portfolioViewModel);

        // Interactor setup
        final PortfolioInputBoundary portfolioInteractor = new PortfolioInteractor(
                portfolioDataAccess, portfolioOutputBoundary
        );

        // Controller setup - only pass interactor here, viewModel can be updated via the interactor
        final PortfolioController controller = new PortfolioController(portfolioInteractor, portfolioViewModel);

        // View setup
        PortfolioView portfolioView = new PortfolioView(
                portfolioViewModel, controller
        );

        // Add the view to the card panel
        cardPanel.add(portfolioView);

        return this;
    }





    /**
         * Creates the JFrame for the application and initially sets the SignupView to be displayed.
         *
         * @return the application
         */
        public JFrame build() {
            final JFrame application = new JFrame("Stock Flow");
            application.setSize(1300, 600);
            Image icon = Toolkit.getDefaultToolkit().getImage("images/icon.png");
            application.setIconImage(icon);
            application.pack();


            application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            application.add(cardPanel);

//            viewManagerModel.setState(portfolioView.getViewName());
            viewManagerModel.firePropertyChanged();

            return application;
        }
    }