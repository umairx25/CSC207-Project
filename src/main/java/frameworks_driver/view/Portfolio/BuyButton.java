package frameworks_driver.view.Portfolio;

import interface_adapter.portfolio.PortfolioController;
import javax.swing.*;
import java.awt.*;
import frameworks_driver.view.style_helpers.ColourManager;

public class BuyButton extends JButton {
    private final PortfolioController controller;
    private final CompanyTextField companyField;
    private final QuantityTextField quantityField;

    public BuyButton(PortfolioController controller, CompanyTextField companyField, QuantityTextField quantityField) {
        super("Buy");
        this.controller = controller;
        this.companyField = companyField;
        this.quantityField = quantityField;

//        setBackground(ColourManager.MEDIUM_GRAY);
        setPreferredSize(new Dimension(100, 30));
        addActionListener(e -> executeBuyOrder());
    }

    private void executeBuyOrder() {
        String company = companyField.getText();
        System.out.println(company);
        int quantity = (int) quantityField.getValue();
        System.out.println(quantity);
        controller.executeBuyOrder(company, quantity);
    }
}
