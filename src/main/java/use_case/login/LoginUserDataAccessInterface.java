package use_case.login;

import entity.User;

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

//    /**
//     * Checks if a user with the given email exists in Firebase.
//     *
//     * @param email the email to check
//     * @return true if the email exists, false otherwise
//     */
//    boolean checkIfEmailExists(String email);

    /**
     * Retrieves a user entity based on their email.
     *
     * @param email the user's email
     * @return the User object if the email exists, or null otherwise
     */
//    User getUser(String email);
}
