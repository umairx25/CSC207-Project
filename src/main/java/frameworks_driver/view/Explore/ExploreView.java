package frameworks_driver.view.explore;

import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;
import frameworks_driver.view.chart.ChartView;
import entity.Stock;
import view.ColourManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ExploreView extends JPanel {
    private final ExploreViewModel viewModel;
    private final ChartView chartView;


    public ExploreView(ExploreController controller, ExploreViewModel viewModel, ChartView chartView) {
        this.viewModel = viewModel;
        this.chartView = chartView;
        setLayout(new BorderLayout());

        // Search bar and buttons
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton homeButton = new JButton("Home");

        searchField.setBackground(new Color(230,230,230));
        searchButton.setBackground(ColourManager.MEDIUM_GRAY);
        searchButton.setForeground(Color.WHITE);
        homeButton.setBackground(ColourManager.MEDIUM_GRAY);
        homeButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(searchButton, BorderLayout.WEST);
        buttonPanel.add(homeButton, BorderLayout.EAST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);

        // Scrollable list
        DefaultListModel<String> companyListModel = new DefaultListModel<>();
        JList<String> companyList = new JList<>(companyListModel);
        companyList.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(companyList);
        scrollPane.getViewport().getView().setBackground(ColourManager.MEDIUM_GRAY);


        //Graph panel
        JPanel graphPanel = new JPanel();
        graphPanel.setBackground(ColourManager.INNER_BOX_BLUE);
        graphPanel.add(chartView);

        // Stats panel
        JPanel statsPanel = createStatsPanel(graphPanel);

        //Split Pane for statsPanel and scrollPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, statsPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.2);

        // Change the divider color
        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void paint(Graphics g) {
                        g.setColor(ColourManager.LIGHT_GRAY);
                        g.fillRect(0, 0, getSize().width, getSize().height);
                        super.paint(g);
                    }
                };
            }
        });

        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Home button functionality
        homeButton.addActionListener(e -> controller.switchToHomeView());

        // Search button functionality
        searchButton.addActionListener((ActionEvent e) -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                controller.searchCompanies(query);
                SwingUtilities.invokeLater(() -> {
                    companyListModel.clear();
                    for (String result : viewModel.getSearchOutput()) {
                        companyListModel.addElement(result);
                    }
                });
            }
        });

        // Company selection functionality
        companyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !companyList.isSelectionEmpty()) {
                String selectedCompany = companyList.getSelectedValue();
                controller.getCompanyDetails(selectedCompany);
                SwingUtilities.invokeLater(() -> {
                    render(statsPanel, selectedCompany);
                    updateStatsPanel(statsPanel, viewModel.getStockInfoOutput());

                });
            }
        });
    }

    private JPanel createStatsPanel(JPanel graphPanel) {
        JPanel panel = new JPanel();
        panel.setBackground(ColourManager.NAVY_BLUE);
        panel.setLayout(new BorderLayout());

        // Add the graph panel on top
        panel.add(graphPanel, BorderLayout.NORTH);

        // Create a bottom panel with two parts: left for description and right for other labels
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(ColourManager.NAVY_BLUE);
        bottomPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Left panel for description
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(ColourManager.NAVY_BLUE);
        gbc.insets = new Insets(0, 200, 0, 0);  // Adjust left padding
        gbc.gridx = 0;
        gbc.weightx = 0;  // No resizing for the description section
        gbc.gridheight = GridBagConstraints.REMAINDER;  // Make sure it spans the height
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Create the description label
        JLabel descriptionLabel = createLabel("");
        descriptionLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        leftPanel.add(descriptionLabel);
        descriptionLabel.setForeground(Color.WHITE);
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

        // Create and add the other stats labels to the right panel
        for (String text : labelsText) {
            JLabel label = createLabel(text);
            label.setForeground(Color.WHITE); // Set label text to white for better contrast
            rightPanel.add(label);
        }

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Allow resizing to fill available space
        gbc.gridheight = GridBagConstraints.REMAINDER;  // Make sure the right panel spans the height
        bottomPanel.add(rightPanel, gbc);

        // Add the bottom panel to the main panel
        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }


    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        Font font = new Font("Verdana", Font.BOLD, 14);
        label.setFont(font);
        return label;
    }


    private void updateStatsPanel(JPanel statsPanel, Stock company) {
        Component[] components = statsPanel.getComponents();

        try {
            Font titleFont = new Font("Arial", Font.BOLD, 22);
            LineBorder lineBorder = new LineBorder(ColourManager.MEDIUM_GRAY, 7);
            TitledBorder titleBorder = new TitledBorder(lineBorder, company.getName());
            titleBorder.setTitleFont(titleFont);
            titleBorder.setTitlePosition(TitledBorder.BELOW_TOP);
            ((JPanel) components[0]).setBorder(titleBorder);
        } catch (Exception ex) {
            System.out.println("Stock object 'company' is null.");
            return;
        }

        // Update the chart
        chartView.inputTicker(company.getTicker());


        // Access panels and their components
        JPanel bottomPanel = (JPanel) components[1];
        JPanel leftPanel = (JPanel) bottomPanel.getComponent(0);
        Component[] leftPanelComponents = leftPanel.getComponents();
        JPanel rightPanel = (JPanel) bottomPanel.getComponent(1);
        Component[] rightPanelComponents = rightPanel.getComponents();

        // Update the description label on the left panel
        ((JLabel) leftPanelComponents[0]).setText("<html><div style='width:800px; padding-bottom:10px;'>" +
                company.getDescription() + "</div></html>");

        // Update the labels inside the right panel for other stats
        ((JLabel) rightPanelComponents[0]).setText("Primary Exchange: " + company.getPrimaryExchange());
        ((JLabel) rightPanelComponents[1]).setText("Market Cap: " + company.getMarketCap());
        ((JLabel) rightPanelComponents[2]).setText("Open: " + company.getOpen());
        ((JLabel) rightPanelComponents[3]).setText("High: " + company.getHigh());
        ((JLabel) rightPanelComponents[4]).setText("Low: " + company.getLow());
        ((JLabel) rightPanelComponents[5]).setText("Average Volume: " + company.getVolume());
        ((JLabel) rightPanelComponents[6]).setText("Location: " + company.getLocation());
        ((JLabel) rightPanelComponents[7]).setText("Webpage: " + company.getWebpage());

        statsPanel.revalidate();
        statsPanel.repaint();
    }


    // checks if company stats are in a state of error
    public void render(JPanel statsPanel, String company) {
        boolean errorState = viewModel.isErrorState();
        if (errorState) {
            JOptionPane.showMessageDialog(statsPanel, ExploreViewModel.ERROR_MESSAGE + company,
                    "Error", JOptionPane.WARNING_MESSAGE);
            viewModel.setErrorState(false); // reset to avoid continuous error the next time a valid company is selected
        }
    }
}
