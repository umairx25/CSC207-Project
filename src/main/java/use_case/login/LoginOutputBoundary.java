package use_case.login;

/**
 * Output Boundary for the Login Use Case.
 *
 * This interface defines the methods for preparing the view in response to a login attempt.
 * The methods are called by the interactor to present either a success or failure view based on the result of the login operation.
 */
public interface LoginOutputBoundary {

    /**
     * Prepares the success view after a successful login attempt.
     *
     * This method is called when the login credentials are verified successfully.
     * It updates the view with the provided output data, such as the user's email and login status.
     *
     * @param outputData The data containing the login result, such as the user's email and success status.
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the failure view after a failed login attempt.
     *
     * This method is called when the login attempt fails due to invalid credentials or other errors.
     * It updates the view with an error message describing the cause of failure.
     *
     * @param errorMessage The error message to be displayed on the failure view.
     */
    void prepareFailView(String errorMessage);
}
