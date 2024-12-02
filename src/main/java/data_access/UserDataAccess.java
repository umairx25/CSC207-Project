package data_access;

import app.Builder;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import com.google.firebase.cloud.FirestoreClient;
import entity.User;
import use_case.signup.SignupDataAccessInterface;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Provides methods for user data access, including signing up users,
 * validating existing users, and interacting with the Firestore database.
 */
public class UserDataAccess implements SignupDataAccessInterface {
    private final String email;
    private final String password;
    private final String username;

    /**
     * Constructs a UserDataAccess object with user credentials.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @param username the username of the user
     */
    public UserDataAccess(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    /**
     * Signs up a new user in Firebase Auth and initializes their data in Firestore.
     *
     * @param user     the user entity to sign up
     * @param password the user's password
     * @param db       the Firestore database instance
     * @return true if the signup is successful, false otherwise
     * @throws FirebaseAuthException if Firebase authentication fails
     */
    public boolean signup(User user, String password, Firestore db) throws FirebaseAuthException {
        try {
            // Validate email format
            if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(user.getEmail())
                    .setDisplayName(user.getUsername())
                    .setEmailVerified(false)
                    .setPassword(password)
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            initialize_database(user, db);
            return true;
        } catch (FirebaseAuthException | IOException | ExecutionException | InterruptedException e) {
            return false;
        }
    }

    /**
     * Checks if a user with the specified email exists in Firebase Auth.
     *
     * @param email the email of the user to check
     * @return true if the user exists, false otherwise
     */
    public boolean existingUser(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }

    /**
     * Initializes the user's data in the Firestore database.
     *
     * @param user the user entity to initialize
     * @param db   the Firestore database instance
     * @throws IOException              if there are issues with database access
     * @throws ExecutionException       if there are execution issues with Firestore operations
     * @throws InterruptedException     if the Firestore operation is interrupted
     */
    public void initialize_database(User user, Firestore db) throws IOException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        Map<String, Object> data = new HashMap<>();

        data.put("email", user.getEmail());
        data.put("username", user.getUsername());
        data.put("balance", 0.0);
        data.put("portfolio_value", 0.0);
        data.put("history", new ArrayList<>());
        data.put("portfolio", new ArrayList<>());

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Document created at: " + result.get().getUpdateTime());
    }

    /**
     * Updates the user's data in the Firestore database.
     *
     * @param user the user entity with updated data
     * @param db   the Firestore database instance
     * @throws ExecutionException   if there are execution issues with Firestore operations
     * @throws InterruptedException if the Firestore operation is interrupted
     */
    public void update_database(User user, Firestore db) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        Map<String, Object> data = new HashMap<>();

        data.put("email", user.getEmail());
        data.put("username", user.getUsername());
        data.put("balance", user.getBalance());
        data.put("portfolio_value", user.getPortfolioValue());
        data.put("history", user.getTransactions());
        data.put("portfolio", user.getPortfolio());

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Document updated at: " + result.get().getUpdateTime());
    }

    /**
     * Retrieves user data from the Firestore database.
     *
     * @param email the email of the user
     * @param db    the Firestore database instance
     * @return the User object containing the retrieved data
     * @throws ExecutionException   if there are execution issues with Firestore operations
     * @throws InterruptedException if the Firestore operation is interrupted
     */
    public static User retreive_user_data(String email, Firestore db) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(email);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        String username = document.getString("username");
        double balance = document.getDouble("balance");
        double portfolioValue = document.getDouble("portfolio_value");
        LinkedHashMap<String, ArrayList<String>> transactions = document.get("history", LinkedHashMap.class);
        ArrayList<String> portfolio = (ArrayList<String>) document.get("portfolio");

        return new User(email, username, balance, portfolioValue, transactions, portfolio);
    }

    /**
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Main method for testing user data access functionality.
     *
     * @param args command-line arguments
     * @throws ExecutionException                if there are execution issues with Firestore operations
     * @throws InterruptedException              if the Firestore operation is interrupted
     * @throws UnsupportedLookAndFeelException   if look-and-feel setup fails
     * @throws ClassNotFoundException            if a required class is not found
     * @throws InstantiationException            if instantiation fails
     * @throws IllegalAccessException            if access control fails
     * @throws IOException                       if there are issues with database access
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException,
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, IOException {
        Builder builder = new Builder();
        builder.initialize_firebase("config.json");
        retreive_user_data("aaa@gmail.com", FirestoreClient.getFirestore());
    }
}
