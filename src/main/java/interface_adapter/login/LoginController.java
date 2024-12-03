package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Login Use Case.
 */
public class LoginController {
    private final LoginInputBoundary loginInteractor;

    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    public void execute(String email, String password) throws IOException, ExecutionException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LoginInputData inputData = new LoginInputData(email, password);
        loginInteractor.execute(inputData);
    }
}
