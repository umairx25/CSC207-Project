
package use_case.signup;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.cloud.firestore.Firestore;
import data_access.UserDataAccess;
import entity.CurrentUser;
import entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    @BeforeAll
    static void setupFirebase() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("config.json")))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            fail("Failed to initialize Firebase: " + e.getMessage());
        }
    }

    @Test
    void successTest() throws FirebaseAuthException {
        // Mock dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "      ") {
            @Override
            public boolean signup(String password, Firestore db) {
                return true; // Simulate successful signup
            }

            @Override
            public boolean existingUser(String email) {
                return false; // Simulate that user doesn't exist
            }
        };

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                assertEquals("test@example.com", outputData.getEmail());
                assertTrue(outputData.isSignupSuccess());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Signup failed unexpectedly: " + errorMessage);
            }
        };

        CurrentUser currentUser = new CurrentUser();
        currentUser.setEmail("test@example.com");
        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter, currentUser);
        SignupInputData inputData = new SignupInputData("test@example.com", "securePassword123", "testuser");

        // Execute interactor
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() throws FirebaseAuthException {
        // Mock dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "      ") {
            @Override
            public boolean signup(String password, Firestore db) {
                return false; // Simulate failure due to existing user
            }

            @Override
            public boolean existingUser(String email) {
                return true; // Simulate that user exists
            }
        };

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Signup succeeded unexpectedly.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Account already exists or signup failed.", errorMessage);
            }
        };

        CurrentUser currentUser = new CurrentUser();
        currentUser.setEmail("existing@example.com");
        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter, currentUser);
        SignupInputData inputData = new SignupInputData("existing@example.com", "securePassword123", "existinguser");

        // Execute interactor
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidEmailFormatTest() {
        // Mock dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "      ") {
            @Override
            public boolean signup(String password, Firestore db) {
                throw new IllegalArgumentException("Invalid email format");
            }

            @Override
            public boolean existingUser(String email) {
                return false;
            }
        };

        SignupOutputBoundary presenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData outputData) {
                fail("Signup succeeded unexpectedly.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Unexpected error: " + errorMessage);
            }
        };

        CurrentUser currentUser = new CurrentUser();
        currentUser.setEmail("invalid-email");
        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter, currentUser);
        SignupInputData inputData = new SignupInputData("invalid-email", "securePassword123", "invaliduser");

        // Execute interactor
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Invalid email format", exception.getMessage());
    }
}