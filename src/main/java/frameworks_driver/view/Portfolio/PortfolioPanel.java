package frameworks_driver.view.Portfolio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PortfolioPanel extends JPanel {
    private final JTable portfolioTable;
    private final DefaultTableModel tableModel;

    public PortfolioPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Portfolio Holdings"));

        String[] columnNames = {
                "Ticker", "Quantity", "Avg Cost", "Market Price", "Total Value", "Gain/Loss %"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        portfolioTable = new JTable(tableModel);
        portfolioTable.setFillsViewportHeight(true);
        portfolioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTableData(Object[][] newData) {
        tableModel.setRowCount(0); // Clear existing rows
        if (newData != null) {
            for (Object[] rowData : newData) {
                tableModel.addRow(rowData);  // Add the new data rows
            }
        }
    }
}
