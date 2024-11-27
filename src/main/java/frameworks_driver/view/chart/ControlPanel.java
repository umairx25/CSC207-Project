package frameworks_driver.view.chart;

import interface_adapter.chart.ChartViewModel;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {


    // Checkboxes
    private final JCheckBox priceHistoryCheckbox;
    private final SmaCheckbox smaCheckbox;
    private final EmaCheckbox emaCheckbox;
    private final RsiCheckbox rsiCheckbox;

    // Text field and button
    private final TickerTextField tickerTextField;
    private final ConfirmTickerbutton confirmTickerbutton;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize checkboxes
        priceHistoryCheckbox = new PriceCheckbox();
        smaCheckbox = new SmaCheckbox();
        emaCheckbox = new EmaCheckbox();
        rsiCheckbox = new RsiCheckbox();

        // Initialize text field and button
        tickerTextField = new TickerTextField();
        tickerTextField.setMaximumSize(new Dimension(1000, 40));
        confirmTickerbutton = new ConfirmTickerbutton();

        // Add components to the panel
        add(priceHistoryCheckbox);
        add(smaCheckbox);
        add(emaCheckbox);
        add(rsiCheckbox);
        add(tickerTextField);
        add(confirmTickerbutton);
    }

    // Getter methods for checkboxes

    public JCheckBox getPriceHistoryCheckbox() {
        return priceHistoryCheckbox;
    }
    public SmaCheckbox getSmaCheckbox() {
        return smaCheckbox;
    }

    public EmaCheckbox getEmaCheckbox() {
        return emaCheckbox;
    }

    public RsiCheckbox getRsiCheckbox() {
        return rsiCheckbox;
    }

    public TickerTextField getTickerTextField() {
        return tickerTextField;
    }

    public void setTickerTextField(String s) {
        this.tickerTextField.setText(s);
    }

    public ConfirmTickerbutton getConfirmTickerbutton() {
        return confirmTickerbutton;
    }
}