package frameworks_driver.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StockChartView extends JFrame {
    private final JCheckBox priceHistoryCheckbox;
    private final JCheckBox smaCheckbox;
    private final JCheckBox emaCheckbox;
    private final JCheckBox rsiCheckbox;
    private final JCheckBox fiveDay;
    private final JCheckBox monthly;
    private final JCheckBox yearly;

    private final JButton updateChartButton;

    public StockChartView(String ticker) {
        setTitle("Stock Chart for " + ticker);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create controls
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        priceHistoryCheckbox = new JCheckBox("Price History");
        smaCheckbox = new JCheckBox("Simple Moving Average (SMA)");
        emaCheckbox = new JCheckBox("Exponential Moving Average (EMA)");
        rsiCheckbox = new JCheckBox("Relative Strength Index (RSI)");
        fiveDay = new JCheckBox("5 DAY");
        monthly = new JCheckBox("Monthly Chart");
        yearly = new JCheckBox("Yearly Chart");

        updateChartButton = new JButton("Update Chart");

        // Add controls to panel
        controlPanel.add(priceHistoryCheckbox);
        controlPanel.add(smaCheckbox);
        controlPanel.add(emaCheckbox);
        controlPanel.add(rsiCheckbox);
        controlPanel.add(fiveDay);
        controlPanel.add(monthly);
        controlPanel.add(yearly);
        controlPanel.add(updateChartButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlPanel, BorderLayout.WEST);

        // Placeholder chart (real chart added by Controller/Presenter)
        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(600, 600));
        getContentPane().add(chartPanel, BorderLayout.CENTER);
    }

    // Expose user inputs to the Controller
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

    public boolean isFiveDaySelected() {
        return fiveDay.isSelected();
    }

    public boolean isMonthlySelected() {
        return monthly.isSelected();
    }

    public boolean isYearlySelected() {
        return yearly.isSelected();
    }

    public void setUpdateChartAction(ActionListener listener) {
        updateChartButton.addActionListener(listener);
    }
}