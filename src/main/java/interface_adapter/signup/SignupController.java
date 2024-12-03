package interface_adapter.signup;



import com.google.firebase.auth.FirebaseAuthException;
import use_case.signup.SignupInputData;
import use_case.signup.SignupInteractor;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {
    private final SignupInteractor signupInteractor;

    public SignupController(SignupInteractor signupInteractor) {
        this.signupInteractor = signupInteractor;
    }

    public void execute(String email, String password, String username) throws IOException, FirebaseAuthException, ExecutionException, InterruptedException {
        SignupInputData inputData = new SignupInputData(email, password, username);
        signupInteractor.execute(inputData);
    }
}