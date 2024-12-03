package interface_adapter.login;

/**
 * The state for the Login View Model.
 * This class holds the data for the login view, including the user's email, password,
 * and the login error status.
 */
public class LoginState {

    private String email;

    public boolean isLoginError() {
        return loginError;
    }

    private boolean loginError;
    private String password;

    /**
     * Gets the email associated with the login state.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password associated with the login state.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the email for the login state.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the login error status for the login state.
     * If true, indicates a login failure occurred.
     *
     * @param loginError The error status indicating whether the login failed.
     */
    public void setLoginError(boolean loginError) {
        this.loginError = loginError;
    }



    /**
     * Sets the password for the login state.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}