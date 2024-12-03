package interface_adapter.portfolio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Map;

public class PortfolioViewModel {
    private double totalBalance;
    private double portfolioBalance;
    private Object[][] portfolioData;
    private List<Map<String, Object>> transactionHistory;
    private double totalGainLoss;
    private double totalGainLossPercentage;
    private String errorMessage;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Add property change listener for automatic UI updates
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    // Setters that trigger property changes
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
        if (portfolioData == null) {
            portfolioData = new Object[0][0];  // Default to an empty 2D array if null
        }
        Object[][] oldValue = this.portfolioData;
        this.portfolioData = portfolioData;
        pcs.firePropertyChange("portfolioData", oldValue, portfolioData); // Fire property change
    }

    public void setTransactionHistory(List<Map<String, Object>> transactionHistory) {
        List<Map<String, Object>> oldValue = this.transactionHistory;
        this.transactionHistory = transactionHistory;
        pcs.firePropertyChange("transactionHistory", oldValue, transactionHistory);  // Fire property change event
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

//    // Getters for the data
//    public double getTotalBalance() {
//        return totalBalance;
//    }

    public double getPortfolioBalance() {
        return portfolioBalance;
    }

    public Object[][] getPortfolioData() {
        return portfolioData;
    }

    public List<Map<String, Object>> getTransactionHistory() {
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

    public void setErrorMessage(String errorMessage) {
        String oldValue = this.errorMessage;
        this.errorMessage = errorMessage;
        pcs.firePropertyChange("errorMessage", oldValue, errorMessage);
    }
}
