package use_case;

import com.google.firebase.cloud.FirestoreClient;
import frameworks_driver.Login;
import io.github.cdimascio.dotenv.Dotenv;
import ui.MainFrame;

public class Main {

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        new MainFrame();
        Login.initialize_firebase(dotenv.get("FIREBASE_INFO"));
    }
}