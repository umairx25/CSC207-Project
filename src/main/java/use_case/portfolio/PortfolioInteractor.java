package use_case.portfolio;

import java.util.List;

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
                dataAccess.executeBuyOrder(
                        inputData.getCompany(),
                        inputData.getQuantity()
                );
                System.out.println(inputData.getCompany());
            } else if ("SELL".equalsIgnoreCase(inputData.getTransactionType())) {
                dataAccess.executeSellOrder(
                        inputData.getCompany(),
                        inputData.getQuantity()
                );
            } else {
                throw new IllegalArgumentException("Invalid transaction type");
            }

            // After successful transaction, get and present updated portfolio info
            presenter.presentPortfolioInfo(getPortfolioInfo());
        } catch (Exception e) {
            presenter.presentTransactionError(e.getMessage());

        }
    }

    @Override
    public PortfolioOutputData getPortfolioInfo() {
        return new PortfolioOutputData(
                dataAccess.getTotalBalance(),
                dataAccess.getPortfolioBalance(),
                dataAccess.getPortfolioData(),
                dataAccess.getTransactionHistory(),
                dataAccess.getTotalGainLoss(),
                dataAccess.getTotalGainLossPercentage()
        );
    }


}