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

        // Set up Clean Architecture components
        ChartDataAccessInterface dataAccess = new StockDataAccess(); // Data Access Implementation
        viewModel = new ChartViewModel(); // Holds state of the view
        ChartOutputBoundary presenter = new ChartPresenter(viewModel); // Presenter handles formatting
        ChartInputBoundary interactor = new ChartInteractor(presenter, dataAccess); // Interactor handles business logic
        controller = new ChartController(interactor); // Controller connects UI with backend

        // Initialize UI components
        controlPanel = new ControlPanel(e -> handleUserAction(ticker)); // Handles user input events
        chartPanel = new ChartPanelView("Stock Data: " + ticker); // Chart rendering panel

        // Add components to the JFrame
        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Handle user interactions and trigger data fetching through the controller.
     */
    private void handleUserAction(String ticker) {
        // Gather user inputs from the control panel
        String timeframe = controlPanel.getSelectedTimeframe();
        boolean includePriceHistory = controlPanel.isPriceHistorySelected();
        boolean includeSma = controlPanel.isSmaSelected();
        boolean includeEma = controlPanel.isEmaSelected();
        boolean includeRsi = controlPanel.isRsiSelected();

        // Send user inputs to the controller
        controller.fetchChartData(ticker, timeframe, includePriceHistory, includeSma, includeEma, includeRsi);

        // Update the chart panel with the latest dataset from the ViewModel
        chartPanel.updateChart(viewModel.getDataset());

        // Display any errors from the ViewModel
        if (viewModel.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, viewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartView view = new ChartView("AAPL"); // Example ticker
            view.setVisible(true);
        });
    }
}
