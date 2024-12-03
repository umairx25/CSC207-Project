package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import data_access.UserDataAccess;
import entity.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Interactor for the Signup Use Case.
 *
 * This class is responsible for handling the business logic for signing up a new user.
 * It communicates with the data access layer and presents the result to the presenter.
 */
public class SignupInteractor implements SignupInputBoundary {

    private final UserDataAccess userDataAccess;
    private final SignupOutputBoundary signupPresenter;

    /**
     * Constructor for initializing the SignupInteractor with the required dependencies.
     *
     * @param userDataAccess The data access object for interacting with user data.
     * @param signupPresenter The presenter responsible for presenting the result of the signup process.
     */
    public SignupInteractor(UserDataAccess userDataAccess, SignupOutputBoundary signupPresenter) {
        this.userDataAccess = userDataAccess;
        this.signupPresenter = signupPresenter;
    }

    /**
     * Executes the signup process.
     *
     * @param signupInputData The input data containing the user's email, password, and username.
     * @throws FirebaseAuthException If there is an issue with Firebase authentication during the signup.
     */
    @Override
    public void execute(SignupInputData signupInputData) throws FirebaseAuthException {
        String email = signupInputData.getEmail();
        String password = signupInputData.getPassword();
        String username = signupInputData.getUsername();

        // Create a new user object with the input data
        User user = new User(email, password, 0, 0, new LinkedHashMap<>(), new ArrayList<>());

        // Try to sign up the user and add them to the database
        boolean signupSuccess = userDataAccess.signup(user, password, FirestoreClient.getFirestore());

        // Check if signup was successful and if the user already exists in the database
        if (signupSuccess && !userDataAccess.existingUser(email)) {
            System.out.println("Signup successful.");
            signupPresenter.prepareSuccessView(new SignupOutputData(email, true));
        } else {
            // Handle failure: either user already exists or signup failed
            System.out.println("Signup failed. User may already exist.");
            signupPresenter.prepareFailView("Account already exists or signup failed.");
        }
    }
}