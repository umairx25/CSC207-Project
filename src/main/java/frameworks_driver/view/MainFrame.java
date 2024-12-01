//package frameworks_driver.view;
//
//import data_access.UserDataAccess;
//import frameworks_driver.view.signup.SignupPanel;
//import frameworks_driver.view.style_helpers.PanelNavigator;
//import frameworks_driver.view.signup.RightPanel;
//import interface_adapter.signup.SignupController;
//import interface_adapter.signup.SignupPresenter;
//import interface_adapter.signup.SignupState;
//import interface_adapter.signup.SignupViewModel;
//import use_case.signup.SignupInteractor;
//import use_case.signup.SignupOutputBoundary;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//
//public class MainFrame extends JFrame implements PanelNavigator {
//    private final CardLayout cardLayout;
//    private final JPanel cardPanel;
//
//    public MainFrame() throws IOException {
//        setTitle("Stock Flow");
//        setSize(800, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//        cardPanel.setPreferredSize(new Dimension(500, 400));
//
//        // Instantiate the Clean Architecture components
//        SignupViewModel signupViewModel = new SignupViewModel();
//        SignupState signupState = signupViewModel.getState();
//        UserDataAccess userDataAccess = new UserDataAccess(signupState.getEmail(), signupState.getPassword(), signupState.getUsername());
//        SignupOutputBoundary loginPresenter = new SignupPresenter(signupViewModel);
//        SignupInteractor signupInteractor = new SignupInteractor(userDataAccess, loginPresenter);
//        SignupController signupController = new SignupController(signupInteractor);
//        userDataAccess.initialize_firebase("config.json");
//
//        SignupPanel signUpPanel = new SignupPanel(signupController, signupViewModel);
//        RightPanel rightPanel = new RightPanel();
//
//        // Add panels to the card layout
//        cardPanel.add(signUpPanel, "signup");
//
//        // Add panels to the frame
//        add(cardPanel, BorderLayout.WEST);
//        add(rightPanel, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//
//    @Override
//    public void navigateTo(String panelName) {
//        cardLayout.show(cardPanel, panelName);
//    }
//
//    public static void main(String[] args) throws IOException {
//        MainFrame frame = new MainFrame();
//    }
//
//
//}
