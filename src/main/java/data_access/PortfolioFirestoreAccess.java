package data_access;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PortfolioFirestoreAccess {
    private final Firestore db;

    public PortfolioFirestoreAccess() {
        this.db = FirestoreClient.getFirestore();
    }

    public Map<String, Object> getUserData(String email) {
        try {
            DocumentReference docRef = db.collection("users").document(email);
            DocumentSnapshot snapshot = docRef.get().get();
            return snapshot.exists() ? snapshot.getData() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user data: " + e.getMessage(), e);
        }
    }

    public void updateBalance(String email, double newBalance) {
        try {
            DocumentReference docRef = db.collection("users").document(email);
            ApiFuture<WriteResult> result = docRef.update("balance", newBalance);
            result.get(); // Wait for the update to complete
        } catch (Exception e) {
            throw new RuntimeException("Error updating balance: " + e.getMessage(), e);
        }
    }

    public void updatePortfolio(String email, String company, int quantity) {
        try {
            DocumentReference docRef = db.collection("users").document(email);
            db.runTransaction(transaction -> {
                DocumentSnapshot snapshot = transaction.get(docRef).get();
                Map<String, Long> portfolio = (Map<String, Long>) snapshot.get("portfolioHoldings");

                if (portfolio == null) {
                    portfolio = new HashMap<>();
                }

                long newQuantity = portfolio.getOrDefault(company, 0L) + quantity;
                portfolio.put(company, newQuantity);

                transaction.update(docRef, "portfolioHoldings", portfolio);
                return null; // Firestore transactions require a return value
            }).get();
        } catch (Exception e) {
            throw new RuntimeException("Error updating portfolio: " + e.getMessage(), e);
        }
    }


    public List<Map<String, Object>> addTransaction(String userEmail, String type, String company, int quantity, double price) {
        try {
            // Get the Firestore document reference for the user
            DocumentReference docRef = db.collection("users").document(userEmail);

            // Create the new transaction
            Map<String, Object> transaction = new HashMap<>();
            transaction.put("type", type);      // BUY/SELL
            transaction.put("company", company);
            transaction.put("quantity", quantity);
            transaction.put("price", price);

            // Update Firestore by adding the new transaction to the array
            ApiFuture<WriteResult> result = docRef.update("transactionHistory", FieldValue.arrayUnion(transaction));
            result.get(); // Wait for the update to complete

            System.out.println("Transaction added: " + type + " " + quantity + " of " + company + " at " + price);

            // After adding the transaction, fetch the updated transaction history
            List<Map<String, Object>> updatedHistory = getTransactionHistory(userEmail);

            // Print the updated history for debugging
            System.out.println("Updated Transaction History: " + updatedHistory);

            return updatedHistory; // Now return the updated history instead of updating the ViewModel here

        } catch (Exception e) {
            throw new RuntimeException("Error adding transaction: " + e.getMessage(), e);
        }
    }




    public List<Map<String, Object>> getTransactionHistory(String userEmail) {
        try {
            DocumentReference userDoc = db.collection("users").document(userEmail);
            DocumentSnapshot snapshot = userDoc.get().get();

            // Retrieve the transaction history from Firestore
            List<Map<String, Object>> transactionHistory = (List<Map<String, Object>>) snapshot.get("transactionHistory");
            if (transactionHistory == null) {
                transactionHistory = new ArrayList<>(); // Initialize as an empty list if null
            }

            return transactionHistory; // Return the list of transactions
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transaction history: " + e.getMessage(), e);
        }
    }


    public double getBalance(String email) {
        try {
            DocumentReference docRef = db.collection("users").document(email);
            DocumentSnapshot snapshot = docRef.get().get();

            Double balance = snapshot.getDouble("balance");
            if (balance == null) {
                throw new RuntimeException("Balance field is missing for user: " + email);
            }

            return balance;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching balance: " + e.getMessage(), e);
        }
    }

    public void initializeUser(String email, double balance) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(email);
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("balance", balance);
        userData.put("transactionHistory", new ArrayList<Map<String, Object>>());
        userData.put("portfolioHoldings", new HashMap<String, Integer>());

        ApiFuture<WriteResult> result = docRef.set(userData);
        System.out.println("User initialized at: " + result.get().getUpdateTime());
    }
}
