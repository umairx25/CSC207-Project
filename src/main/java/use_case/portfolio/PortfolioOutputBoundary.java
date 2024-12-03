package use_case.portfolio;


public interface PortfolioOutputBoundary {
    void presentPortfolioInfo(PortfolioOutputData outputData);
    void presentTransactionError(String errorMessage);
}
