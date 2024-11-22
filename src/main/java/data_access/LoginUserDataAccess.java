package data_access;

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

import java.io.IOException;

public class LoginUserDataAccess implements LoginUserDataAccessInterface {
    private static final Dotenv dotenv = Dotenv.load();



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
            if (!response.isSuccessful()) {
                return null;
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject();
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
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        try {
            FirebaseAuth.getInstance().getUserByEmail(email);
            return true;
        } catch (FirebaseAuthException e) {
            return false;
        }
    }
}
