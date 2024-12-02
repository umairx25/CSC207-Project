package app;

import javax.swing.*;
import java.io.IOException;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        final Builder builder = new Builder();
//        builder.initialize_firebase("config.json");


        final JFrame application = builder
                .addExploreView()
//                .addChatbotView()
//                .addSignupView()
//                .addHomeView()
                .build();

        application.setVisible(true);
    }
}