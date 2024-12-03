package use_case.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface PortfolioDataAccessInterface {
    double getTotalBalance();
    double getPortfolioBalance();
//    List<String[]> getPortfolioTableData();
    Object[][] getPortfolioData();
    List<Map<String, Object>> getTransactionHistory();
    double getTotalGainLoss();
    double getTotalGainLossPercentage();
    void executeBuyOrder(String company, int quantity);
    void executeSellOrder(String company, int quantity);
}