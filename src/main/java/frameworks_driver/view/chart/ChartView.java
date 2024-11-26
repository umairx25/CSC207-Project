//package frameworks_driver.view.chart;
//
//import interface_adapter.chart.ChartController;
//import interface_adapter.chart.ChartState;
//import interface_adapter.chart.ChartViewModel;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.axis.NumberTickUnit;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//import java.time.LocalDate;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ItemEvent;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.util.Map;
//
//public class ChartView extends JPanel implements PropertyChangeListener {
//
//    private final String viewName = "chart view";
//
//    private final ControlPanel controlPanel;
//    private final ChartPanelView chartPanel;
//    private final ChartViewModel chartViewModel;
//    private final ChartController chartController;
//    private final ChartState chartState;
//
//    private final JCheckBox smaCheckBox = new JCheckBox("SMA");
//    private final JCheckBox emaCheckBox = new JCheckBox("EMA");
//    private final JCheckBox rsiCheckBox = new JCheckBox("RSI");
//    private final JCheckBox priceHistoryCheckBox = new JCheckBox("Price History");
//
//    public ChartView(ChartViewModel chartViewModel, ChartController chartController, ChartState chartState)
//            throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        this.chartViewModel = chartViewModel;
//        this.chartController = chartController;
//        this.chartState = chartState;
//
//        String ticker = "AAPL"; // Example ticker
//        setSize(800, 600);
//        setLayout(new BorderLayout());
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//
//        // Initialize UI components
//        controlPanel = new ControlPanel();
//        chartPanel = new ChartPanelView("Stock Data: " + ticker);
//
//        add(controlPanel, BorderLayout.WEST);
////        add(chartPanel, BorderLayout.CENTER);
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        add(createChart(dataset), BorderLayout.CENTER);
//
//        // Add checkboxes to the control panel
//        controlPanel.add(smaCheckBox);
//        controlPanel.add(emaCheckBox);
//        controlPanel.add(rsiCheckBox);
//        controlPanel.add(priceHistoryCheckBox);
//
//        // Initialize checkboxes with the current state
//        updateCheckboxes(chartViewModel.getState());
//
//        // Add item listeners to the checkboxes
//        addCheckboxListeners();
//
//        // Register as a property change listener for the ViewModel
//        chartViewModel.addPropertyChangeListener(this);
//
//        // Fetch initial chart data
//        fetchChartData(ticker);
//
//    }
//
//    private void addCheckboxListeners() {
//        smaCheckBox.addItemListener(e -> presentData("SMA", e));
//        emaCheckBox.addItemListener(e -> presentData("EMA", e));
//        rsiCheckBox.addItemListener(e -> presentData("RSI", e));
//        priceHistoryCheckBox.addItemListener(e -> presentData("Price History", e));
//    }
////
////    private void handleCheckboxStateChange(String checkboxName, ItemEvent e) {
////        boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
////        System.out.println("View: " + checkboxName + " checkbox state changed to " + isSelected);
////
////        // Trigger a new chart data fetch based on updated state
////        try {
////            ChartState currentState = chartViewModel.getState();
////            chartController.handleChartRequest(
////                    "AAPL",
////                    "week",
////                    LocalDate.now().minusYears(5).toString(),
////                    LocalDate.now().toString(),
////                    priceHistoryCheckBox.isSelected(),
////                    smaCheckBox.isSelected(),
////                    emaCheckBox.isSelected(),
////                    rsiCheckBox.isSelected()
////            );
////        } catch (Exception ex) {
////            System.out.println("View: " + ex.getMessage());
////        }
////    }
//
//    private DefaultCategoryDataset presentData(String checkboxName, ItemEvent e) {
//        boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        if (checkboxName.equals("Price History") && isSelected) {
//            for (Map.Entry<Long, Double> entry : chartState.getPriceHistory().entrySet()) {
//                System.out.println(entry);
//                dataset.addValue(entry.getValue(), "Price History", entry.getKey());
//            }
//        }
//
//        if (checkboxName.equals("SMA") && isSelected) {
//            for (Map.Entry<Long, Double> entry : chartState.getSma().entrySet()) {
//                dataset.addValue(entry.getValue(), "SMA", entry.getKey());
//            }
//        }
//
//        if (checkboxName.equals("EMA") && isSelected) {
//            for (Map.Entry<Long, Double> entry : chartState.getEma().entrySet()) {
//                dataset.addValue(entry.getValue(), "EMA", entry.getKey());
//            }
//        }
//
//        if (checkboxName.equals("RSI") && isSelected) {
//            for (Map.Entry<Long, Double> entry : chartState.getRsi().entrySet()) {
//                dataset.addValue(entry.getValue(), "RSI", entry.getKey());
//            }
//        }
//
//        return dataset;
//    }
//
//    public ChartPanel createChart(DefaultCategoryDataset dataset) {
//        JFreeChart lineChart = ChartFactory.createLineChart(
//                "Apple Inc.",
//                "Date",
//                "Price",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false
//        );
//
//        CategoryPlot plot = lineChart.getCategoryPlot();
//        NumberAxis rangeAxis = new NumberAxis("Price");
//        plot.setRangeAxis(rangeAxis);
//        rangeAxis.setAutoRangeIncludesZero(false);
//        rangeAxis.setTickUnit(new NumberTickUnit(20));
//
//        ChartPanel chartPanel = new ChartPanel(lineChart);
//
//        chartController.handleChartRequest("AAPL", "week", java.time.LocalDate.now().minusYears(5).toString(),
//                java.time.LocalDate.now().toString(), smaCheckBox.isSelected(),emaCheckBox.isSelected(),
//                rsiCheckBox.isSelected(), priceHistoryCheckBox.isSelected());
//
//        return chartPanel;
//    }
//
//
//    private void fetchChartData(String ticker) {
//        ChartState initialState = chartViewModel.getState();
//        String endDate = LocalDate.now().toString();
//        String startDate = LocalDate.now().minusYears(5).toString();
//
//        chartController.handleChartRequest(
//                ticker,
//                "week",
//                startDate,
//                endDate,
//                initialState.isPriceHistorySelected(),
//                initialState.isSmaSelected(),
//                initialState.isEmaSelected(),
//                initialState.isRsiSelected()
//        );
//    }
//
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        if ("state".equals(evt.getPropertyName())) {
//            ChartState newState = (ChartState) evt.getNewValue();
//            updateCheckboxes(newState);
//        }
//    }
//
//    private void updateCheckboxes(ChartState state) {
//        smaCheckBox.setSelected(state.isSmaSelected());
//        emaCheckBox.setSelected(state.isEmaSelected());
//        rsiCheckBox.setSelected(state.isRsiSelected());
//        priceHistoryCheckBox.setSelected(state.isPriceHistorySelected());
//    }
//
//    public String getViewName() {
//        System.out.println(viewName);
//        return this.viewName;
//    }
//}

package frameworks_driver.view.chart;

import interface_adapter.chart.ChartController;
import interface_adapter.chart.ChartState;
import interface_adapter.chart.ChartViewModel;
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
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Map;

public class ChartView extends JPanel implements PropertyChangeListener {

    private final String viewName = "chart view";

    private final ControlPanel controlPanel;
    private final ChartPanel chartPanel;
    private final ChartViewModel chartViewModel;
    private final ChartController chartController;
    private final ChartState chartState;

    private final JCheckBox smaCheckBox = new JCheckBox("SMA");
    private final JCheckBox emaCheckBox = new JCheckBox("EMA");
    private final JCheckBox rsiCheckBox = new JCheckBox("RSI");
    private final JCheckBox priceHistoryCheckBox = new JCheckBox("Price History");

    private DefaultCategoryDataset dataset; // Shared dataset for all data series

    public ChartView(ChartViewModel chartViewModel, ChartController chartController, ChartState chartState)
            throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.chartViewModel = chartViewModel;
        this.chartController = chartController;
        this.chartState = chartState;

        String ticker = "AAPL"; // Example ticker
        setSize(800, 600);
        setLayout(new BorderLayout());
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        // Initialize shared dataset and chart
        dataset = new DefaultCategoryDataset();
        chartPanel = createChartPanel();

        // Initialize UI components
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);

        // Add checkboxes to the control panel
        controlPanel.add(smaCheckBox);
        controlPanel.add(emaCheckBox);
        controlPanel.add(rsiCheckBox);
        controlPanel.add(priceHistoryCheckBox);

        // Initialize checkboxes with the current state
        updateCheckboxes(chartViewModel.getState());

        // Add item listeners to the checkboxes
        addCheckboxListeners();

        // Register as a property change listener for the ViewModel
        chartViewModel.addPropertyChangeListener(this);

        // Fetch initial chart data
        fetchChartData(ticker);
    }

    private void addCheckboxListeners() {
        smaCheckBox.addItemListener(e -> updateChartData());
        emaCheckBox.addItemListener(e -> updateChartData());
        rsiCheckBox.addItemListener(e -> updateChartData());
        priceHistoryCheckBox.addItemListener(e -> updateChartData());
    }

    private void updateChartData() {
        // Clear the dataset before updating
        dataset.clear();

        // Add data based on selected checkboxes
        if (priceHistoryCheckBox.isSelected()) {
            for (Map.Entry<Long, Double> entry : chartState.getPriceHistory().entrySet()) {
                dataset.addValue(entry.getValue(), "Price History", entry.getKey());
            }
        }

        if (smaCheckBox.isSelected()) {
            for (Map.Entry<Long, Double> entry : chartState.getSma().entrySet()) {
                dataset.addValue(entry.getValue(), "SMA", entry.getKey());
            }
        }

        if (emaCheckBox.isSelected()) {
            for (Map.Entry<Long, Double> entry : chartState.getEma().entrySet()) {
                dataset.addValue(entry.getValue(), "EMA", entry.getKey());
            }
        }

        if (rsiCheckBox.isSelected()) {
            for (Map.Entry<Long, Double> entry : chartState.getRsi().entrySet()) {
                dataset.addValue(entry.getValue(), "RSI", entry.getKey());
            }
        }

        // Refresh the chart
        revalidate();
        repaint();
    }

    private ChartPanel createChartPanel() {
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Apple Inc.",
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

        return new ChartPanel(lineChart);
    }

    private void fetchChartData(String ticker) {
        ChartState initialState = chartViewModel.getState();
        String endDate = LocalDate.now().toString();
        String startDate = LocalDate.now().minusYears(5).toString();

        chartController.handleChartRequest(
                ticker,
                "week",
                startDate,
                endDate,
                initialState.isPriceHistorySelected(),
                initialState.isSmaSelected(),
                initialState.isEmaSelected(),
                initialState.isRsiSelected()
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ChartState newState = (ChartState) evt.getNewValue();
            updateCheckboxes(newState);
        }
    }

    private void updateCheckboxes(ChartState state) {
        smaCheckBox.setSelected(state.isSmaSelected());
        emaCheckBox.setSelected(state.isEmaSelected());
        rsiCheckBox.setSelected(state.isRsiSelected());
        priceHistoryCheckBox.setSelected(state.isPriceHistorySelected());
    }

    public String getViewName() {
        System.out.println(viewName);
        return this.viewName;
    }
}