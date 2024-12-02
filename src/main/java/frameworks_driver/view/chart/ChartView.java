package frameworks_driver.view.chart;

import frameworks_driver.view.style_helpers.ColourManager;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Map;

/**
 * Represents the graphical user interface for displaying stock charts.
 * Allows users to view different technical indicators (Price History, SMA, EMA, RSI)
 * and interact with checkboxes to customize the chart view.
 */
public class ChartView extends JPanel implements PropertyChangeListener {

    private final ControlPanel controlPanel;
    private final ChartViewModel chartViewModel;
    private final ChartController chartController;
    private final ChartState chartState;
    private JFreeChart lineChart;
    private final DefaultCategoryDataset dataset;

    /**
     * Constructs a new ChartView object.
     *
     * @param chartViewModel  the view model containing chart data and state
     * @param chartController the controller responsible for handling chart-related requests
     * @param chartState      the state object representing the chart's current configuration
     */
    public ChartView(ChartViewModel chartViewModel, ChartController chartController, ChartState chartState) {
        this.chartViewModel = chartViewModel;
        this.chartController = chartController;
        this.chartState = chartState;

        setLayout(new BorderLayout());

        dataset = new DefaultCategoryDataset();
        controlPanel = new ControlPanel();
        controlPanel.setBackground(ColourManager.INNER_BOX_BLUE);
        ChartPanel chartPanel = createChartPanel();

        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);

        // Initialize checkboxes with the current state
        updateCheckboxes(chartViewModel.getState());

        // Add listeners to checkboxes
        addCheckboxListeners();

        // Register as a property change listener for the ViewModel
        chartViewModel.addPropertyChangeListener(this);
    }

    /**
     * Adds listeners to the checkboxes for updating chart data.
     */
    private void addCheckboxListeners() {
        controlPanel.getPriceHistoryCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getSmaCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getEmaCheckbox().addItemListener(e -> updateChartData());
        controlPanel.getRsiCheckbox().addItemListener(e -> updateChartData());
    }

    /**
     * Fetches and displays chart data for the given stock ticker.
     *
     * @param ticker the stock ticker symbol
     */
    public void inputTicker(String ticker) {
        fetchChartData(ticker);
        lineChart.setTitle(ticker + "\n" + chartViewModel.getCurrPrice() + " USD" + "\n" +
                chartViewModel.getPointIncrease() + " (" + chartViewModel.getPercentIncrease() + "%)");
        updateChartData();
        revalidate();
        repaint();
    }

    /**
     * Updates the chart data based on the selected checkboxes.
     */
    private void updateChartData() {
        dataset.clear();

        try {
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
        }

        revalidate();
        repaint();
    }

    /**
     * Creates and configures the chart panel for displaying the stock chart.
     *
     * @return a configured ChartPanel object
     */
    private ChartPanel createChartPanel() {
        lineChart = ChartFactory.createLineChart(
                "",
                "Date",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );
        lineChart.setBackgroundPaint(ColourManager.INNER_BOX_BLUE);

        CategoryPlot plot = lineChart.getCategoryPlot();
        NumberAxis rangeAxis = new NumberAxis("Price");
        plot.setRangeAxis(rangeAxis);
        plot.setBackgroundPaint(ColourManager.NAVY_BLUE);

        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickUnit(new NumberTickUnit(20));

        ChartPanel chartPanel = new ChartPanel(lineChart) {
            @Override
            public Dimension getPreferredSize() {
                return getParent() != null ? getParent().getSize() : new Dimension(800, 600);
            }
        };

        chartPanel.setPreferredSize(null);
        chartPanel.setMinimumSize(new Dimension(200, 200));
        chartPanel.setMaximumDrawWidth(Integer.MAX_VALUE);
        chartPanel.setMaximumDrawHeight(Integer.MAX_VALUE);

        return chartPanel;
    }




    /**
     * Fetches chart data for the given stock ticker using the ChartController.
     *
     * @param ticker the stock ticker symbol
     */
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

    /**
     * Handles property changes and updates the checkboxes to reflect the new chart state.
     *
     * @param evt the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ChartState newState = (ChartState) evt.getNewValue();
            updateCheckboxes(newState);
        }
    }

    /**
     * Updates the checkboxes in the control panel based on the given chart state.
     *
     * @param state the new ChartState object
     */
    private void updateCheckboxes(ChartState state) {
        controlPanel.getSmaCheckbox().setSelected(state.isSmaSelected());
        controlPanel.getEmaCheckbox().setSelected(state.isEmaSelected());
        controlPanel.getRsiCheckbox().setSelected(state.isRsiSelected());
        controlPanel.getPriceHistoryCheckbox().setSelected(state.isPriceHistorySelected());
    }

    /**
     * @return the name of the view
     */
    public String getViewName() {
        return "chart view";
    }
}
