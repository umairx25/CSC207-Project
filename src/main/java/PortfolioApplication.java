import data_access.InMemoryPortfolioUserDataAccess;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioPresenter;
import interface_adapter.portfolio.PortfolioViewModel;
import use_case.portfolio.PortfolioDataAccessInterface;
import use_case.portfolio.PortfolioInteractor;
import frameworks_driver.view.Portfolio.BuyButton;
import frameworks_driver.view.Portfolio.CompanyTextField;
import frameworks_driver.view.Portfolio.HistoryPanel;
import frameworks_driver.view.Portfolio.PortfolioPanel;
import frameworks_driver.view.Portfolio.PortfolioView;
import frameworks_driver.view.Portfolio.QuantityTextField;
import frameworks_driver.view.Portfolio.SellButton;

import javax.swing.*;

public class PortfolioApplication {
    public static void main(String[] args) {
        // Instantiate the data access layer
        InMemoryPortfolioUserDataAccess portfolioDataAccess = new InMemoryPortfolioUserDataAccess();

        // Create the ViewModel
        PortfolioViewModel portfolioViewModel = new PortfolioViewModel();

        // Create the Presenter
        PortfolioPresenter portfolioPresenter = new PortfolioPresenter(portfolioViewModel);

        // Create the Interactor
        PortfolioInteractor portfolioInteractor = new PortfolioInteractor(
                (PortfolioDataAccessInterface) portfolioDataAccess,
            portfolioPresenter
        );

        // Create the Controller
        PortfolioController portfolioController = new PortfolioController(portfolioInteractor);

        // Create text fields
        CompanyTextField companyTextField = new CompanyTextField();
        QuantityTextField quantityTextField = new QuantityTextField();

        // Create buttons
        BuyButton buyButton = new BuyButton(portfolioController, companyTextField, quantityTextField);
        SellButton sellButton = new SellButton(portfolioController, companyTextField, quantityTextField);

        // Create panels
        // Initial data for panels
        Object[][] initialPortfolioData = portfolioDataAccess.getPortfolioData();
        java.util.List<String> initialTransactionHistory = portfolioDataAccess.getTransactionHistory();

        PortfolioPanel portfolioPanel = new PortfolioPanel();
        portfolioPanel.updateTableData(initialPortfolioData);

        HistoryPanel historyPanel = new HistoryPanel(initialTransactionHistory);

        // Add property change listeners to update UI components
        portfolioViewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "portfolioData":
                    Object[][] newData = (Object[][]) evt.getNewValue();
                    portfolioPanel.updateTableData(newData);
                    break;
                case "transactionHistory":
                    java.util.List<String> newHistory = (java.util.List<String>) evt.getNewValue();
                    historyPanel.updateHistory(newHistory);
                    break;
                case "errorMessage":
                    String errorMessage = (String) evt.getNewValue();
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            null, 
                            errorMessage, 
                            "Transaction Error", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;
            }
        });

        // Create the main Portfolio View
        PortfolioView portfolioView = new PortfolioView(
            portfolioViewModel,
            companyTextField,
            quantityTextField,
            buyButton,
            sellButton,
            portfolioPanel,
            historyPanel
        );

        // Display the view
        SwingUtilities.invokeLater(() -> {
            portfolioView.setVisible(true);
        });
    }
}
