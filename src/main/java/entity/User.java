package entity;

import java.util.List;
import java.util.Map;

/**
 * Represents a user in the system.
 * This class holds user-specific details like email, username, balance, portfolio, and transaction history.
 */
public class User {

    private String email;
    private String username;
    private double balance;
    private double portfolioValue;
    private Map<String, Double> portfolioHoldings;
    private List<Map<String, Object>> transactionHistory;

    /**
     * Constructor to initialize the User object with necessary information.
     *
     * @param email The user's email address.
     * @param username The user's username.
     * @param balance The user's account balance.
     * @param portfolioValue The total value of the user's portfolio.
     * @param portfolio A map representing the user's stock holdings with ticker symbols and their quantities.
     * @param transactions A list of transactions performed by the user, each represented as a map.
     */
    public User(String email, String username, double balance, double portfolioValue, Map<String, Double> portfolio, List<Map<String, Object>> transactions) {
        this.email = email;
        this.username = username;
        this.balance = balance;
        this.portfolioValue = portfolioValue;
        this.portfolioHoldings = portfolio;
        this.transactionHistory = transactions;
    }

    /**
     * Gets the user's email address.
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's current balance.
     *
     * @return The user's balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the user's account balance.
     *
     * @param balance The balance to set. It should be a positive value representing the user's balance.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Gets the user's username.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the current value of the user's portfolio.
     *
     * @return The value of the portfolio.
     */
    public double getPortfolioValue() {
        return portfolioValue;
    }

    /**
     * Sets the total value of the user's portfolio.
     *
     * @param portfolioValue The portfolio value to set.
     */
    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    /**
     * Gets the user's portfolio holdings.
     *
     * @return A map of stock tickers to quantities representing the user's portfolio.
     */
    public Map<String, Double> getPortfolioHoldings() {
        return portfolioHoldings;
    }

    /**
     * Sets the user's portfolio holdings.
     *
     * @param portfolioHoldings A map of stock tickers to quantities representing the user's portfolio.
     */
    public void setPortfolioHoldings(Map<String, Double> portfolioHoldings) {
        this.portfolioHoldings = portfolioHoldings;
    }

    /**
     * Gets the transaction history of the user.
     *
     * @return A list of maps, where each map represents a transaction with details (e.g., date, amount, etc.).
     */
    public List<Map<String, Object>> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Sets the user's transaction history.
     *
     * @param transactionHistory A list of maps representing the user's transaction history.
     *                         Each map contains details about individual transactions.
     */
    public void setTransactionHistory(List<Map<String, Object>> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}