package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RefreshButton extends JButton {
    public RefreshButton(PortfolioController portfolioController) {
        super("Refresh Prices");

        // Add action listener to trigger portfolio refresh
        addActionListener(e -> portfolioController.refreshPortfolioData());
    }
}