//package use_case.login;
//
//import data_access.LoginUserDataAccess;
//import org.junit.jupiter.api.Test;
//import use_case.login.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LoginInteractorTest {
//
//    @Test
//    void successTest() {
//        // Set up dependencies
//        LoginUserDataAccessInterface userDataAccess = new LoginUserDataAccess() {
//            @Override
//            public String fetchToken(String email, String password) {
//                if (email.equals("test@example.com") && password.equals("securePassword123")) {
//                    return "validToken";
//                }
//                return null;
//            }
//
//            @Override
//            public boolean verifyToken(String idToken) {
//                return "validToken".equals(idToken);
//            }
//        };
//
//        LoginOutputBoundary presenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData outputData) {
//                assertEquals("test@example.com", outputData.getEmail());
//                assertTrue(outputData.isLoginSuccess());
//            }
//
//            @Override
//            public void prepareFailView(String errorMessage) {
//                fail("Login failed unexpectedly: " + errorMessage);
//            }
//        };
//
//        LoginInteractor interactor = new LoginInteractor(userDataAccess, presenter);
//        LoginInputData inputData = new LoginInputData("test@example.com", "securePassword123");
//
//        // Execute interactor
//        interactor.execute(inputData);
//    }
//
//    @Test
//    void failureInvalidCredentialsTest() {
//        // Set up dependencies
//        LoginUserDataAccessInterface userDataAccess = new LoginUserDataAccess() {
//            @Override
//            public String fetchToken(String email, String password) {
//                return null; // Simulate invalid credentials
//            }
//
//            @Override
//            public boolean verifyToken(String idToken) {
//                return false; // Token verification should not be reached
//            }
//        };
//
//        LoginOutputBoundary presenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData outputData) {
//                fail("Login succeeded unexpectedly.");
//            }
//
//            @Override
//            public void prepareFailView(String errorMessage) {
//                assertEquals("Invalid credentials.", errorMessage);
//            }
//        };
//
//        LoginInteractor interactor = new LoginInteractor(userDataAccess, presenter);
//        LoginInputData inputData = new LoginInputData("wrong@example.com", "wrongPassword");
//
//        // Execute interactor
//        interactor.execute(inputData);
//    }
//
//    @Test
//    void failureTokenVerificationTest() {
//        // Set up dependencies
//        LoginUserDataAccessInterface userDataAccess = new LoginUserDataAccess() {
//            @Override
//            public String fetchToken(String email, String password) {
//                return "invalidToken"; // Simulate fetching a token
//            }
//
//            @Override
//            public boolean verifyToken(String idToken) {
//                return false; // Simulate token verification failure
//            }
//        };
//
//        LoginOutputBoundary presenter = new LoginOutputBoundary() {
//            @Override
//            public void prepareSuccessView(LoginOutputData outputData) {
//                fail("Login succeeded unexpectedly.");
//            }
//
//            @Override
//            public void prepareFailView(String errorMessage) {
//                assertEquals("Invalid credentials.", errorMessage);
//            }
//        };
//
//        LoginInteractor interactor = new LoginInteractor(userDataAccess, presenter);
//        LoginInputData inputData = new LoginInputData("test@example.com", "securePassword123");
//
//        // Execute interactor
//        interactor.execute(inputData);
//    }
//}