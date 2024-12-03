package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Input Boundary for the Signup Use Case.
 *
 * This interface defines the method for executing the signup process, accepting user data
 * as input and handling the necessary operations to complete the signup.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup process using the provided input data.
     *
     * @param inputData The data needed for signup, including email, password, and username.
     * @throws FirebaseAuthException If there is an issue with Firebase authentication.
     * @throws IOException If there is an error with input/output operations, such as connecting to Firebase.
     * @throws ExecutionException If there is an issue executing a task, such as retrieving data or processing.
     * @throws InterruptedException If the thread executing the task is interrupted.
     */
    void execute(SignupInputData inputData) throws FirebaseAuthException, IOException, ExecutionException, InterruptedException;
}