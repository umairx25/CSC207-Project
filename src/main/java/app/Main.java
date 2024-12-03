package app;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Builder builder = new Builder();
        builder.initialize_firebase("config.json");
        builder.addSignupView()
                .addHomeView()
                .addExploreView()
                .addLoginView()
                .addPortfolioView()
                .addChatbotView();

        JFrame application = builder.build();
        application.setVisible(true);
        builder.showView("portfolio");

    }
}