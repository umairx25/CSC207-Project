package frameworks_driver.view.chart;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TimeframeComboBox extends JPanel {

    private final JComboBox<String> timeframeComboBox;

    public TimeframeComboBox() {
        // Initialize the combo box with options
        timeframeComboBox = new JComboBox<>(new String[]{"5 Day", "Monthly", "Yearly"});
        timeframeComboBox.setSelectedIndex(0); // Default selection is "5 Day"

        // Add the combo box to the panel
        add(new JLabel("Timeframe:"));
        add(timeframeComboBox);
    }

    // Getter method to retrieve the selected timeframe
    public String getSelectedTimeframe() {
        return (String) timeframeComboBox.getSelectedItem();
    }

    // Add an ActionListener to respond to changes in the combo box
    public void addActionListener(ActionListener listener) {
        timeframeComboBox.addActionListener(listener);
    }
}