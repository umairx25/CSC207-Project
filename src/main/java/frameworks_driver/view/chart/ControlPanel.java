package frameworks_driver.view.chart;

import frameworks_driver.view.style_helpers.ColourManager;
import interface_adapter.chart.ChartViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the control panel for the chart view, allowing users to select
 * different technical indicators (Price History, SMA, EMA, RSI) to display on the chart.
 */
public class ControlPanel extends JPanel {

    // Checkboxes for different chart options
    private final JCheckBox priceHistoryCheckbox;
    private final SmaCheckbox smaCheckbox;
    private final EmaCheckbox emaCheckbox;
    private final RsiCheckbox rsiCheckbox;

    /**
     * Constructs a new ControlPanel object and initializes its components.
     */
    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize checkboxes
        priceHistoryCheckbox = new PriceCheckbox();
        smaCheckbox = new SmaCheckbox();
        emaCheckbox = new EmaCheckbox();
        rsiCheckbox = new RsiCheckbox();
        JLabel historyLabel = new JLabel("<html><div style='padding-top: 10px; padding-left: 5px;'>"+
                ChartViewModel.CONTROL_PANEL_MESSAGE +"</div></html>");

        // Set background color for checkboxes
        priceHistoryCheckbox.setBackground(ColourManager.INNER_BOX_BLUE);
        smaCheckbox.setBackground(ColourManager.INNER_BOX_BLUE);
        emaCheckbox.setBackground(ColourManager.INNER_BOX_BLUE);
        rsiCheckbox.setBackground(ColourManager.INNER_BOX_BLUE);
        historyLabel.setBackground(ColourManager.INNER_BOX_BLUE);

        // Add components to the panel
        add(priceHistoryCheckbox);
        add(smaCheckbox);
        add(emaCheckbox);
        add(rsiCheckbox);
        add(historyLabel);
    }

    /**
     * @return the checkbox for Price History
     */
    public JCheckBox getPriceHistoryCheckbox() {
        return priceHistoryCheckbox;
    }

    /**
     * @return the checkbox for SMA (Simple Moving Average)
     */
    public SmaCheckbox getSmaCheckbox() {
        return smaCheckbox;
    }

    /**
     * @return the checkbox for EMA (Exponential Moving Average)
     */
    public EmaCheckbox getEmaCheckbox() {
        return emaCheckbox;
    }

    /**
     * @return the checkbox for RSI (Relative Strength Index)
     */
    public RsiCheckbox getRsiCheckbox() {
        return rsiCheckbox;
    }
}
