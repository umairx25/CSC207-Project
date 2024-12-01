package use_case;

import data_access.login.LoginUserDataAccess;
import frameworks_driver.view.login.MainFrame;
import io.github.cdimascio.dotenv.Dotenv;

public class Main2 {

    public static void main(String[] args) throws Exception {
        new MainFrame();
        LoginUserDataAccess.initialize_firebase("firebase_info.json");
    }
}