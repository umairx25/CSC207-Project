package interface_adapter.signup;

import interface_adapter.ViewModel;

/**
 * The View Model for the Signup View.
 * This class holds the state for the signup view and initializes it with the necessary signup state.
 */
public class SignupViewModel extends ViewModel<SignupState> {

    /**
     * Constructs a new SignupViewModel and initializes the state.
     * Sets the state to a new instance of SignupState, which holds the signup information.
     */
    public SignupViewModel() {
        super("sign up");
        setState(new SignupState());
    }
}