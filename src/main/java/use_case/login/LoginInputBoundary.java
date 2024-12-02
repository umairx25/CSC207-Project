package use_case.login;

/**
 * Input Boundary for actions related to logging in.
 */
public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData);
}
