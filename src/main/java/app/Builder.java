package app;

import java.awt.CardLayout;

import javax.swing.*;

import data_access.InMemoryUserDataAccessObject;
import data_access.StockDataAccess;
import entity.CommonUserFactory;
import entity.UserFactory;
import frameworks_driver.view.chart.ChartView;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.chart.ChartController;
import interface_adapter.chart.ChartPresenter;
import interface_adapter.chart.ChartViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.chart.ChartDataAccessInterface;
import use_case.chart.ChartInputBoundary;
import use_case.chart.ChartInteractor;
import use_case.chart.ChartOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class Builder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final view.ViewManager viewManager = new view.ViewManager(cardPanel, cardLayout, viewManagerModel);
    private final ChartViewModel chartViewModel = new ChartViewModel();
    private final StockDataAccess stockDataAccess = new StockDataAccess();
    final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(viewManagerModel,
            chartViewModel);
    final ChartInputBoundary chartInteractor = new ChartInteractor(
            stockDataAccess, chartOutputBoundary);
    final ChartController controller = new ChartController(chartInteractor);
    //        chartView.setChartController(controller);
    ChartView chartView= new ChartView(chartViewModel, controller);


    // thought question: is the hard dependency below a problem? Yes, never have hard dpendenices
//    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();


    public Builder() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
//    public AppBuilder addSignupView() {
//        signupViewModel = new SignupViewModel();
//        signupView = new SignupView(signupViewModel);
//        cardPanel.add(signupView, signupView.getViewName());
//        return this;
//    }

//    public Builder addChartView() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        ChartViewModel chartViewModel= new ChartViewModel();
//        ChartView chartView= new ChartView(chartViewModel);
//        cardPanel.add(chartView);
//        return this;
//    }

    public Builder addChartView() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ChartViewModel chartViewModel = new ChartViewModel();

        // Initialize and set the SignupController
        final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(viewManagerModel,
                chartViewModel);
        final ChartInputBoundary chartInteractor = new ChartInteractor(
                stockDataAccess, chartOutputBoundary);
        final ChartController controller = new ChartController(chartInteractor);
//        chartView.setChartController(controller);
        ChartView chartView= new ChartView(chartViewModel, controller);

        cardPanel.add(chartView, chartView.getViewName());

        return this;
    }


    /**
     //     * Adds the Signup Use Case to the application.
     //     * @return this builder
     //     */
//    public Builder addChartUseCase() {
//        final ChartOutputBoundary chartOutputBoundary = new ChartPresenter(viewManagerModel,chartViewModel);
//
//        final ChartInputBoundary userSignupInteractor = new ChartInteractor(
//                stockDataAccess, chartOutputBoundary);
//
//        final ChartController controller = new ChartController(userSignupInteractor);
//        chartView.setChartController(controller);
//        return this;
//    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
//    public AppBuilder addLoginView() {
//        loginViewModel = new LoginViewModel();
//        loginView = new LoginView(loginViewModel);
//        cardPanel.add(loginView, loginView.getViewName());
//        return this;
//    }
//
//    /**
//     * Adds the LoggedIn View to the application.
//     * @return this builder
//     */
//    public AppBuilder addLoggedInView() {
//        loggedInViewModel = new LoggedInViewModel();
//        loggedInView = new LoggedInView(loggedInViewModel);
//        cardPanel.add(loggedInView, loggedInView.getViewName());
//        return this;
//    }
//
//    /**
//     * Adds the Signup Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addSignupUseCase() {
//        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
//                signupViewModel, loginViewModel);
//        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
//                userDataAccessObject, signupOutputBoundary, userFactory);
//
//        final SignupController controller = new SignupController(userSignupInteractor);
//        signupView.setSignupController(controller);
//        return this;
//    }
//
//    /**
//     * Adds the Login Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addLoginUseCase() {
//        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel);
//        final LoginInputBoundary loginInteractor = new LoginInteractor(
//                userDataAccessObject, loginOutputBoundary);
//
//        final LoginController loginController = new LoginController(loginInteractor);
//        loginView.setLoginController(loginController);
//        return this;
//    }
//
//    /**
//     * Adds the Change Password Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addChangePasswordUseCase() {
//        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
//                new ChangePasswordPresenter(loggedInViewModel);
//
//        final ChangePasswordInputBoundary changePasswordInteractor =
//                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);
//
//        final ChangePasswordController changePasswordController =
//                new ChangePasswordController(changePasswordInteractor);
//        loggedInView.setChangePasswordController(changePasswordController);
//        return this;
//    }
//
//    /**
//     * Adds the Logout Use Case to the application.
//     * @return this builder
//     */
//    public AppBuilder addLogoutUseCase() {
//        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel);
//
//        final LogoutInputBoundary logoutInteractor =
//                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
//
//        final LogoutController logoutController = new LogoutController(logoutInteractor);
//        loggedInView.setLogoutController(logoutController);
//        return this;
//    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(chartView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}