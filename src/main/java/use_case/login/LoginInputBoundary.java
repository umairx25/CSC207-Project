package use_case.login;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * Input Boundary for actions related to logging in.
 */
public interface LoginInputBoundary {
    void execute(LoginInputData loginInputData) throws ExecutionException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException;
}
