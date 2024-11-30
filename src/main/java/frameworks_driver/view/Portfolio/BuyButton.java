package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;
import javax.swing.*;
import java.awt.*;

public class BuyButton extends JButton {
    private final PortfolioController controller;
    private final CompanyTextField companyField;
    private final QuantityTextField quantityField;

    public BuyButton(PortfolioController controller, CompanyTextField companyField, QuantityTextField quantityField) {
        super("Buy");
        this.controller = controller;
        this.companyField = companyField;
        this.quantityField = quantityField;

        setBackground(new Color(46, 204, 113));
        setForeground(Color.BLACK);
        setPreferredSize(new Dimension(100, 30));
        addActionListener(e -> executeBuyOrder());
    }

    private void executeBuyOrder() {
        String company = companyField.getText();
        int quantity = (int) quantityField.getValue();
        controller.executeBuyOrder(company, quantity);
    }
}
