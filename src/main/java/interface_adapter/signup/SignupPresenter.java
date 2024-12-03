package interface_adapter.signup;

import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Presenter for the Signup Use Case.
 * This class prepares the view with the success or failure status after attempting to sign up.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;

    /**
     * Constructs a SignupPresenter with the specified SignupViewModel.
     *
     * @param signupViewModel The ViewModel used to update the view with signup status.
     */
    public SignupPresenter(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
    }

    /**
     * Prepares the view for a successful signup by updating the view model with the user's email
     * and setting the signup error status to false.
     *
     * @param outputData The output data containing the user's email after a successful signup.
     */
    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        SignupState state = signupViewModel.getState();
        state.setEmail(outputData.getEmail());
        state.setSignupError(false);
        System.out.println("Presenter success");
        signupViewModel.setState(state);
    }

    /**
     * Prepares the view for a failed signup attempt by setting the error status to true
     * and logging the user's email.
     *
     * @param errorMessage The error message indicating why the signup failed.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        SignupState state = signupViewModel.getState();
        System.out.println(state.getEmail()); // Logs the current email before failure
        state.setSignupError(true);
        System.out.println("Presenter error");
        signupViewModel.setState(state);
    }
}