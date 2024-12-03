package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import entity.CurrentUser;
import entity.User;
import use_case.signup.SignupUserDataAccessInterface;

import io.github.cdimascio.dotenv.Dotenv;

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
public class UserDataAccess implements SignupUserDataAccessInterface {
    String email;
    String password;
//    String username;
    final Dotenv dotenv = Dotenv.load();

    /**
     * Constructs a UserDataAccess object with user credentials.
     *
     * @param email    the email of the user
     * @param password the password of the user
//     * @param username the username of the user
     */
    public UserDataAccess(String email, String password) {
        this.email = email;
        this.password = password;
//        this.username = username;

    }

    /**
     * Signs up a new user in Firebase Auth and initializes their data in Firestore.
     *
//     * @param user     the user entity to sign up
     * @param password the user's password
     * @param db       the Firestore database instance
     * @return true if the signup is successful, false otherwise
     */
    public boolean signup(String password, Firestore db) {
        try {
            // Validate email format
            System.out.println("UserDa Signup: " + CurrentUser.getemail());
            if (CurrentUser.getemail() == null || !CurrentUser.getemail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                System.out.println(CurrentUser.getemail());
                throw new IllegalArgumentException("Invalid email format");
            }

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(CurrentUser.getemail())
                    .setDisplayName(CurrentUser.getemail())
                    .setEmailVerified(false)
                    .setPassword(password)
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());

            PortfolioFirestoreAccess firestoreAccess = new PortfolioFirestoreAccess();
            firestoreAccess.initializeUser(CurrentUser.getemail(), 10000.0);  // Initialize with zero balance

            return true;
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
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
     * @throws IOException          if there are issues with database access
     * @throws ExecutionException   if there are execution issues with Firestore operations
     * @throws InterruptedException if the Firestore operation is interrupted
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

    public void update_database (User user, Firestore db) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(user.getEmail());
        Map<String, Object> data = new HashMap<>();

        data.put("email", user.getEmail());
        data.put("username", user.getUsername());
        data.put("balance", user.getBalance());
        data.put("portfolio_value", user.getPortfolioValue());
        data.put("history", user.getTransactions());
        data.put("portfolio", user.getPortfolio());

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Document created at: " + result.get().getUpdateTime());
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
    public  User retreive_user_data(String email, Firestore db) throws ExecutionException, InterruptedException {
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
    public String getEmail () {
        return email;
    }

    /**
     * @return the username of the user
     */
//    public String getUsername () {
//        return username;
//    }

}
