package frameworks_driver;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.FirebaseAuth;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;
import org.json.JSONObject;
import java.io.IOException;

public class Login extends NewUser {

    String email;
    String password;
    static final Dotenv dotenv = Dotenv.load();

    public Login(String email, String password) throws Exception {
        super(email, password, "");
        this.email = email;
        this.password = password;
    }

    public static String login(String email, String password) throws Exception {

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
                throw new IOException("The email password combination is incorrect. Please try again.");
            }

            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            return jsonResponse.getString("idToken");
        }
    }

    public static FirebaseToken verify_login(String idToken) throws FirebaseAuthException {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        }
        catch (FirebaseAuthException e) {
            throw new FirebaseAuthException(e);
        }
    }

}
