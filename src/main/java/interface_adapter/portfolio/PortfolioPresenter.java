package interface_adapter.portfolio;

import use_case.portfolio.PortfolioOutputBoundary;
import use_case.portfolio.PortfolioOutputData;


public class PortfolioPresenter implements PortfolioOutputBoundary {
    private final PortfolioViewModel viewModel;

    public PortfolioPresenter(PortfolioViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentPortfolioInfo(PortfolioOutputData outputData) {
        viewModel.setTotalBalance(outputData.getTotalBalance());
        viewModel.setPortfolioBalance(outputData.getPortfolioBalance());
        viewModel.setPortfolioData(outputData.getPortfolioData());
        viewModel.setTransactionHistory(outputData.getTransactionHistory());
        viewModel.setTotalGainLoss(outputData.getTotalGainLoss());
        viewModel.setTotalGainLossPercentage(outputData.getTotalGainLossPercentage());

        // Clear any previous error messages
        viewModel.setErrorMessage(null);
    }

    @Override
    public void presentTransactionError(String errorMessage) {
        // Set error message in the ViewModel
        viewModel.setErrorMessage(errorMessage);
    }
}