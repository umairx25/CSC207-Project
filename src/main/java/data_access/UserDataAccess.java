package data_access;

//import app.Builder;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;


import io.github.cdimascio.dotenv.Dotenv;
import use_case.signup.SignupDataAccessInterface;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class UserDataAccess implements SignupDataAccessInterface {
    String email;
    String password;
    String username;
    final Dotenv dotenv = Dotenv.load();

    public UserDataAccess(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

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

            PortfolioFirestoreAccess firestoreAccess = new PortfolioFirestoreAccess();
            firestoreAccess.initializeUser(user.getEmail(), 10000.0);  // Initialize with zero balance

            return true;
        } catch (FirebaseAuthException | ExecutionException | InterruptedException e) {
            return false;
        }
    }



    public boolean existingUser(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }

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

    public void update_database(User user, Firestore db) throws IOException, ExecutionException, InterruptedException {
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

//    public static void main(String[] args) throws ExecutionException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
//        Builder builder = new Builder();
//        builder.initialize_firebase("config.json");
//        retreive_user_data("aaa@gmail.com", FirestoreClient.getFirestore());
//    }

}