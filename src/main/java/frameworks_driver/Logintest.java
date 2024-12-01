package frameworks_driver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;

public class Logintest {
    private static final Dotenv dotenv = Dotenv.load();

    public static String fetchToken(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("password", password);
        jsonBody.put("returnSecureToken", true);
        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(dotenv.get("FIREBASE_AUTH_URL") + "?key=" + dotenv.get("WEB_API_KEY"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Debugging: Print out the request URL and the body
            System.out.println("Request URL: " + request.url());
            System.out.println("Request Body: " + jsonBody.toString());

            // Check if the response is successful and print the response body
            if (!response.isSuccessful() || response.body() == null) {
                System.out.println("Error: " + response.body());
                return null;
            }

            String responseBody = response.body().string();
            // Debugging: Print out the response body
            System.out.println("Response Body: " + responseBody);

            // Parse the response and get the idToken
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("idToken");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifyToken(String idToken) {
        try {
            System.out.println("Verifying token: " + idToken);
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            System.out.println("Log in verified");
            return true;
        } catch (FirebaseAuthException e) {
            System.out.println("FirebaseAuthException during verifyToken: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

//    public static boolean checkIfEmailExists(String email) {
//        try {
//            System.out.println("Checking if email exists: " + email);
//            FirebaseAuth.getInstance().getUserByEmail(email);
//            return true;
//        } catch (FirebaseAuthException e) {
//            System.out.println("FirebaseAuthException during checkIfEmailExists: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        }
//    }

    public static void initialize_firebase(String file) throws IOException {
        System.out.println("Initializing Firebase with file: " + file);
        if (FirebaseApp.getApps().isEmpty()) { // Check if no FirebaseApp instances exist
            try {
                FileInputStream serviceAccount = new FileInputStream(file);
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId(dotenv.get("PROJECT_ID"))
                        .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully.");
            } catch (IOException e) {
                System.out.println("IOException during Firebase initialization: " + e.getMessage());
                e.printStackTrace();
                throw e; // rethrow after logging
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            initialize_firebase("C:\\Users\\panja\\IdeaProjects\\CSC207-Project2\\config.json");
            String email = "aneeq23@gmail.com";
            String password = "pakistanzindabad";

//            System.out.println("Email exists check: " + checkIfEmailExists(email));

            String token = fetchToken(email, password);
            if (token == null) {
                System.out.println("Failed to fetch token for email: " + email);
            } else {
                System.out.println("Token fetched successfully: " + token);
                System.out.println("Token verification result: " + verifyToken(token));
            }
        } catch (IOException e) {
            System.out.println("Exception in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
