package interface_adapter.portfolio;

import use_case.portfolio.PortfolioInputBoundary;
import use_case.portfolio.PortfolioInputData;

public class PortfolioController {
    private final PortfolioInputBoundary portfolioInteractor;

    public PortfolioController(PortfolioInputBoundary portfolioInteractor) {
        this.portfolioInteractor = portfolioInteractor;
    }


    public void executeBuyOrder(String company, int quantity) {
        PortfolioInputData inputData = new PortfolioInputData(company, quantity, "BUY");
        portfolioInteractor.executeTransaction(inputData);
    }

    public void executeSellOrder(String company, int quantity) {
        PortfolioInputData inputData = new PortfolioInputData(company, quantity, "SELL");
        portfolioInteractor.executeTransaction(inputData);
    }

    public void refreshPortfolioData() {
        portfolioInteractor.getPortfolioInfo();
    }


}