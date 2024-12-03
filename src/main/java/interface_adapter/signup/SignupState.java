package interface_adapter.signup;

/**
 * The state for the Signup View Model.
 */
public class SignupState {
    private String email;
    private boolean signupError;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public void setSignupError(boolean usernameError) {
        this.signupError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}