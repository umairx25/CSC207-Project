package interface_adapter.signup;

import com.google.firebase.auth.FirebaseAuthException;
import use_case.signup.SignupInputData;
import use_case.signup.SignupInteractor;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Signup Use Case.
 * This class handles the logic for initiating a signup request with the provided email, password, and username.
 */
public class SignupController {

    private final SignupInteractor signupInteractor;

    /**
     * Constructs a SignupController with the specified SignupInteractor.
     *
     * @param signupInteractor The interactor used to process the signup request.
     */
    public SignupController(SignupInteractor signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    /**
     * Executes the signup process by passing the provided email, password, and username to the SignupInteractor.
     * This method performs the signup operation asynchronously.
     *
     * @param email    The email of the user attempting to sign up.
     * @param password The password of the user attempting to sign up.
     * @param username The username of the user attempting to sign up.
     * @throws IOException               If an I/O error occurs during the process.
     * @throws FirebaseAuthException     If an error occurs related to Firebase authentication.
     * @throws ExecutionException        If an error occurs during the execution of the signup process.
     * @throws InterruptedException      If the thread executing the signup process is interrupted.
     */
    public void execute(String email, String password, String username) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException {
        SignupInputData inputData = new SignupInputData(email, password, username);
        signupInteractor.execute(inputData);
    }
}