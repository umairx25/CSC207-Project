package frameworks_driver.view.chart;

import javax.swing.*;

public class ControlPanel extends JPanel {

    private final JCheckBox priceHistoryCheckbox;
    private final SmaCheckbox smaCheckbox;
    private final EmaCheckbox emaCheckbox;
    private final RsiCheckbox rsiCheckbox;
    private final TimeframeComboBox timeframeComboBox;

    public ControlPanel() {
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