package frameworks_driver.view.explore;

import frameworks_driver.view.chart.ChartView;
import frameworks_driver.view.style_helpers.ColourManager;
import entity.Stock;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * A panel that displays the stats of a selected stock, including a chart,
 * description, and key statistics such as market cap and volume.
 */
public class ExploreStatsPanel extends JPanel {
    private final ChartView chartView;
    private final JLabel descriptionLabel;
    private final JLabel[] statsLabels;

    /**
     * Constructs an ExploreStatsPanel that contains a chart view, a description,
     * and a list of stock statistics.
     *
     * @param chartView the chart view component for displaying stock performance
     */
    public ExploreStatsPanel(ChartView chartView) {
        this.chartView = chartView;
        setBackground(ColourManager.INNER_BOX_BLUE);
        setLayout(new BorderLayout());

        // Add the graph panel on top
        add(chartView, BorderLayout.NORTH);

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
        gbc.weightx = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 130, 0, 0);
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

        statsLabels = new JLabel[labelsText.length];
        for (int i = 0; i < labelsText.length; i++) {
            JLabel label = createLabel(labelsText[i]);
            label.setForeground(Color.WHITE);
            rightPanel.add(label);
            statsLabels[i] = label;
        }

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        bottomPanel.add(rightPanel, gbc);

        add(bottomPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a JLabel with a specified text and consistent font styling.
     *
     * @param text the text for the label
     * @return the created JLabel
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        Font font = new Font("Verdana", Font.BOLD, 14);
        label.setFont(font);
        return label;
    }

    /**
     * Updates the stats panel with details of the provided stock entity.
     *
     * @param company the stock entity containing details such as ticker and description
     */
    public void updateStatsPanel(Stock company) {
        // Handle the event where an invalid company is clicked
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
        descriptionLabel.setText("<html><div style='width:800px; padding-bottom:10px;'>" +
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
}
