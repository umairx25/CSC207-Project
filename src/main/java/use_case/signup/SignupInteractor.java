package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import data_access.UserDataAccess;
import entity.CurrentUser;
import entity.User;
import use_case.login.LoginUserDataAccessInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Interactor for the Signup Use Case.
 *
 * This class is responsible for handling the business logic for signing up a new user.
 * It communicates with the data access layer and presents the result to the presenter.
 */
public class SignupInteractor implements SignupInputBoundary {

    private final SignupUserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary signupPresenter;
    private final CurrentUser currentUser;

    /**
     * Constructor for initializing the SignupInteractor with the required dependencies.
     *
     * @param userDataAccess The data access object for interacting with user data.
     * @param signupPresenter The presenter responsible for presenting the result of the signup process.
     */
    public SignupInteractor(UserDataAccess userDataAccess, SignupOutputBoundary signupPresenter, CurrentUser currentUser) {
        this.userDataAccess = userDataAccess;
        this.signupPresenter = signupPresenter;
        this.currentUser = currentUser;
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
        //User user = new User(email, password, 0, 0, new LinkedHashMap<>(), new ArrayList<>());

        // Try to sign up the user and add them to the database
        this.currentUser.setEmail(email);
        boolean signupSuccess = userDataAccess.signup(password, FirestoreClient.getFirestore());

        // Check if signup was successful and if the user already exists in the database
        if (signupSuccess) {
            System.out.println("Signup successful." + email);
            signupPresenter.prepareSuccessView(new SignupOutputData(CurrentUser.getemail(), true));
        } else {
            // Handle failure: either user already exists or signup failed
            System.out.println("Signup failed. User may already exist.");
            currentUser.setEmail("");
            signupPresenter.prepareFailView("Account already exists or signup failed.");
        }
    }

}