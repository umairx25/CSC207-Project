package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;

public interface SignupInputBoundary {
    void execute(SignupInputData inputData) throws FirebaseAuthException;
}
