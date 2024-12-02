package use_case.signup;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import entity.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface SignupDataAccessInterface {
    public static void signup(User user, String password, Firestore db) throws FirebaseAuthException{}
    public static void initialize_firebase(String file) throws IOException{}
    public static void initialize_database(User user, Firestore db) throws IOException, ExecutionException, InterruptedException {}

}
