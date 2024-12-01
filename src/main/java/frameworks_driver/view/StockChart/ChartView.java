package frameworks_driver.view.StockChart;

import data_access.StockDataAccess;
import interface_adapter.stockData.ChartController;
import interface_adapter.stockData.ChartPresenter;
import interface_adapter.stockData.ChartViewModel;
import use_case.stockChart.ChartDataAccessInterface;
import use_case.stockChart.ChartInputBoundary;
import use_case.stockChart.ChartInteractor;
import use_case.stockChart.ChartOutputBoundary;

import javax.swing.*;
import java.awt.*;

public class ChartView extends JFrame {

    private final ControlPanel controlPanel;
    private final ChartPanelView chartPanel;
    private final ChartController controller;
    private final ChartViewModel viewModel;

    public ChartView(String ticker) {
        setTitle("Stock Price Chart for " + ticker);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Backend setup following Clean Architecture
        ChartDataAccessInterface dataAccess = new StockDataAccess(); // Data Access Layer
        viewModel = new ChartViewModel(); // Holds state for the view
        ChartOutputBoundary presenter = new ChartPresenter(viewModel); // Presenter formats the output
        ChartInputBoundary interactor = new ChartInteractor(presenter, dataAccess); // Interactor handles use case logic
        controller = new ChartController(interactor); // Controller connects UI to backend

        // Frontend setup
        controlPanel = new ControlPanel(e -> handleUserAction(ticker)); // Handles user input
        chartPanel = new ChartPanelView("Stock Data: " + ticker); // Chart rendering panel

        // Add components to the JFrame
        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Handles user interactions and fetches chart data through the controller.
     */
    private void handleUserAction(String ticker) {
        try {
            // Map the timeframe to API-compatible values
            String rawTimeframe = controlPanel.getSelectedTimeframe();
            String timeframe = mapTimeframe(rawTimeframe);

            boolean includePriceHistory = controlPanel.isPriceHistorySelected();
            boolean includeSma = controlPanel.isSmaSelected();
            boolean includeEma = controlPanel.isEmaSelected();
            boolean includeRsi = controlPanel.isRsiSelected();

            // Fetch data via the controller
            controller.fetchChartData(ticker, timeframe, includePriceHistory, includeSma, includeEma, includeRsi);

            // Update the chart panel
            chartPanel.updateChart(viewModel.getDataset());

            // Display errors if any
            if (viewModel.getErrorMessage() != null) {
                JOptionPane.showMessageDialog(this, viewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String mapTimeframe(String timeframe) {
        switch (timeframe) {
            case "5 Day": return "day";
            case "Monthly": return "month";
            case "Yearly": return "year";
            default: throw new IllegalArgumentException("Invalid timeframe: " + timeframe);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartView view = new ChartView("AAPL"); // Example stock ticker
            view.setVisible(true);
        });
    }
}
