package frameworks_driver.view.chart;


import interface_adapter.chart.ChartController;
import interface_adapter.chart.ChartState;
import interface_adapter.chart.ChartViewModel;

import javax.swing.*;
import java.awt.*;

public class ChartView extends JPanel {

    private final String viewName = "chart view";

    private final ControlPanel controlPanel;
    private final ChartPanelView chartPanel;
    private final ChartViewModel chartViewModel;
    private ChartController chartController;


    public ChartView(ChartViewModel chartViewModel, ChartController chartController) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.chartViewModel = chartViewModel;
        this.chartController = chartController;

        String ticker = "AAPL";
//        setTitle("Stock Price Chart for " + ticker);
        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        // Initialize UI components
        controlPanel = new ControlPanel();
        chartPanel = new ChartPanelView("Stock Data: " + ticker);

        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);

        // Replace with actual implementation
        final ChartState currentState = chartViewModel.getState();

        chartController.fetchChartData(ticker, "day", "2023-01-01", "2023-01-31",
                currentState.isSmaselected(), currentState.isEmaselected(), currentState.isRsiselected());

    }


//    public void setChartController(ChartController controller) {
//        this.chartController = controller;
//    }

    public String getViewName() {
        System.out.println(viewName);
        return this.viewName;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                ChartView view = new ChartView(new ChartViewModel());
//                view.setVisible(true);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
}