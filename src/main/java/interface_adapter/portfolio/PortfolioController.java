package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;
import use_case.portfolio.PortfolioOutputData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PortfolioController {
    private final PortfolioInputBoundary portfolioInteractor;
    private final PortfolioViewModel viewModel;

    // Constructor now accepts both the interactor and the view model
    public PortfolioController(PortfolioInputBoundary portfolioInteractor, PortfolioViewModel viewModel) {
        this.portfolioInteractor = portfolioInteractor;
        this.viewModel = viewModel;
    }

    // Executes buy order, delegates to PortfolioInteractor
    public void executeBuyOrder(String company, int quantity) {
        PortfolioInputData inputData = new PortfolioInputData(company, quantity, "BUY");
        portfolioInteractor.executeTransaction(inputData);
        refreshPortfolioData();  // Refresh after buying
        updateTransactionHistory();  // Update transaction history
    }

    // Executes sell order, delegates to PortfolioInteractor
    public void executeSellOrder(String company, int quantity) {
        PortfolioInputData inputData = new PortfolioInputData(company, quantity, "SELL");
        portfolioInteractor.executeTransaction(inputData);
        refreshPortfolioData();  // Refresh after selling
        updateTransactionHistory();  // Update transaction history
    }

    // Refresh the portfolio data in the ViewModel
    public void refreshPortfolioData() {
        // Fetch the updated portfolio info from Firestore (via interactor)
        PortfolioOutputData updatedData = portfolioInteractor.getPortfolioInfo();

        // Update the ViewModel with the new data
        viewModel.setTotalBalance(updatedData.getTotalBalance());
        viewModel.setPortfolioBalance(updatedData.getPortfolioBalance());
        viewModel.setPortfolioData(updatedData.getPortfolioData());
        viewModel.setTotalGainLoss(updatedData.getTotalGainLoss());
        viewModel.setTotalGainLossPercentage(updatedData.getTotalGainLossPercentage());
    }

    // Explicitly update the transaction history
    public void updateTransactionHistory() {
        // Get the updated transaction history
        List<Map<String, Object>> updatedHistory = portfolioInteractor.getPortfolioInfo().getTransactionHistory();

        // Set the updated transaction history in the ViewModel
        viewModel.setTransactionHistory(updatedHistory);  // Notify the UI with the new history
    }
}
