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
import view.ColourManager;

import javax.swing.*;
import java.awt.*;
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
    private JFreeChart lineChart;
    private DefaultCategoryDataset dataset; // Shared dataset for all data series

    public ChartView(ChartViewModel chartViewModel, ChartController chartController, ChartState chartState) {

        this.chartViewModel = chartViewModel;
        this.chartController = chartController;
        this.chartState = chartState;

        setLayout(new BorderLayout());

        // Initialize panels and datasets
        dataset = new DefaultCategoryDataset();
        controlPanel = new ControlPanel();
        chartPanel = createChartPanel();

        // Add panels
        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(ColourManager.DARK_BLUE));

        // Initialize checkboxes with the current state
        updateCheckboxes(chartViewModel.getState());

        // Add item listeners to the checkboxes
        addCheckboxListeners();

        // Register as a property change listener for the ViewModel
        chartViewModel.addPropertyChangeListener(this);
    }

    private void addCheckboxListeners() {
        controlPanel.getPriceHistoryCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getSmaCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getEmaCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getRsiCheckbox().addItemListener(e -> updateChartData());
    }

    public void inputTicker(String ticker) {
        fetchChartData(ticker);
        lineChart.setTitle(ticker + "\n" + chartViewModel.getCurrPrice() + " USD" + "\n" + chartViewModel.getPointIncrease()
                + " (" + chartViewModel.getPercentIncrease() + "%)");
        updateChartData();
        revalidate();
        repaint();
    }

    private void updateChartData() {
        // Clear the dataset before updating
        dataset.clear();

        try {
            // Add data based on selected checkboxes
            if (controlPanel.getPriceHistoryCheckbox().isSelected()) {
                for (Map.Entry<Long, Double> entry : chartState.getPriceHistory().entrySet()) {
                    dataset.addValue(entry.getValue(), "Price History", entry.getKey());
                }
            }

            if (controlPanel.getSmaCheckbox().isSelected()) {
                for (Map.Entry<Long, Double> entry : chartState.getSma().entrySet()) {
                    dataset.addValue(entry.getValue(), "SMA", entry.getKey());
                }
            }

            if (controlPanel.getEmaCheckbox().isSelected()) {
                for (Map.Entry<Long, Double> entry : chartState.getEma().entrySet()) {
                    dataset.addValue(entry.getValue(), "EMA", entry.getKey());
                }
            }

            if (controlPanel.getRsiCheckbox().isSelected()) {
                for (Map.Entry<Long, Double> entry : chartState.getRsi().entrySet()) {
                    dataset.addValue(entry.getValue(), "RSI", entry.getKey());
                }
            }
        } catch (Exception e) {
            System.out.println("Indicator is null.");
            return;
        }

        // Refresh the chart
        revalidate();
        repaint();
    }

    private ChartPanel createChartPanel() {
        lineChart = ChartFactory.createLineChart(
                "",
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
        chartPanel.setPreferredSize(new Dimension(1000,770));
        return chartPanel;
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
        controlPanel.getSmaCheckbox().setSelected(state.isSmaSelected());
        controlPanel.getEmaCheckbox().setSelected(state.isEmaSelected());
        controlPanel.getRsiCheckbox().setSelected(state.isRsiSelected());
        controlPanel.getPriceHistoryCheckbox().setSelected(state.isPriceHistorySelected());
    }

    public String getViewName() {
        return this.viewName;
    }
}