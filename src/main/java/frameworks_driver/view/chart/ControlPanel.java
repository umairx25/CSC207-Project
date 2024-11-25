package frameworks_driver.view.chart;
import interface_adapter.chart.ChartState;
import interface_adapter.chart.ChartViewModel;

import javax.swing.*;

public class ControlPanel extends JPanel {

    private ChartViewModel chartViewModel;

    public JCheckBox getPriceHistoryCheckbox() {
        return priceHistoryCheckbox;
    }

    private final JCheckBox priceHistoryCheckbox;

    public SmaCheckbox getSmaCheckbox() {
        return smaCheckbox;
    }

    private final SmaCheckbox smaCheckbox;

    public EmaCheckbox getEmaCheckbox() {
        return emaCheckbox;
    }

    private final EmaCheckbox emaCheckbox;

    public RsiCheckbox getRsiCheckbox() {
        return rsiCheckbox;
    }

    private final RsiCheckbox rsiCheckbox;

    public TimeframeComboBox getTimeframeComboBox() {
        return timeframeComboBox;
    }

    private final TimeframeComboBox timeframeComboBox;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize checkboxes
        priceHistoryCheckbox = new JCheckBox("Price History");
        smaCheckbox = new SmaCheckbox();
        emaCheckbox = new EmaCheckbox();
        rsiCheckbox = new RsiCheckbox();
        timeframeComboBox = new TimeframeComboBox();

        setEMAListener(emaCheckbox);
        setSMAListener(smaCheckbox);
        setRSIListener(rsiCheckbox);
        setTimeframeComboBox(timeframeComboBox);

        // Add components to the panel
        add(priceHistoryCheckbox);
        add(smaCheckbox);
        add(emaCheckbox);
        add(rsiCheckbox);
        add(timeframeComboBox);
    }

    private void setEMAListener(JCheckBox checkBox){
        checkBox.addItemListener(e -> {
            final ChartState currentState = chartViewModel.getState();
            currentState.setEmaselected(checkBox.isSelected());
            chartViewModel.setState(currentState);
        });
    }

    private void setSMAListener(JCheckBox checkBox){
        checkBox.addItemListener(e -> {
            final ChartState currentState = chartViewModel.getState();
            currentState.setEmaselected(checkBox.isSelected());
            chartViewModel.setState(currentState);
        });
    }

    private void setRSIListener(JCheckBox checkBox){
        checkBox.addItemListener(e -> {
            final ChartState currentState = chartViewModel.getState();
            currentState.setEmaselected(checkBox.isSelected());
            chartViewModel.setState(currentState);
        });
    }

    private void setTimeframeComboBox(TimeframeComboBox timeframe) {
        timeframe.addActionListener(e -> {
            final ChartState currentState = chartViewModel.getState();
            currentState.setTimePeriod(getSelectedTimeframe());
            chartViewModel.setState(currentState);
        });
    }

    // Getter for selected timeframe
    public String getSelectedTimeframe() {
        return timeframeComboBox.getSelectedTimeframe();
    }
}