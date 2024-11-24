import com.google.firebase.cloud.FirestoreClient;
import frameworks_driver.Login;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    // Sample login/signup use case
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        frameworks_driver.NewUser user = new frameworks_driver.NewUser("aa230@yahoo.com", "abcdefg", "user112");
        frameworks_driver.NewUser.initialize_firebase(dotenv.get("FIREBASE_INFO"));
        user.signup(FirestoreClient.getFirestore());
        String id = Login.login("aa230@yahoo.com", "abcdefghi");
        Login.initialize_firebase(dotenv.get("FIREBASE_INFO"));
        System.out.println(Login.verify_login(id));
    }
}
