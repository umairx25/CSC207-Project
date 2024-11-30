package frameworks_driver.view.Portfolio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class PortfolioPanel extends JPanel {
    private final JTable portfolioTable;
    private final DefaultTableModel tableModel;
    private final String[] columnNames = {
            "Ticker", "Quantity", "Avg Cost", "Market Price", "Total Value", "Profit/Loss"
    };

    public PortfolioPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Portfolio Holdings"));

        String[] columnNames = {
                "Ticker", "Quantity", "Avg Cost", "Market Price", "Total Value", "Gain/Loss %"
        };

        // Create a DefaultTableModel with no data initially
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        portfolioTable = new JTable(tableModel);

        // Customize table appearance
        portfolioTable.setFillsViewportHeight(true);
        portfolioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add scroll pane to allow scrolling
        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTableData(Object[][] newData) {
        // Clear existing data
        tableModel.setRowCount(0);

        // Add new data
        if (newData != null) {
            for (Object[] rowData : newData) {
                tableModel.addRow(rowData);
            }
        }
    }
}