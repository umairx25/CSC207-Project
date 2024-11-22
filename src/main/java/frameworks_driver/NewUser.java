package frameworks_driver;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NewUser {
    String email;
    String password;
    String username;
    static final Dotenv dotenv = Dotenv.load();

    public NewUser(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static void signup(String email, String password, String username, Firestore db) throws FirebaseAuthException {
        try {
            // Validate email format
            if (email == null || !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                throw new IllegalArgumentException("Invalid email format");
            }

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setDisplayName(username)
                    .setEmailVerified(false)
                    .setPassword(password)
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created new user: " + userRecord.getUid());
            initialize_database(email, username, db);
        } catch (FirebaseAuthException e) {
            throw new FirebaseAuthException(e);
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void initialize_firebase(String file) throws IOException {
        if (FirebaseApp.getApps().isEmpty()) { // Check if no FirebaseApp instances exist
            FileInputStream serviceAccount = new FileInputStream(file);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(dotenv.get("PROJECT_ID"))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }


    public static void initialize_database(String email, String username, Firestore db) throws IOException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(email);
        Map<String, Object> data = new HashMap<>();

        data.put("email", email);
        data.put("username", username);
        data.put("favourites", new ArrayList<>());
        data.put("portfolio", new ArrayList<>());

        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Document created at: " + result.get().getUpdateTime());
    }
}