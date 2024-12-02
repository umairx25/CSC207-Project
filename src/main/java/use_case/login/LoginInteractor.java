package use_case.login;

/**
 * Interactor for the Login Use Case.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface LoginUserDataAccess;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccess, LoginOutputBoundary loginPresenter) {
        this.LoginUserDataAccess = userDataAccess;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String email = loginInputData.getEmail();
        String password = loginInputData.getPassword();

            String token = LoginUserDataAccess.fetchToken(email, password);
            if (token != null && LoginUserDataAccess.verifyToken(token)) {
                System.out.println("logged in");
                loginPresenter.prepareSuccessView(new LoginOutputData(email, true));

            } else {
                System.out.println("credentials are wrong");
                loginPresenter.prepareFailView("Invalid credentials.");
            }
    }
}
