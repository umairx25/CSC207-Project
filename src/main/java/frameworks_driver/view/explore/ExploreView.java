package frameworks_driver.view;

import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;
import entity.Stock;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ExploreView extends JPanel {
    private final ExploreViewModel viewModel;

    public ExploreView(ExploreController controller, ExploreViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton homeButton = new JButton("Home"); // Added Home button
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(searchButton, BorderLayout.WEST);
        buttonPanel.add(homeButton, BorderLayout.EAST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);

        // Scrollable list
        DefaultListModel<String> companyListModel = new DefaultListModel<>();
        JList<String> companyList = new JList<>(companyListModel);
        JScrollPane scrollPane = new JScrollPane(companyList);

        //Graph panel
        JPanel graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(400, 150));
        graphPanel.setBorder(BorderFactory.createTitledBorder(""));

        // Stats panel
        JPanel statsPanel = createStatsPanel(graphPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, statsPanel);
        splitPane.setDividerLocation(200);

        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Home button functionality
        homeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(statsPanel, "Returning to the home frameworks_driver.view. Backend not yet implemented.");
        });

        // Search button functionality
        searchButton.addActionListener((ActionEvent e) -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                controller.searchCompanies(query);
                companyListModel.clear();
                for (String result : viewModel.getSearchOuput()) {
                    companyListModel.addElement(result);
                }
            }
        });

        // Company selection listener
        companyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !companyList.isSelectionEmpty()) {
                String selectedCompany = companyList.getSelectedValue(); //ticker & update state
                controller.getCompanyDetails(selectedCompany);//converts ticker to its Stock object
                render(statsPanel,selectedCompany); //implement
                updateStatsPanel(statsPanel, viewModel.getStockInfoOuput());

            }
        });
    }

    private JPanel createStatsPanel(JPanel graphPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(graphPanel);

        Font labelFont = new Font("Courier New", Font.PLAIN, 14);

        // Labels for company stats
        String[] labelsText = {
                "Description: ",
                "Primary Exchange: ",
                "Market Cap: ",
                "Open: ",
                "High: ",
                "Low: ",
                "Webpage: ",
                "Location: ",
                "Average Volume: "
        };

        // Create and add labels
        for (String text : labelsText) {
            panel.add(createLabel(text, labelFont));
        }
        return panel;


    }

    //Helper for createStatsPanel (above)
    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void updateStatsPanel(JPanel statsPanel, Stock company) {
        Component[] components = statsPanel.getComponents();

        Font titleFont = new Font("Arial", Font.BOLD, 16);
        TitledBorder graphTitle;

        try {
            graphTitle = BorderFactory.createTitledBorder(company.getName());
            graphTitle.setTitleFont(titleFont);
            ((JPanel) components[0]).setBorder(graphTitle); //this may be an issue

        } catch (ClassCastException ex) { //will check for the issue of casting JPanel (it's supposed to be a JPanel though)
            throw new RuntimeException(ex);
        }

        ((JLabel) components[1]).setText("<html><div style='width:800px; padding-bottom:10px;'>" + company.getDescription() + "</div></html>");
        ((JLabel) components[2]).setText("Primary Exchange: " + company.getPrimaryExchange());
        ((JLabel) components[3]).setText("Market Cap: " + company.getMarketCap());
        ((JLabel) components[4]).setText("Open: " + company.getOpen());
        ((JLabel) components[5]).setText("High: " + company.getHigh());
        ((JLabel) components[6]).setText("Low: " + company.getLow());
        ((JLabel) components[7]).setText("Average Volume: " + company.getVolume());
        ((JLabel) components[8]).setText("Location: " + company.getLocation());
        ((JLabel) components[9]).setText("Webpage: " + company.getWebpage());

        statsPanel.revalidate();
        statsPanel.repaint();
    }

    //checks if company stats are in a state of error
    public void render(JPanel statsPanel, String company) {
        boolean errorState = viewModel.isErrorState();
        if (errorState) {
            JOptionPane.showMessageDialog(statsPanel, ExploreViewModel.ERROR_MESSAGE + company, "Error", JOptionPane.WARNING_MESSAGE);
            viewModel.setErrorState(false); //reset to avoid continuous error the next time a good company is selected
        }
    }
}