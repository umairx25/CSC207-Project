package use_case.login;

/**
 * Output Boundary for the Login Use Case.
 */
public interface LoginOutputBoundary {
    void prepareSuccessView(LoginOutputData outputData);
    void prepareFailView(String errorMessage);
}
