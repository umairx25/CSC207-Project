package use_case.login;

/**
 * Interactor for the Login Use Case.
 *
 * This class handles the login logic by processing the login input data (email and password),
 * interacting with the data access interface to fetch and verify the user's token,
 * and notifying the presenter whether the login attempt was successful or failed.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface LoginUserDataAccess;
    private final LoginOutputBoundary loginPresenter;

    /**
     * Constructs a LoginInteractor with the specified data access interface and presenter.
     *
     * @param userDataAccess The data access interface to fetch and verify user data.
     * @param loginPresenter The presenter responsible for preparing the success or failure view.
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary loginPresenter) {
        this.LoginUserDataAccess = userDataAccess;
        this.loginPresenter = loginPresenter;
    }

    /**
     * Executes the login use case.
     *
     * This method takes the login input data, retrieves and verifies the user's token from
     * the data access interface, and presents the outcome (success or failure) to the presenter.
     *
     * @param loginInputData The data containing the user's email and password.
     */
    @Override
    public void execute(LoginInputData loginInputData) {
        String email = loginInputData.getEmail();
        String password = loginInputData.getPassword();

        // Attempt to fetch and verify the user's token based on email and password.
        String token = LoginUserDataAccess.fetchToken(email, password);
        if (token != null && LoginUserDataAccess.verifyToken(token)) {
            System.out.println("logged in");
            // Prepare the success view with a valid token
            loginPresenter.prepareSuccessView(new LoginOutputData(email, true));
        } else {
            System.out.println("credentials are wrong");
            // Prepare the failure view if the credentials are invalid
            loginPresenter.prepareFailView("Invalid credentials.");
        }
    }
}