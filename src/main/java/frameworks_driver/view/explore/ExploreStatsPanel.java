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
 * This panel includes a chart view at the top and various statistics such as market cap, location, exchange, etc.
 */
public class ExploreStatsPanel extends JPanel {
    private final ChartView chartView;
    private final JLabel descriptionLabel;
    private final JLabel[] statsLabels;

    /**
     * Constructs a new ExploreStatsPanel object.
     * Initializes the chart view, description label, and the panel layout, which includes
     * a bottom section for displaying various company statistics.
     *
     * @param chartView the chart view to be displayed at the top of the panel
     */
    public ExploreStatsPanel(ChartView chartView) {
        this.chartView = chartView;
        setBackground(ColourManager.INNER_BOX_BLUE);
        setLayout(new BorderLayout());

        // Add the chart panel on top
        add(chartView, BorderLayout.CENTER);

        // Create bottom panel with GridBagLayout
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(ColourManager.NAVY_BLUE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Left panel for description
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(ColourManager.NAVY_BLUE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        descriptionLabel = createLabel("");
        descriptionLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        descriptionLabel.setForeground(Color.WHITE);
        leftPanel.add(descriptionLabel);

        gbc.gridx = 0;
        gbc.weightx = 2.0;  // Make the left panel take more space horizontally
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 50, 0, 0);
        bottomPanel.add(leftPanel, gbc);

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

        gbc.weighty = 1.0;  // Allow right panel to stretch vertically

        statsLabels = new JLabel[labelsText.length];
        for (int i = 0; i < labelsText.length; i++) {
            JLabel label = createLabel(labelsText[i]);
            label.setForeground(Color.WHITE);
            rightPanel.add(label);
            statsLabels[i] = label;
        }

        gbc.gridx = 1;
        gbc.weightx = 1.0;  // Set weight of right panel to 1.0 for equal distribution
        gbc.gridheight = GridBagConstraints.REMAINDER;
        bottomPanel.add(rightPanel, gbc);

        // Add bottomPanel to main panel
        bottomPanel.setPreferredSize(new Dimension(this.getWidth(), 250));  // Set height limit
        add(bottomPanel, BorderLayout.SOUTH);

        // Make sure the chart view panel has room to grow in the center
        chartView.setPreferredSize(new Dimension(this.getWidth(), 500)); // Adjust based on your desired chart size
    }

    /**
     * Updates the statistics panel with details of the given company.
     * This method updates the description, chart, and various statistics for the selected company.
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
        descriptionLabel.setText("<html><div style='width:500px; padding-bottom:10px;'>" +
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

    /**
     * Creates and returns a JLabel with the specified text, formatted in a consistent font.
     *
     * @param text the text to be displayed in the label
     * @return a JLabel with the specified text and a predefined font
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        Font font = new Font("Verdana", Font.BOLD, 14);
        label.setFont(font);
        return label;
    }
}
