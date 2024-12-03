package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import frameworks_driver.view.style_helpers.ColourManager;


public class RefreshButton extends JButton {
    public RefreshButton(PortfolioController portfolioController) {
        super("Refresh Prices");

        setBackground(ColourManager.MEDIUM_GRAY);
        setPreferredSize(new Dimension(200, 30));

        // Add action listener to trigger portfolio refresh
        addActionListener(e -> portfolioController.refreshPortfolioData());
    }
}