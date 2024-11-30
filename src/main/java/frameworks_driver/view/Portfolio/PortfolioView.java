package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;
import interface_adapter.portfolio.PortfolioViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.beans.PropertyChangeListener;

public class PortfolioView extends JFrame {
    private final PortfolioViewModel viewModel;
    private final JLabel balanceLabel;
    private final JLabel portfolioBalanceLabel;

    public PortfolioView(
            PortfolioViewModel viewModel,
            CompanyTextField companyTextField,
            QuantityTextField quantityTextField,
            BuyButton buyButton,
            SellButton sellButton,
//            RefreshButton refreshButton,
            PortfolioPanel portfolioPanel,
            HistoryPanel historyPanel
    ) {
        this.viewModel = viewModel;

        // Set up the frame
        setTitle("Stock Trading Portfolio");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Title and Home Button
        JLabel titleLabel = new JLabel("Portfolio", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);

        HomeButton homeButton = new HomeButton();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(homeButton, BorderLayout.EAST);

        // Balance Panel
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balanceLabel = new JLabel("Balance:");
        portfolioBalanceLabel = new JLabel("Portfolio:");
        balancePanel.add(balanceLabel);
        balancePanel.add(portfolioBalanceLabel);

        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(balancePanel, BorderLayout.SOUTH);

        // Transaction Input Panel
        JPanel transactionPanel = new JPanel(new FlowLayout());
        transactionPanel.add(new JLabel("Company:"));
        transactionPanel.add(companyTextField);
        transactionPanel.add(new JLabel("Quantity:"));
        transactionPanel.add(quantityTextField);
        transactionPanel.add(buyButton);
        transactionPanel.add(sellButton);
//        transactionPanel.add(refreshButton);


        // Main Content Area
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(portfolioPanel, BorderLayout.CENTER);
        mainContentPanel.add(historyPanel, BorderLayout.EAST);

        // Add Components to Frame
        add(headerPanel, BorderLayout.NORTH);
        add(transactionPanel, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.SOUTH);

        // Add ViewModel listeners
        viewModel.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
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