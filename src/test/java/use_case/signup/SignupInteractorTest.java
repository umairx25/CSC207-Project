package use_case.signup;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import data_access.UserDataAccess;
import entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import use_case.signup.*;

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
        // Set up dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "", "") {
            @Override
            public boolean signup(User user, String password, Firestore db) {
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

        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter);
        SignupInputData inputData = new SignupInputData("test@example.com", "securePassword123", "testuser");

        // Execute interactor
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() throws FirebaseAuthException {
        // Set up dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "", "") {
            @Override
            public boolean signup(User user, String password, Firestore db) {
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
                assertEquals("Invalid credentials.", errorMessage);
            }
        };

        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter);
        SignupInputData inputData = new SignupInputData("existing@example.com", "securePassword123", "existinguser");

        // Execute interactor
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidEmailFormatTest() {
        // Set up dependencies
        UserDataAccess userDataAccess = new UserDataAccess("", "", "") {
            @Override
            public boolean signup(User user, String password, Firestore db) {
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

        SignupInteractor interactor = new SignupInteractor(userDataAccess, presenter);
        SignupInputData inputData = new SignupInputData("invalid-email", "securePassword123", "invaliduser");

        // Execute interactor
        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactor.execute(inputData));
        assertEquals("Invalid email format", exception.getMessage());
    }
}
