package frameworks_driver.view.portfolio;

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
