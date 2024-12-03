package data_access;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import use_case.portfolio.PortfolioDataAccessInterface;

import java.util.*;

public class InMemoryPortfolioUserDataAccess implements PortfolioDataAccessInterface {
    private final PortfolioFirestoreAccess firestoreAccess;
    private final String userEmail;
    private final Firestore db;

    public InMemoryPortfolioUserDataAccess(String userEmail) {
        this.firestoreAccess = new PortfolioFirestoreAccess();
        this.userEmail = userEmail;
        this.db = FirestoreClient.getFirestore();
    }

    @Override
    public void executeBuyOrder(String company, int quantity) {
        try {
            StockDataAccess dataAccess = new StockDataAccess();
            double pricePerShare = dataAccess.getCurrentPrice(company);
            if (pricePerShare == -1) {
                throw new RuntimeException("Failed to fetch stock price for " + company);
            }

            double totalCost = pricePerShare * quantity;

            Map<String, Object> userData = firestoreAccess.getUserData(userEmail);
            double balance = (double) userData.get("balance");

            if (balance < totalCost) {
                throw new IllegalArgumentException("Not enough balance to execute buy order.");
            }

            // Update Firestore balance
            firestoreAccess.updateBalance(userEmail, balance - totalCost);

            // Update portfolio holdings (correctly add to the quantity)
            firestoreAccess.updatePortfolio(userEmail, company, quantity);  // Update portfolio holdings

            // Add transaction record
            firestoreAccess.addTransaction(userEmail, "BUY", company, quantity, pricePerShare);

            // Refresh transaction history
            getTransactionHistory();

        } catch (Exception e) {
            throw new RuntimeException("Error executing buy order: " + e.getMessage(), e);
        }
    }

    @Override
    public void executeSellOrder(String company, int quantity) {
        try {
            StockDataAccess dataAccess = new StockDataAccess();
            double pricePerShare = dataAccess.getCurrentPrice(company);
            if (pricePerShare == -1) {
                throw new RuntimeException("Failed to fetch stock price for " + company);
            }

            DocumentReference userDoc = db.collection("users").document(userEmail);
            DocumentSnapshot snapshot = userDoc.get().get();

            Map<String, Long> portfolio = (Map<String, Long>) snapshot.get("portfolioHoldings");
            if (portfolio == null) {
                throw new IllegalArgumentException("Portfolio is not initialized for user: " + userEmail);
            }

            long currentQuantity = portfolio.getOrDefault(company, 0L);
            if (currentQuantity < quantity) {
                throw new IllegalArgumentException("Not enough shares of " + company + " to sell.");
            }

            // Deduct the shares from the portfolio
            portfolio.put(company, currentQuantity - quantity);

            // Calculate the total earnings from the sale
            double totalEarnings = quantity * pricePerShare;

            // Retrieve the current balance
            Double currentBalance = snapshot.getDouble("balance");
            if (currentBalance == null) {
                throw new RuntimeException("Balance field is missing for user: " + userEmail);
            }

            double newBalance = currentBalance + totalEarnings;

            // Update Firestore with updated portfolio and balance
            userDoc.update("portfolioHoldings", portfolio).get();
            userDoc.update("balance", newBalance).get();

            // Add transaction record for the sell
            firestoreAccess.addTransaction(userEmail, "SELL", company, quantity, pricePerShare);

            // Refresh transaction history
            getTransactionHistory();

        } catch (Exception e) {
            throw new RuntimeException("Error executing sell order: " + e.getMessage(), e);
        }
    }


    @Override
    public Object[][] getPortfolioData() {
        try {
            Map<String, Object> userData = firestoreAccess.getUserData(userEmail);
            Map<String, Long> portfolio = (Map<String, Long>) userData.get("portfolioHoldings");

            if (portfolio == null || portfolio.isEmpty()) {
                return new Object[0][];
            }

            StockDataAccess dataAccess = new StockDataAccess();
            Object[][] data = new Object[portfolio.size()][6];
            int i = 0;

            for (Map.Entry<String, Long> entry : portfolio.entrySet()) {
                String company = entry.getKey();
                long quantity = entry.getValue();
                double marketPrice = dataAccess.getCurrentPrice(company);
                double avgCost = getAverageCost(company);
                double totalValue = quantity * marketPrice;
                double gainLoss = (marketPrice - avgCost) * quantity;

                data[i++] = new Object[]{
                        company, quantity, avgCost, marketPrice, totalValue, gainLoss
                };
            }

            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching portfolio data: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getTransactionHistory() {
        try {
            return firestoreAccess.getTransactionHistory(userEmail);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching transaction history: " + e.getMessage(), e);
        }
    }

    @Override
    public double getTotalBalance() {
        try {
            return firestoreAccess.getBalance(userEmail);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching total balance: " + e.getMessage(), e);
        }
    }

    @Override
    public double getPortfolioBalance() {
        try {
            Map<String, Long> portfolio = getPortfolioHoldings();
            if (portfolio == null) return 0;

            StockDataAccess dataAccess = new StockDataAccess();
            double totalValue = 0;

            for (Map.Entry<String, Long> entry : portfolio.entrySet()) {
                double marketPrice = dataAccess.getCurrentPrice(entry.getKey());
                totalValue += entry.getValue() * marketPrice;
            }

            return totalValue;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching portfolio balance: " + e.getMessage(), e);
        }
    }

    @Override
    public double getTotalGainLoss() {
        double portfolioValue = getPortfolioBalance();
        double investedCapital = getInvestedCapital();
        return portfolioValue - investedCapital;
    }

    @Override
    public double getTotalGainLossPercentage() {
        double investedCapital = getInvestedCapital();
        return investedCapital == 0 ? 0 : (getTotalGainLoss() / investedCapital) * 100;
    }

    private double getAverageCost(String company) {
        try {
            List<Map<String, Object>> history = getTransactionHistory();
            double totalCost = 0.0;
            int totalShares = 0;

            for (Map<String, Object> transaction : history) {
                if ("BUY".equalsIgnoreCase((String) transaction.get("type"))
                        && company.equals(transaction.get("company"))) {
                    int quantity = ((Number) transaction.get("quantity")).intValue();
                    double price = ((Number) transaction.get("price")).doubleValue();

                    totalCost += quantity * price;
                    totalShares += quantity;
                }
            }

            return totalShares == 0 ? 0.0 : totalCost / totalShares;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating average cost for company: " + company, e);
        }
    }

    private double getInvestedCapital() {
        try {
            Map<String, Long> portfolio = getPortfolioHoldings();
            double totalCost = 0.0;

            for (String company : portfolio.keySet()) {
                totalCost += portfolio.get(company) * getAverageCost(company);
            }

            return totalCost;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating invested capital: " + e.getMessage(), e);
        }
    }

    private Map<String, Long> getPortfolioHoldings() {
        try {
            Map<String, Object> userData = firestoreAccess.getUserData(userEmail);
            return (Map<String, Long>) userData.get("portfolioHoldings");
        } catch (Exception e) {
            throw new RuntimeException("Error fetching portfolio holdings: " + e.getMessage(), e);
        }
    }
}
