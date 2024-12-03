package entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class User {

    public User(String email, String username, double balance, double portfolioValue, Map<String, Double> portfolio, List<Map<String, Object>> transactions) {
        this.email = email;
        this.username = username;
        this.balance = balance;
        this.portfolioValue = portfolioValue;
        this.portfolioHoldings = portfolio;
        this.transactionHistory = transactions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(int portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public Map<String, Double> getPortfolioHoldings() {
        return portfolioHoldings;
    }

    public void setPortfolioHoldings(Map<String, Double> portfolioHoldings) {
        this.portfolioHoldings = portfolioHoldings;
    }

    public List<Map<String, Object>> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Map<String, Object>> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    private String email;
    private String username;
    private double balance;
    private double portfolioValue;
    private Map<String, Double> portfolioHoldings;
    private List<Map<String, Object>> transactionHistory;
}
