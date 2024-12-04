package frameworks_driver.view.portfolio;

import frameworks_driver.view.style_helpers.ColourManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PortfolioPanel extends JPanel {
    private final JTable portfolioTable;
    private final DefaultTableModel tableModel;

    public PortfolioPanel() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Portfolio Holdings");
        titledBorder.setTitleColor(Color.WHITE);
        setBorder(titledBorder);
        setBackground(ColourManager.MEDIUM_GRAY);

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
        portfolioTable.setShowGrid(false);


// Customize the column header appearance
        JTableHeader tableHeader = portfolioTable.getTableHeader();
        tableHeader.setBackground(ColourManager.NAVY_BLUE); // Change background color
        tableHeader.setForeground(Color.WHITE); // Change text color
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14)); // Optional: change font



        JScrollPane scrollPane = new JScrollPane(portfolioTable);
        scrollPane.getViewport().getView().setBackground(Color.GRAY);
        scrollPane.getViewport().getView().setForeground(Color.WHITE);
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
