package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {
    private final String email;
    private final boolean signupSuccess;

    public SignupOutputData(String email, boolean SignupSuccess) {
        this.email = email;
        this.signupSuccess = SignupSuccess;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSignupSuccess() {
        return signupSuccess;
    }
}