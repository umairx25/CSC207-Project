package frameworks_driver;

import data_access.StockHelper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockChart extends JFrame {

    private final JCheckBox priceHistoryCheckbox;
    private final JCheckBox smaCheckbox;
    private final JCheckBox emaCheckbox;
    private final JCheckBox rsiCheckbox;
    private final JCheckBox fiveDay;
    private final JCheckBox monthly;
    private final JCheckBox yearly;

    private final String ticker;
    private final LinkedHashMap<Long, Double> priceHistory;
    private final LinkedHashMap<Long, Double> smaData;
    private final LinkedHashMap<Long, Double> emaData;
    private final LinkedHashMap<Long, Double> rsiData;

    public StockChart(JCheckBox fiveDay, JCheckBox monthly, JCheckBox yearly, String ticker, LinkedHashMap<Long, Double> priceHistory, LinkedHashMap<Long, Double> smaData,
                      LinkedHashMap<Long, Double> emaData, LinkedHashMap<Long, Double> rsiData) throws Exception {
        this.fiveDay = fiveDay;
        this.monthly = monthly;
        this.yearly = yearly;
        this.ticker = ticker;
        this.priceHistory = priceHistory;
        this.smaData = smaData;
        this.emaData = emaData;
        this.rsiData = rsiData;

        setTitle("Stock Price Chart for " + ticker);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Double currPrice = StockHelper.currPrice(ticker);
        ArrayList<Double> increase = StockHelper.increase(ticker);

        String value = increase.get(1).toString();
        String percent = increase.get(0).toString();
        if (increase.get(0) > 0) {
            value = "+" + value;
            percent = "+" + percent;
        }

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        priceHistoryCheckbox = new JCheckBox("Price History");
        smaCheckbox = new JCheckBox("Simple Moving Average (SMA)");
        emaCheckbox = new JCheckBox("Exponential Moving Average (EMA)");
        rsiCheckbox = new JCheckBox("Relative Strength Index (RSI)");

        controlPanel.add(priceHistoryCheckbox);
        controlPanel.add(smaCheckbox);
        controlPanel.add(emaCheckbox);
        controlPanel.add(rsiCheckbox);
        controlPanel.add(fiveDay);
        controlPanel.add(monthly);
        controlPanel.add(yearly);

        ActionListener checkboxListener = e -> updateChart();

        priceHistoryCheckbox.addActionListener(checkboxListener);
        smaCheckbox.addActionListener(checkboxListener);
        emaCheckbox.addActionListener(checkboxListener);
        rsiCheckbox.addActionListener(checkboxListener);
        fiveDay.addActionListener(checkboxListener);
        monthly.addActionListener(checkboxListener);
        yearly.addActionListener(checkboxListener);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart lineChart = ChartFactory.createLineChart(
                StockHelper.getTickerName(ticker) + " (" + ticker + ") " + "\n" + currPrice + "\n" + value + " (" + percent + "%)",
                "Date",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        CategoryPlot plot = lineChart.getCategoryPlot();
        NumberAxis rangeAxis = new NumberAxis("Price");
        plot.setRangeAxis(rangeAxis);
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickUnit(new NumberTickUnit(20));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlPanel, BorderLayout.WEST);
        getContentPane().add(chartPanel, BorderLayout.CENTER);

        updateChart();
    }

    private void updateChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (priceHistoryCheckbox.isSelected()) {
            for (Map.Entry<Long, Double> entry : priceHistory.entrySet()) {
                dataset.addValue(entry.getValue(), "Price History", entry.getKey());
            }
        }
        if (smaCheckbox.isSelected()) {
            for (Map.Entry<Long, Double> entry : smaData.entrySet()) {
                dataset.addValue(entry.getValue(), "SMA", entry.getKey());
            }
        }
        if (emaCheckbox.isSelected()) {
            for (Map.Entry<Long, Double> entry : emaData.entrySet()) {
                dataset.addValue(entry.getValue(), "EMA", entry.getKey());
            }
        }
        if (rsiCheckbox.isSelected()) {
            for (Map.Entry<Long, Double> entry : rsiData.entrySet()) {
                dataset.addValue(entry.getValue(), "RSI", entry.getKey());
            }
        }

        ((CategoryPlot) ((ChartPanel) getContentPane().getComponent(1)).getChart().getPlot()).setDataset(dataset);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                LinkedHashMap<Long, Double> priceHistory = StockHelper.getHistoricalData("AAPL", "month","2020-01-01", "2024-03-03");
                LinkedHashMap<Long, Double> smaData = StockHelper.getSMAData("AAPL", "month", "2020-01-01", "2024-03-03", 10);
                LinkedHashMap<Long, Double> emaData = StockHelper.getEMAData("AAPL", "month", "2020-01-01", "2024-03-03", 10);
                LinkedHashMap<Long, Double> rsiData = StockHelper.getRSIData("AAPL", "month", "2020-01-01", "2024-03-03", 10);

                JCheckBox fiveDay = new JCheckBox("5 DAY");
                JCheckBox monthly = new JCheckBox("Monthly Chart");
                JCheckBox yearly = new JCheckBox("Yearly Chart");

                StockChart chart = new StockChart(fiveDay, monthly, yearly, "AAPL", priceHistory, smaData, emaData, rsiData);
                chart.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}