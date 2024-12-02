package frameworks_driver.view.explore;

import frameworks_driver.view.style_helpers.ColourManager;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;
import frameworks_driver.view.chart.ChartView;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

/**
 * Represents the main view for the Explore feature, allowing users to browse and select companies
 * and view their details alongside charts and statistics.
 */
public class ExploreView extends JPanel {
    private final ExploreViewModel viewModel;

    /**
     * Constructs a new ExploreView object.
     *
     * @param controller the controller for handling user interactions
     * @param viewModel  the view model providing data and state for the view
     * @param chartView  the chart view for displaying stock charts
     */
    public ExploreView(ExploreController controller, ExploreViewModel viewModel, ChartView chartView) {
        this.viewModel = viewModel;
        setLayout(new BorderLayout());

        // Company list
        DefaultListModel<String> companyListModel = new DefaultListModel<>();
        JList<String> companyList = new JList<>(companyListModel);
        companyList.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(companyList);
        scrollPane.getViewport().getView().setBackground(ColourManager.MEDIUM_GRAY);

        // Create stats panel and search panel
        ExploreStatsPanel statsPanel = new ExploreStatsPanel(chartView);
        ExploreSearchPanel searchPanel = new ExploreSearchPanel(controller, viewModel, companyList);
        SwingUtilities.invokeLater(() -> scrollPane.requestFocusInWindow());

        // Create split pane for company list and stats panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, statsPanel);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.2);

        // Customize split pane divider
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

        // Add components to main panel
        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Company selection functionality
        companyList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !companyList.isSelectionEmpty()) {
                String selectedCompany = companyList.getSelectedValue();
                controller.getCompanyDetails(selectedCompany);
                SwingUtilities.invokeLater(() -> {
                    render(selectedCompany);
                    statsPanel.updateStatsPanel(viewModel.getStockInfoOutput());
                });
            }
        });
    }

    /**
     * Displays an error message if the view model is in an error state.
     *
     * @param company the company name associated with the error
     */
    public void render(String company) {
        boolean errorState = viewModel.isErrorState();
        if (errorState) {
            JOptionPane.showMessageDialog(this, ExploreViewModel.ERROR_MESSAGE + company,
                    "Error", JOptionPane.WARNING_MESSAGE);
            viewModel.setErrorState(false);
        }
    }
}
