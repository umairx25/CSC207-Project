package interface_adapter.login;

import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Login Use Case.
 * This class handles the logic for initiating a login request using the provided email and password.
 */
public class LoginController {

    private final LoginInputBoundary loginInteractor;

    /**
     * Constructs a LoginController with the specified LoginInputBoundary (Interactor).
     *
     * @param loginInteractor The interactor used to process the login request.
     */
    public LoginController(LoginInputBoundary loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    /**
     * Executes the login process by passing the provided email and password to the LoginInputBoundary.
     * This method performs the login operation asynchronously.
     *
     * @param email    The email of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @throws IOException                    If an I/O error occurs during the process.
     * @throws ExecutionException             If an error occurs during the execution of the login process.
     * @throws InterruptedException           If the thread executing the login process is interrupted.
     * @throws UnsupportedLookAndFeelException If the requested look-and-feel is not supported.
     * @throws ClassNotFoundException         If a class needed for the process cannot be found.
     * @throws InstantiationException         If an error occurs while creating an instance of a class.
     * @throws IllegalAccessException         If an attempt is made to access a class or method that is not accessible.
     */
    public void execute(String email, String password) throws IOException, ExecutionException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LoginInputData inputData = new LoginInputData(email, password);
        loginInteractor.execute(inputData);
    }
}