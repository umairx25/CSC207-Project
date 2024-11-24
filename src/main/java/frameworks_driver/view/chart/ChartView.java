package frameworks_driver.view.chart;


import javax.swing.*;
import java.awt.*;

public class ChartView extends JFrame {

    private final ControlPanel controlPanel;
    private final ChartPanelView chartPanel;

    public ChartView(String ticker) {
        setTitle("Stock Price Chart for " + ticker);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize UI components
        controlPanel = new ControlPanel();
        chartPanel = new ChartPanelView("Stock Data: " + ticker);

        add(controlPanel, BorderLayout.WEST);
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChartView view = new ChartView("AAPL");
            view.setVisible(true);
        });
    }
}