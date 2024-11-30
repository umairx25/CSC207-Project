package interface_adapter.portfolio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class PortfolioViewModel {
    private double totalBalance;
    private double portfolioBalance;
    private Object[][] portfolioData;
    private java.util.List<String> transactionHistory;
    private double totalGainLoss;
    private double totalGainLossPercentage;
    private String errorMessage;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Add property change listener
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    // Setters with property change support
    public void setTotalBalance(double totalBalance) {
        double oldValue = this.totalBalance;
        this.totalBalance = totalBalance;
        pcs.firePropertyChange("totalBalance", oldValue, totalBalance);
    }

    public void setPortfolioBalance(double portfolioBalance) {
        double oldValue = this.portfolioBalance;
        this.portfolioBalance = portfolioBalance;
        pcs.firePropertyChange("portfolioBalance", oldValue, portfolioBalance);
    }

    public void setPortfolioData(Object[][] portfolioData) {
        Object[][] oldValue = this.portfolioData;
        this.portfolioData = portfolioData;
        pcs.firePropertyChange("portfolioData", oldValue, portfolioData);
    }

    public void setTransactionHistory(java.util.List<String> transactionHistory) {
        java.util.List<String> oldValue = this.transactionHistory;
        this.transactionHistory = transactionHistory;
        pcs.firePropertyChange("transactionHistory", oldValue, transactionHistory);
    }

    public void setTotalGainLoss(double totalGainLoss) {
        double oldValue = this.totalGainLoss;
        this.totalGainLoss = totalGainLoss;
        pcs.firePropertyChange("totalGainLoss", oldValue, totalGainLoss);
    }

    public void setTotalGainLossPercentage(double totalGainLossPercentage) {
        double oldValue = this.totalGainLossPercentage;
        this.totalGainLossPercentage = totalGainLossPercentage;
        pcs.firePropertyChange("totalGainLossPercentage", oldValue, totalGainLossPercentage);
    }

    public void setErrorMessage(String errorMessage) {
        String oldValue = this.errorMessage;
        this.errorMessage = errorMessage;
        pcs.firePropertyChange("errorMessage", oldValue, errorMessage);
    }

    // Getters
    public double getTotalBalance() {
        return totalBalance;
    }

    public double getPortfolioBalance() {
        return portfolioBalance;
    }

    public Object[][] getPortfolioData() {
        return portfolioData;
    }

    public java.util.List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public double getTotalGainLoss() {
        return totalGainLoss;
    }

    public double getTotalGainLossPercentage() {
        return totalGainLossPercentage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object[][] getPortfolioTableData() {
        return portfolioData;
    }

    public java.util.List<String> getTransactionHistoryList() {
        return transactionHistory;
    }

    public String getBalanceDisplay() {
        return String.format("$%.2f", totalBalance);
    }

    public String getPortfolioBalanceDisplay() {
        return String.format("$%.2f", portfolioBalance);
    }

    public String getGainLossPercentageDisplay() {
        return String.format("%.2f%%", totalGainLossPercentage);
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isEmpty();
    }

    public void clearError() {
        setErrorMessage(null);
    }
}
