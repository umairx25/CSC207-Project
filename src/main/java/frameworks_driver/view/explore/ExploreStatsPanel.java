package frameworks_driver.view.explore;

import frameworks_driver.view.chart.ChartView;
import frameworks_driver.view.style_helpers.ColourManager;
import entity.Stock;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Represents the panel displaying detailed statistics and charts for a selected company.
 */
public class ExploreStatsPanel extends JPanel {
    private final ChartView chartView;
    private final JLabel descriptionLabel;
    private final JLabel[] statsLabels;

    /**
     * Constructs a new ExploreStatsPanel object.
     *
     * @param chartView the chart view to be displayed at the top of the panel
     */
    public ExploreStatsPanel(ChartView chartView) {
        this.chartView = chartView;
        setBackground(ColourManager.INNER_BOX_BLUE);
        setLayout(new BorderLayout());

        // Add the graph panel on top
        add(chartView, BorderLayout.CENTER);

        // Create bottom panel with BorderLayout
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(ColourManager.NAVY_BLUE);

        // Set preferred size for the bottom panel to make it taller
        bottomPanel.setPreferredSize(new Dimension(bottomPanel.getWidth(), 200));

        // Left panel for description
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(ColourManager.NAVY_BLUE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        descriptionLabel = createLabel("");
        descriptionLabel.setFont(new Font("Verdana", Font.BOLD, 10));
        descriptionLabel.setForeground(Color.WHITE);
        leftPanel.add(descriptionLabel);

        // Right panel for other stats
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(ColourManager.NAVY_BLUE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        String[] labelsText = {
                "Primary Exchange: ",
                "Market Cap: ",
                "Open: ",
                "High: ",
                "Low: ",
                "Average Volume: ",
                "Location: ",
                "Webpage: "
        };

        statsLabels = new JLabel[labelsText.length];
        for (int i = 0; i < labelsText.length; i++) {
            JLabel label = createLabel(labelsText[i]);
            label.setForeground(Color.WHITE);
            rightPanel.add(label);
            statsLabels[i] = label;
        }

        // Add panels to bottomPanel
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        // Add bottomPanel to main panel
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the statistics panel with details of the given company.
     *
     * @param company the Stock object containing company details
     */
    public void updateStatsPanel(Stock company) {
        if (company == null) {
            System.out.println("Stock object 'company' is null.");
            return;
        }

        // Update border with company name
        Font titleFont = new Font("Arial", Font.BOLD, 22);
        LineBorder lineBorder = new LineBorder(ColourManager.MEDIUM_GRAY, 7);
        TitledBorder titleBorder = new TitledBorder(lineBorder, company.getName());
        titleBorder.setTitleFont(titleFont);
        titleBorder.setTitlePosition(TitledBorder.BELOW_TOP);
        setBorder(titleBorder);

        // Update chart
        chartView.inputTicker(company.getTicker());

        // Update description
        descriptionLabel.setText("<html><div style='width:400px; padding-bottom:10px;'>" +
                company.getDescription() + "</div></html>");

        // Update stats labels
        statsLabels[0].setText("Primary Exchange: " + company.getPrimaryExchange());
        statsLabels[1].setText("Market Cap: " + company.getMarketCap());
        statsLabels[2].setText("Open: " + company.getOpen());
        statsLabels[3].setText("High: " + company.getHigh());
        statsLabels[4].setText("Low: " + company.getLow());
        statsLabels[5].setText("Average Volume: " + company.getVolume());
        statsLabels[6].setText("Location: " + company.getLocation());
        statsLabels[7].setText("Webpage: " + company.getWebpage());

        revalidate();
        repaint();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        Font font = new Font("Verdana", Font.BOLD, 10);
        label.setFont(font);
        return label;
    }
}
