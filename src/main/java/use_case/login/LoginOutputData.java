package use_case.login;

/**
 * Output Data for the Login Use Case.
 *
 * This class encapsulates the data that is returned as a result of a login attempt.
 * It contains the user's email and the status of the login attempt (success or failure).
 */
public class LoginOutputData {

    private final String email;
    private final boolean loginSuccess;

    /**
     * Constructs a LoginOutputData object with the provided email and login success status.
     *
     * @param email The email of the user attempting to log in.
     * @param loginSuccess A boolean indicating whether the login was successful (true) or failed (false).
     */
    public LoginOutputData(String email, boolean loginSuccess) {
        this.email = email;
        this.loginSuccess = loginSuccess;
    }

    /**
     * Gets the email associated with the login attempt.
     *
     * @return The email address of the user attempting to log in.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks whether the login attempt was successful.
     *
     * @return true if the login was successful, false otherwise.
     */
    public boolean isLoginSuccess() {
        return loginSuccess;
    }
}