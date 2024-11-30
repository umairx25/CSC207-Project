package use_case.portfolio;

import java.util.List;


public interface PortfolioDataAccessInterface {
    double getTotalBalance();
    double getPortfolioBalance();
    List<String[]> getPortfolioTableData();
    Object[][] getPortfolioData();
    List<String> getTransactionHistory();
    double getTotalGainLoss();
    double getTotalGainLossPercentage();
    void executeBuyOrder(String company, int quantity);
    void executeSellOrder(String company, int quantity);
    boolean hasEnoughShares(String company, int quantity);
    boolean hasEnoughBalance(String company, int quantity);
}