package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {
    private final String email;
    private final boolean loginSuccess;

    public LoginOutputData(String email, boolean loginSuccess) {
        this.email = email;
        this.loginSuccess = loginSuccess;
    }

    public String getEmail() {
        return email;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}
