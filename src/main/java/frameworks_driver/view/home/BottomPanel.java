package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the bottom panel of the home view, displaying the portfolio balance.
 */
public class BottomPanel extends JPanel {

    /**
     * Constructs a new BottomPanel with the user's portfolio balance.
     *
     * @param portfolioBalance the user's current portfolio balance
     */
    public BottomPanel(double portfolioBalance) {
        setOpaque(false); // Transparent for gradient background

        JLabel portfolioBalanceLabel = new JLabel("Portfolio Balance: $" + String.format("%.2f", portfolioBalance));
        portfolioBalanceLabel.setFont(new Font("Segue UI", Font.BOLD, 16));
        portfolioBalanceLabel.setForeground(Color.WHITE);

        add(portfolioBalanceLabel);
    }
}
