package use_case.signup;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import data_access.UserDataAccess;
import entity.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Interactor for the Signup Use Case.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final UserDataAccess userDataAccess;
    private final SignupOutputBoundary signupPresenter;

    public SignupInteractor(UserDataAccess userDataAccess, SignupOutputBoundary SignupPresenter) {
        this.userDataAccess = userDataAccess;
        this.signupPresenter = SignupPresenter;
    }

    @Override
    public void execute(SignupInputData SignupInputData) throws FirebaseAuthException {
        String email = SignupInputData.getEmail();
        String password = SignupInputData.getPassword();

//        if (!SignupUserDataAccess.checkIfEmailExists(email)) {
//            System.out.println("email does not exist");
//            SignupPresenter.prepareFailView("Email not found.");
//        } else {

        User user = new User(email, password, 0, 0, new LinkedHashMap<>(), new ArrayList<>());
        boolean signupSuccess = userDataAccess.signup(user, password, FirestoreClient.getFirestore());

        //check if account exists
        if (signupSuccess && !userDataAccess.existingUser(email)) {
            System.out.println("signed in");
            signupPresenter.prepareSuccessView(new SignupOutputData(email, true));

        } else {
            System.out.println("credentials are wrong");
            signupPresenter.prepareFailView("Invalid credentials.");
        }
    }
}