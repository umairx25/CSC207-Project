package use_case.login;

/**
 * Input Data for the Login Use Case.
 *
 * This class represents the data required for the login process, including
 * the user's email and password. It is used as input for the LoginUseCase
 * to validate and authenticate the user.
 */
public class LoginInputData {
    private final String email;
    private final String password;

    /**
     * Constructs a new LoginInputData object with the specified email and password.
     *
     * @param email The email address of the user attempting to log in.
     * @param password The password associated with the user's account.
     */
    public LoginInputData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the email address associated with this login attempt.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password associated with this login attempt.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
}