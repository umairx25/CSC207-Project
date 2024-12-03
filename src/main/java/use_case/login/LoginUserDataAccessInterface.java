package use_case.login;

/**
 * Interface for user data access operations required by the Login Use Case.
 */
public interface LoginUserDataAccessInterface {
    /**
     * Fetches a Firebase authentication token for a user.
     *
     * @param email    the user's email
     * @param password the user's password
     * @return the authentication token if successful, or null if login fails
     */
    String fetchToken(String email, String password);

    /**
     * Verifies the validity of a Firebase authentication token.
     *
     * @param idToken the token to verify
     * @return true if the token is valid, false otherwise
     */
    boolean verifyToken(String idToken);

}
