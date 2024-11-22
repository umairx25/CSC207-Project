package use_case.login;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface dataAccess;

    public LoginInteractor(LoginUserDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public LoginOutputData login(LoginInputData inputData) {
        String token = dataAccess.fetchToken(inputData.getEmail(), inputData.getPassword());
        if (token == null) {
            return new LoginOutputData(false, "Login failed.");
        }
        return new LoginOutputData(true, token);
    }

    public boolean verifyLogin(String idToken) {
        return dataAccess.verifyToken(idToken);
    }

    public boolean checkIfEmailExists(String email) {
        return dataAccess.checkIfEmailExists(email);
    }
}