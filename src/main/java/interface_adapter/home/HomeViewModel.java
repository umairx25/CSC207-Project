package interface_adapter.home;

import interface_adapter.ViewModel;

/**
 * The View Model for the Logged In View.
 */
public class HomeViewModel extends ViewModel<HomeState> {

    public HomeViewModel() {
        System.out.println("home view model reached view name retrived");
        super("home view");
    }
}

