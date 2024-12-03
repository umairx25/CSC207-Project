package use_case.signup;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import entity.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Interface for accessing the data required for the Signup use case.
 *
 * This interface defines methods for signing up a user, initializing Firebase and Firestore,
 * and retrieving user data from the Firestore database.
 */
public interface SignupDataAccessInterface {

    /**
     * Signs up a new user by creating an account and storing their data in Firebase Authentication.
     *
     * @param user The user to be created.
     * @param password The password for the user.
     * @param db The Firestore database instance.
     * @throws FirebaseAuthException If there is an error with Firebase authentication.
     */
    public static void signup(User user, String password, Firestore db) throws FirebaseAuthException {}

    /**
     * Initializes the Firebase system with the provided credentials file.
     *
     * @param file The path to the Firebase credentials JSON file.
     * @throws IOException If there is an error initializing Firebase.
     */
    public static void initialize_firebase(String file) throws IOException {}

    /**
     * Initializes the Firestore database and stores the user's data in it.
     *
     * @param user The user whose data is to be stored.
     * @param db The Firestore database instance.
     * @throws IOException If there is an error during the database initialization.
     * @throws ExecutionException If the operation on the database fails.
     * @throws InterruptedException If the operation is interrupted.
     */
    public static void initialize_database(User user, Firestore db) throws IOException, ExecutionException, InterruptedException {}

    /**
     * Retrieves the user data from the Firestore database using the user's email.
     *
     * @param email The email of the user whose data is to be retrieved.
     * @param db The Firestore database instance.
     * @return The User object containing the user’s data.
     * @throws ExecutionException If the operation to retrieve the user data fails.
     * @throws InterruptedException If the operation is interrupted.
     */
    public User retreive_user_data(String email, Firestore db) throws ExecutionException, InterruptedException;

}