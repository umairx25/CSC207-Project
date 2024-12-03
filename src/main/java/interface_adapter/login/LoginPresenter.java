package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the Login Use Case.
 * This class handles preparing the view for both success and failure scenarios after a login attempt.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;

    /**
     * Constructs a LoginPresenter with the specified LoginViewModel.
     *
     * @param loginViewModel The ViewModel used to update the view with login status.
     */
    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    /**
     * Prepares the view for a successful login by updating the view model with the user's email.
     *
     * @param outputData The output data containing the user's email after a successful login.
     */
    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        LoginState state = loginViewModel.getState();
        state.setEmail(outputData.getEmail());
        loginViewModel.setState(state);
    }

    /**
     * Prepares the view for a failed login attempt by setting the error state and printing the user's email.
     *
     * @param errorMessage The error message indicating why the login failed.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        LoginState state = loginViewModel.getState();
        System.out.println(state.getEmail()); // Logs the current email before failure
        state.setLoginError(true);
        loginViewModel.setState(state);
    }
}