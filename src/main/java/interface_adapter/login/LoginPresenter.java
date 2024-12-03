package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;

    public LoginPresenter(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData outputData) {
        LoginState state = loginViewModel.getState();
        state.setEmail(outputData.getEmail());
        loginViewModel.setState(state);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        LoginState state = loginViewModel.getState();
        System.out.println(state.getEmail());
        state.setLoginError(true);
        loginViewModel.setState(state);
    }
}
