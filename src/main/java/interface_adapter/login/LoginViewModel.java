package interface_adapter.login;

import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 * This class holds the state for the login view and initializes it with the necessary login state.
 */
public class LoginViewModel extends ViewModel<LoginState> {

    /**
     * Constructs a new LoginViewModel and initializes the state.
     * Sets the state to a new instance of LoginState.
     */
    public LoginViewModel() {
        super("log in");
        setState(new LoginState());
    }

}