package use_case.signup;

/**
 * Input Data for the Signup Use Case.
 *
 * This class stores the data required for the user signup process, including
 * the email, password, and username. It provides getters and setters for
 * accessing and modifying the signup data.
 */
public class SignupInputData {

    private String email;
    private String password;
    private String username;

    /**
     * Constructor to initialize the SignupInputData with the provided email, password, and username.
     *
     * @param email    The email address of the user.
     * @param password The password chosen by the user.
     * @param username The username chosen by the user.
     */
    public SignupInputData(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email The email address to be set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password chosen by the user.
     *
     * @return The password chosen by the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The password to be set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username chosen by the user.
     *
     * @return The username chosen by the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     *
     * @param username The username to be set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}