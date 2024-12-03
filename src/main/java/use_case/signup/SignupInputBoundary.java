package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface SignupInputBoundary {
    void execute(SignupInputData inputData) throws FirebaseAuthException, IOException, ExecutionException, InterruptedException;
}