package use_case.portfolio;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PortfolioInteractor implements PortfolioInputBoundary {
    private final PortfolioDataAccessInterface dataAccess;
    private final PortfolioOutputBoundary presenter;

    public PortfolioInteractor(
            PortfolioDataAccessInterface dataAccess,
            PortfolioOutputBoundary presenter
    ) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void executeTransaction(PortfolioInputData inputData) {
        try {
            if ("BUY".equalsIgnoreCase(inputData.getTransactionType())) {
                dataAccess.executeBuyOrder(inputData.getCompany(), inputData.getQuantity());
            } else if ("SELL".equalsIgnoreCase(inputData.getTransactionType())) {
                dataAccess.executeSellOrder(inputData.getCompany(), inputData.getQuantity());
            } else {
                throw new IllegalArgumentException("Invalid transaction type");
            }

            // Fetch updated data after executing transaction
            PortfolioOutputData updatedData = getPortfolioInfo();
            presenter.presentPortfolioInfo(updatedData);

        } catch (Exception e) {
            presenter.presentTransactionError(e.getMessage());
        }
    }

    @Override
        public PortfolioOutputData getPortfolioInfo() {
            double totalBalance = dataAccess.getTotalBalance();
            double portfolioBalance = dataAccess.getPortfolioBalance();
            Object[][] portfolioData = dataAccess.getPortfolioData();
            List<Map<String, Object>> transactionHistory = dataAccess.getTransactionHistory();
            double totalGainLoss = dataAccess.getTotalGainLoss();
            double totalGainLossPercentage = dataAccess.getTotalGainLossPercentage();

            // Debug fetched data
            System.out.println("Total Balance: " + totalBalance);
            System.out.println("Portfolio Balance: " + portfolioBalance);
            System.out.println("Portfolio Data: " + Arrays.deepToString(portfolioData));
            System.out.println("Transaction History: " + transactionHistory);

            return new PortfolioOutputData(
                    totalBalance,
                    portfolioBalance,
                    portfolioData,
                    transactionHistory,
                    totalGainLoss,
                    totalGainLossPercentage
            );
        }
}
