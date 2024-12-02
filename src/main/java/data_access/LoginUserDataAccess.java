package data_access;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

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

/**
 * Implements data access methods for user login and authentication using Firebase.
 */
public class LoginUserDataAccess implements LoginUserDataAccessInterface {
    private static final Dotenv dotenv = Dotenv.load();

    /**
     * Fetches an authentication token for a user using their email and password.
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return the authentication token (idToken) if successful, or null if authentication fails
     */
    @Override
    public String fetchToken(String email, String password) {
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

    /**
     * Verifies the validity of an authentication token.
     *
     * @param idToken the authentication token to verify
     * @return true if the token is valid, false otherwise
     */
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
}
