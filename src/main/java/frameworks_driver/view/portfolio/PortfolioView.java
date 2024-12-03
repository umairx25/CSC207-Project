package frameworks_driver.view.portfolio;

import app.Builder;
import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.List;

import frameworks_driver.view.style_helpers.ColourManager;

public class PortfolioView extends JPanel {
    private final PortfolioViewModel viewModel;
    private final PortfolioController controller;

    private final PortfolioPanel portfolioPanel; // Reference to PortfolioPanel
    private final HistoryPanel historyPanel;     // Reference to HistoryPanel

    public PortfolioView(PortfolioViewModel viewModel, PortfolioController controller, Builder builder) {
        this.viewModel = viewModel;
        this.controller = controller;

        // Initialize UI components
        setLayout(new BorderLayout());
        setSize(1000, 700);
        setBackground(ColourManager.NAVY_BLUE);  // Set background to light blue

        // Header Panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ColourManager.NAVY_BLUE); // Set header panel to dark blue

        JLabel titleLabel = new JLabel("Portfolio", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(ColourManager.DARK_BLUE);  // Set text to white
        HomeButton homeButton = new HomeButton();
        homeButton.addHomeButtonListener(e -> builder.showView("home"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(homeButton, BorderLayout.EAST);

        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balancePanel.setBackground(ColourManager.NAVY_BLUE); // Set balance panel to dark blue
        JLabel balanceLabel = new JLabel("Balance:");
        JLabel portfolioBalanceLabel = new JLabel("Portfolio:");
        balanceLabel.setForeground(ColourManager.WHITE);  // Set text to white
        portfolioBalanceLabel.setForeground(ColourManager.WHITE);  // Set text to white
        balancePanel.add(balanceLabel);
        balancePanel.add(portfolioBalanceLabel);

        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(balancePanel, BorderLayout.SOUTH);

        // Transaction Input Panel setup
        JPanel transactionPanel = new JPanel(new FlowLayout());
        transactionPanel.setBackground(ColourManager.NAVY_BLUE); // Set transaction panel to dark blue

        CompanyTextField companyTextField = new CompanyTextField();
        QuantityTextField quantityTextField = new QuantityTextField();
        JLabel companyLabel = new JLabel("Company:");
        JLabel quantityLabel = new JLabel("Quantity:");
        companyLabel.setForeground(ColourManager.WHITE);
        quantityLabel.setForeground(ColourManager.WHITE);
        transactionPanel.add(companyLabel);
        transactionPanel.add(companyTextField);
        transactionPanel.add(quantityLabel);
        transactionPanel.add(quantityTextField);

        // Apply grey color to buttons
        JButton buyButton = new BuyButton(controller, companyTextField, quantityTextField);
        JButton sellButton = new SellButton(controller, companyTextField, quantityTextField);
        JButton refreshButton = new RefreshButton(controller);

        buyButton.setBackground(ColourManager.DARK_GRAY);
        sellButton.setBackground(ColourManager.DARK_GRAY);
        refreshButton.setBackground(ColourManager.DARK_GRAY);

        transactionPanel.add(buyButton);
        transactionPanel.add(sellButton);
        transactionPanel.add(refreshButton);

        // Main Content Area setup
        portfolioPanel = new PortfolioPanel();
        historyPanel = new HistoryPanel(viewModel.getTransactionHistory());

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(ColourManager.LIGHT_GRAY);  // Set main content background to light blue
        mainContentPanel.add(portfolioPanel, BorderLayout.CENTER);
        mainContentPanel.add(historyPanel, BorderLayout.EAST);

        // Add components to frame
        add(headerPanel, BorderLayout.NORTH);
        add(transactionPanel, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.SOUTH);

        // ViewModel listener setup
        viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case "portfolioData":
                    portfolioPanel.updateTableData((Object[][]) evt.getNewValue());
                    break;
                case "transactionHistory":
                    historyPanel.updateHistory((List<Map<String, Object>>) evt.getNewValue());
                    break;
                case "totalBalance":
                    balanceLabel.setText(String.format("Balance: $%.2f", (Double) evt.getNewValue()));
                    break;
                case "portfolioBalance":
                    portfolioBalanceLabel.setText(String.format("Portfolio: $%.2f", (Double) evt.getNewValue()));
                    break;
            }
        });

    }
}
