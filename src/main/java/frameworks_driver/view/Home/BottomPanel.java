package frameworks_driver.view.Home;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    public BottomPanel(double portfolioBalance) {
        setOpaque(false); // Transparent for gradient background

        JLabel portfolioBalanceLabel = new JLabel("Portfolio Balance: $" + String.format("%.2f", portfolioBalance));
        portfolioBalanceLabel.setFont(new Font("Segue UI", Font.BOLD, 16));
        portfolioBalanceLabel.setForeground(Color.WHITE);

        add(portfolioBalanceLabel);
    }
}
