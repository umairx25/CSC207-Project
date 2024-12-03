package use_case.signup;

/**
 * Output Boundary for the Signup Use Case.
 *
 * This interface defines the methods that the Presenter will implement
 * to prepare the view for success or failure in the signup process.
 */
public interface SignupOutputBoundary {

    /**
     * Prepares the view with the successful signup data.
     *
     * @param outputData The data containing information about the signed-up user.
     */
    void prepareSuccessView(SignupOutputData outputData);

    /**
     * Prepares the view with an error message when signup fails.
     *
     * @param errorMessage A message describing the failure reason.
     */
    void prepareFailView(String errorMessage);
}