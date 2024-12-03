package entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a user in the system.
 * This class holds user-specific details like email, username, balance, portfolio, and transaction history.
 */
public class User {
    public User(String email, String username, double balance, double portfolioValue, LinkedHashMap<String,
            ArrayList<String>> portfolio, ArrayList<String> transactions) {
        this.email = email;
        this.username = username;
        this.balance = balance;
        this.portfolioValue = portfolioValue;
        this.portfolio = portfolio;
        this.transactions = transactions;
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
    public LinkedHashMap<String, ArrayList<String>> getPortfolio() {
        return portfolio;
    }
    public void setPortfolio(LinkedHashMap<String, ArrayList<String>> portfolio) {
        this.portfolio = portfolio;
    }
    public ArrayList<String> getTransactions() {
        return transactions;
    }
    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }
    private String email;
    private String username;
    private double balance;
    private double portfolioValue;
    private LinkedHashMap<String, ArrayList<String>> portfolio;
    private ArrayList<String> transactions;
}