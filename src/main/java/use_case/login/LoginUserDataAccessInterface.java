package use_case.login;

public interface LoginUserDataAccessInterface {

    /**
     * Fetch the authentication token for the given email and password.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return The authentication token (idToken) if successful, null otherwise.
     */
    String fetchToken(String email, String password);

    /**
     * Verify the validity of the authentication token.
     *
     * @param idToken The token to be verified.
     * @return True if the token is valid, false otherwise.
     */
    boolean verifyToken(String idToken);

    /**
     * Check if a user with the given email already exists.
     *
     * @param email The email to check.
     * @return True if the email exists, false otherwise.
     */
    boolean checkIfEmailExists(String email);
}