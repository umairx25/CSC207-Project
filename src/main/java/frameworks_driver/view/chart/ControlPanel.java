package frameworks_driver.view.chart;

import javax.swing.*;

public class ControlPanel extends JPanel {

    // Checkboxes
    private final JCheckBox priceHistoryCheckbox;
    private final SmaCheckbox smaCheckbox;
    private final EmaCheckbox emaCheckbox;
    private final RsiCheckbox rsiCheckbox;


    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize checkboxes
        priceHistoryCheckbox = new PriceCheckbox();
        smaCheckbox = new SmaCheckbox();
        emaCheckbox = new EmaCheckbox();
        rsiCheckbox = new RsiCheckbox();

        // Add components to the panel
        add(priceHistoryCheckbox);
        add(smaCheckbox);
        add(emaCheckbox);
        add(rsiCheckbox);
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

}