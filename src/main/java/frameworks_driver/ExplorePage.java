package frameworks_driver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExplorePage extends JPanel {

    public ExplorePage() {
        setLayout(new BorderLayout());

        // Set Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Top search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Scrollable list for company names
        DefaultListModel<String> companyListModel = new DefaultListModel<>();
        JList<String> companyList = new JList<>(companyListModel);
        JScrollPane scrollPane = new JScrollPane(companyList);

        // Stats panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        // Empty space for future graph
        JPanel graphPanel = new JPanel();
        graphPanel.setPreferredSize(new Dimension(400, 150));
        graphPanel.setBorder(BorderFactory.createTitledBorder(""));
        statsPanel.add(graphPanel);

        // Labels for company stats
        Font labelFont = new Font("Courier New", Font.PLAIN, 14); // Bold, size 14

        JLabel descriptionLabel = new JLabel("Description: ");
        descriptionLabel.setFont(labelFont);
        JLabel exchangeLabel = new JLabel("Primary Exchange: ");
        exchangeLabel.setFont(labelFont);
        JLabel marketCapLabel = new JLabel("Market Cap: ");
        marketCapLabel.setFont(labelFont);
        JLabel openLabel = new JLabel("Open: ");
        openLabel.setFont(labelFont);
        JLabel highLabel = new JLabel("High: ");
        highLabel.setFont(labelFont);
        JLabel lowLabel = new JLabel("Low: ");
        lowLabel.setFont(labelFont);
        JLabel webpageLabel = new JLabel("Webpage: ");
        webpageLabel.setFont(labelFont);
        JLabel locationLabel = new JLabel("Location: ");
        locationLabel.setFont(labelFont);
        JLabel volumeLabel = new JLabel("Average Volume: ");
        volumeLabel.setFont(labelFont);

        // Add stat labels to the stats panel
        statsPanel.add(descriptionLabel);
        statsPanel.add(exchangeLabel);
        statsPanel.add(marketCapLabel);
        statsPanel.add(openLabel);
        statsPanel.add(highLabel);
        statsPanel.add(lowLabel);
        statsPanel.add(volumeLabel);
        statsPanel.add(locationLabel);
        statsPanel.add(webpageLabel);


        // Combine the left list and right stats panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, statsPanel);
        splitPane.setDividerLocation(200); // Adjust divider location as needed

        // Add components to the main layout
        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Gather the full list of exchanges
        List<String> exchanges = PolygonAPI.getAllExchanges();

        // Search button functionality
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().trim(); // Trim spaces
                if (!query.isEmpty()) {
                    String inputType = PolygonAPI.identifyInputType(query);
                    String results;
                    if (inputType.equals("Ticker")) {
                        results = PolygonAPI.searchCompany(query, "", "");
                    } else if (inputType.equals("Exchange")) {
                        results = PolygonAPI.searchCompany("", query, "");
                    } else if (inputType.equals("Keyword")) {
                        results = PolygonAPI.searchCompany("", "", query);
                    } else {
                        throw new IllegalArgumentException("Invalid input type!");
                    }

                    List<String> companiesList = PolygonAPI.extractCompanyTickers(results); // Fetch search results
                    companyListModel.clear();
                    for (String company : companiesList) {
                        companyListModel.addElement(company);
                    }
                }
            }
        });



        // List selection listener to display stats
        companyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !companyList.isSelectionEmpty()) {
                String selectedCompany = companyList.getSelectedValue();
                updateStatsPanel(selectedCompany, statsPanel, descriptionLabel, exchangeLabel,
                        marketCapLabel, openLabel, highLabel, lowLabel, webpageLabel, locationLabel, volumeLabel);

                Font titleFont = new Font("Arial", Font.BOLD, 16);
                TitledBorder graphTitle = null;
                try {
                    graphTitle = BorderFactory.createTitledBorder(PolygonAPI.getTickerName(selectedCompany));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                graphTitle.setTitleFont(titleFont);
                graphPanel.setBorder(graphTitle);
            }
        });

    }

//    private void updateStatsPanel(String company, JPanel statsPanel, JLabel desc, JLabel exchange,
//                                  JLabel marketCap, JLabel open, JLabel high, JLabel low, JLabel webpage,
//                                  JLabel location, JLabel volume) throws Exception {
//        // Fetch details from the PolygonAPI
//        String description = PolygonAPI.getDesc(company);
//        String primaryExchange = PolygonAPI.getPrimaryExchange(company);
//        String marketCapValue = PolygonAPI.getMarketCap(company);
//        String openingPrice = PolygonAPI.getOpen(company);
//        String highPrice = PolygonAPI.getHighLow(company).get(0);
//        String lowPrice = PolygonAPI.getHighLow(company).get(1);
//        String webpageUrl = PolygonAPI.getWebpage(company);
//        String companyLocation = PolygonAPI.getLocation(company);
//        String avgVolume = PolygonAPI.calculateAverageVolume(company);
//
//        // Update labels
//        desc.setText("<html><div style='width:800px; padding-bottom:10px;'>" + description + "</div></html>");// Wrapping text
//        exchange.setText("Primary Exchange: " + primaryExchange);
//        marketCap.setText("Market Cap: " + marketCapValue);
//        open.setText("Open: " + openingPrice);
//        high.setText("High: " + highPrice);
//        low.setText("Low: " + lowPrice);
//        volume.setText("Average Volume: " + avgVolume);
//        location.setText("Location: " + companyLocation);
//        webpage.setText("Webpage: " + webpageUrl);
//
//
//        // Refresh stats panel
//        statsPanel.revalidate();
//        statsPanel.repaint();
//    }
    private void updateStatsPanel(String company, JPanel statsPanel, JLabel desc, JLabel exchange,
                                  JLabel marketCap, JLabel open, JLabel high, JLabel low, JLabel webpage,
                                  JLabel location, JLabel volume) {
        try {
            // Fetch details from the PolygonAPI
            String description = PolygonAPI.getDesc(company);
            String primaryExchange = PolygonAPI.getPrimaryExchange(company);
            String marketCapValue = PolygonAPI.getMarketCap(company);
            String openingPrice = PolygonAPI.getOpen(company);
            String highPrice = PolygonAPI.getHighLow(company).get(0);
            String lowPrice = PolygonAPI.getHighLow(company).get(1);
            String webpageUrl = PolygonAPI.getWebpage(company);
            String companyLocation = PolygonAPI.getLocation(company);
            String avgVolume = PolygonAPI.calculateAverageVolume(company);

            // Update labels
            desc.setText("<html><div style='width:800px; padding-bottom:10px;'>" + description + "</div></html>");// Wrapping text
            exchange.setText("Primary Exchange: " + primaryExchange);
            marketCap.setText("Market Cap: " + marketCapValue);
            open.setText("Open: " + openingPrice);
            high.setText("High: " + highPrice);
            low.setText("Low: " + lowPrice);
            volume.setText("Average Volume: " + avgVolume);
            location.setText("Location: " + companyLocation);
            webpage.setText("Webpage: " + webpageUrl);

            // Refresh stats panel
            statsPanel.revalidate();
            statsPanel.repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(statsPanel, "This company no longer exists or might have " +
                    "changed its ticker: " + company, "Error", JOptionPane.WARNING_MESSAGE);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Explore Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 600);

        ExplorePage explorePage = new ExplorePage();

        frame.add(explorePage);
        frame.setVisible(true);
    }
}

