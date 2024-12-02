package frameworks_driver.view.home;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {

    public CenterPanel(PortfolioButton portfolioButton, ExploreButton exploreButton, ChatBotButton chatBotButton) {
        setLayout(new GridBagLayout());
        setOpaque(false); // Transparent for gradient background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 20, 20); // Spacing between buttons
        gbc.gridy = 0;

        gbc.gridx = 0;
        add(portfolioButton, gbc);

        gbc.gridx++;
        add(exploreButton, gbc);

        gbc.gridx++;
        add(chatBotButton, gbc);
    }
}
