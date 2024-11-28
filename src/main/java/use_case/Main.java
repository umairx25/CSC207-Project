package use_case;

import data_access.LoginUserDataAccess;
import frameworks_driver.Login;
import frameworks_driver.view.LoginAndSignUp.MainFrame;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) throws Exception {
        LoginUserDataAccess.initialize_firebase("config.json");
        new MainFrame();
    }
}