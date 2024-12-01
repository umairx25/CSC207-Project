package data_access.login;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuth;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;

import java.io.FileInputStream;
import java.io.IOException;

public class LoginUserDataAccess implements LoginUserDataAccessInterface {
    private static final Dotenv dotenv = Dotenv.load();

    @Override
    public String fetchToken(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        System.out.println(email);
        jsonBody.put("password", password);
        System.out.println(password);
        jsonBody.put("returnSecureToken", true);
        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(dotenv.get("FIREBASE_AUTH_URL") + "?key=" + dotenv.get("WEB_API_KEY"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("token error");
            if (!response.isSuccessful() || response.body() == null) {
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("idToken");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verifyToken(String idToken) {
        try {
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            System.out.println("Log in verified");
            return true;
        } catch (FirebaseAuthException e) {
            System.out.println("Log in not verified");
            return false;
        }
    }

//    @Override
//    public boolean checkIfEmailExists(String email) {
//        try {
//            // Try fetching the user by email
//            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
//            System.out.println("User found: " + userRecord.getEmail());  // Log user email for debugging
//            return true;
//        } catch (FirebaseAuthException e) {
//            // If user is not found, handle the exception
//            System.out.println("User with email " + email + " not found.");
//            return false;
//        }
//    }

//    @Override
//    public User getUser(String email) {
//        try {
//            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
//            System.out.println("user error");
//            // Create a new User entity based on Firebase data
//            return new User(userRecord.getDisplayName(), email, null); // Password is not retrievable
//        } catch (FirebaseAuthException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

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

}
