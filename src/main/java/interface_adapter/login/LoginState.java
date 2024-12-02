package interface_adapter.login;

/**
 * The state for the Login View Model.
 */
public class LoginState {
    private String email;
    private boolean loginError;
    private String password;

    public String getEmail() {
        return email;
    }

    public boolean getLoginError() {
        return loginError;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public void setLoginError(boolean usernameError) {
        this.loginError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
