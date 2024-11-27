package app;


import javax.swing.*;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final Builder builder = new Builder();

        final JFrame application = builder
                .addExploreView()
                .build();

        application.setVisible(true);
    }
}