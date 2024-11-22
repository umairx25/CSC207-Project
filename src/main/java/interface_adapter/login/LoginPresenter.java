package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    @Override
    public void present(LoginOutputData outputData) {
        if (outputData.isSuccess()) {
            System.out.println("Login successful! Token: " + outputData.getMessage());
        } else {
            System.out.println("Login failed: " + outputData.getMessage());
        }
    }
}