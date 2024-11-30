package use_case.portfolio;

import java.util.List;


public class PortfolioOutputData {
    private final double totalBalance;
    private final double portfolioBalance;
    private final Object[][] portfolioData;
    private final List<String> transactionHistory;
    private final double totalGainLoss;
    private final double totalGainLossPercentage;

    public PortfolioOutputData(
            double totalBalance,
            double portfolioBalance,
            Object[][] portfolioData,
            List<String> transactionHistory,
            double totalGainLoss,
            double totalGainLossPercentage
    ) {
        this.totalBalance = totalBalance;
        this.portfolioBalance = portfolioBalance;
        this.portfolioData = portfolioData;
        this.transactionHistory = transactionHistory;
        this.totalGainLoss = totalGainLoss;
        this.totalGainLossPercentage = totalGainLossPercentage;
    }

    // Getters for each field
    public double getTotalBalance() {
        return totalBalance;
    }

    public double getPortfolioBalance() {
        return portfolioBalance;
    }

    public Object[][] getPortfolioData() {
        return portfolioData;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public double getTotalGainLoss() {
        return totalGainLoss;
    }

    public double getTotalGainLossPercentage() {
        return totalGainLossPercentage;
    }
}