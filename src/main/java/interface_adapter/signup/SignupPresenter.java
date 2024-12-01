package interface_adapter.signup;


import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {
    private final SignupViewModel SignupViewModel;

    public SignupPresenter(SignupViewModel SignupViewModel) {
        this.SignupViewModel = SignupViewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        SignupState state = SignupViewModel.getState();
        state.setEmail(outputData.getEmail());
        state.setSignupError(false);
        System.out.println("presenter success");
        SignupViewModel.setState(state);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        SignupState state = SignupViewModel.getState();
        System.out.println(state.getEmail());
        state.setSignupError(true);
        System.out.println("presenter error");
        SignupViewModel.setState(state);
    }
}