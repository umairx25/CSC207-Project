package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 * This class holds the data related to the signup process, such as the email, password,
 * username, and the signup error status.
 */
public class SignupState {

    private String email;
    private boolean signupError;
    private String password;
    private String username;

    /**
     * Gets the username associated with the signup state.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the signup state.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email associated with the signup state.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password associated with the signup state.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the email for the signup state.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the signup error status for the signup state.
     * If true, indicates a signup failure occurred.
     *
     * @param signupError The error status indicating whether the signup failed.
     */
    public void setSignupError(boolean signupError) {
        this.signupError = signupError;
    }

    /**
     * Sets the password for the signup state.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}