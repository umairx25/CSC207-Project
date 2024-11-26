package use_case;

import frameworks_driver.Login;
import io.github.cdimascio.dotenv.Dotenv;
import view.MainFrame;

public class Main2 {

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        new MainFrame();
        Login.initialize_firebase(dotenv.get("FIREBASE_INFO"));
    }
}