package frameworks_driver.view.chart;

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

public class ChartPanelView extends JPanel {

    private final ChartPanel chartPanel;
    private JFreeChart chart;

    public ChartPanelView(String title) {
        setLayout(new BorderLayout());
        chart = createEmptyChart(title);
        chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
    }

    private JFreeChart createEmptyChart(String title) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                title,
                "Date",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = new NumberAxis("Price");
        plot.setRangeAxis(rangeAxis);
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickUnit(new NumberTickUnit(20));
        return chart;
    }

    public void updateChart(DefaultCategoryDataset dataset) {
        chart.getCategoryPlot().setDataset(dataset);
    }
}