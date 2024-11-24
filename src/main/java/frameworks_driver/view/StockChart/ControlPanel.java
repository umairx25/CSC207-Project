package frameworks_driver.view.StockChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private final JCheckBox priceHistoryCheckbox;
    private final SmaCheckbox smaCheckbox;
    private final EmaCheckbox emaCheckbox;
    private final RsiCheckbox rsiCheckbox;
    private final TimeframeComboBox timeframeComboBox;

    public ControlPanel(ActionListener actionListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize checkboxes
        priceHistoryCheckbox = new JCheckBox("Price History");
        smaCheckbox = new SmaCheckbox();
        emaCheckbox = new EmaCheckbox();
        rsiCheckbox = new RsiCheckbox();

        // Initialize the TimeframeComboBox
        timeframeComboBox = new TimeframeComboBox();

        // Add components to the panel
        add(priceHistoryCheckbox);
        add(smaCheckbox);
        add(emaCheckbox);
        add(rsiCheckbox);
        add(timeframeComboBox);

        // Attach listeners
        priceHistoryCheckbox.addActionListener(actionListener);
        smaCheckbox.addActionListener(actionListener);
        emaCheckbox.addActionListener(actionListener);
        rsiCheckbox.addActionListener(actionListener);
        timeframeComboBox.addActionListener(actionListener);
    }

    // Getter methods for checkboxes
    public boolean isPriceHistorySelected() {
        return priceHistoryCheckbox.isSelected();
    }

    public boolean isSmaSelected() {
        return smaCheckbox.isSelected();
    }

    public boolean isEmaSelected() {
        return emaCheckbox.isSelected();
    }

    public boolean isRsiSelected() {
        return rsiCheckbox.isSelected();
    }

    // Getter for selected timeframe
    public String getSelectedTimeframe() {
        return timeframeComboBox.getSelectedTimeframe();
    }
}
