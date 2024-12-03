package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 *
 * This class contains the data that will be sent to the presenter
 * after attempting the signup, such as the email and signup success status.
 */
public class SignupOutputData {

    private final String email;
    private final boolean signupSuccess;

    /**
     * Constructor to initialize the SignupOutputData.
     *
     * @param email The email associated with the signup attempt.
     * @param signupSuccess The result of the signup process (success or failure).
     */
    public SignupOutputData(String email, boolean signupSuccess) {
        this.email = email;
        this.signupSuccess = signupSuccess;
    }

    /**
     * Retrieves the email of the user who attempted to sign up.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Indicates whether the signup was successful or not.
     *
     * @return true if the signup was successful, false otherwise.
     */
    public boolean isSignupSuccess() {
        return signupSuccess;
    }

}