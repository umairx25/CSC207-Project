package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;
import javax.swing.*;
import java.awt.*;
import frameworks_driver.view.style_helpers.ColourManager;


public class SellButton extends JButton {
    private final PortfolioController controller;
    private final CompanyTextField companyField;
    private final QuantityTextField quantityField;

    public SellButton(PortfolioController controller, CompanyTextField companyField, QuantityTextField quantityField) {
        super("Sell");
        this.controller = controller;
        this.companyField = companyField;
        this.quantityField = quantityField;

        setBackground(ColourManager.MEDIUM_GRAY);
        setPreferredSize(new Dimension(100, 30));
        addActionListener(e -> executeSellOrder());
    }

    private void executeSellOrder() {
        String company = companyField.getText();
        int quantity = (int) quantityField.getValue();
        controller.executeSellOrder(company, quantity);
    }
}
