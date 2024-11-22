package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

public class LoginController {
    private final LoginInputBoundary inputBoundary;

    public LoginController(LoginInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void handleLogin(String email, String password) {
        LoginInputData inputData = new LoginInputData(email, password);
        inputBoundary.login(inputData);
    }
}