package use_case.login;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * Input Boundary for actions related to logging in.
 * This interface defines the methods for handling user login actions.
 * It represents the use case interface that the LoginInteractor will implement.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login action with the provided login input data.
     *
     * @param loginInputData The data containing the user's email and password for login.
     * @throws ExecutionException If an error occurs during the execution of the login process.
     * @throws InterruptedException If the execution thread is interrupted.
     * @throws UnsupportedLookAndFeelException If the requested Look and Feel is not supported.
     * @throws ClassNotFoundException If the Look and Feel class cannot be found.
     * @throws InstantiationException If the Look and Feel class cannot be instantiated.
     * @throws IllegalAccessException If the Look and Feel class or its constructor is not accessible.
     */
    void execute(LoginInputData loginInputData) throws ExecutionException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
