package use_case.portfolio;

public interface PortfolioInputBoundary {
    void executeTransaction(PortfolioInputData inputData);
    PortfolioOutputData getPortfolioInfo();
}


