package frameworks_driver.view.portfolio;

import frameworks_driver.view.style_helpers.ColourManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
public class HistoryPanel extends JPanel {
    private final DefaultListModel<String> listModel;
    private final JList<String> historyList;

    public HistoryPanel(List<Map<String, Object>> initialHistory) {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = new TitledBorder("Transaction History");
        titledBorder.setTitleColor(Color.WHITE);
        setBorder(titledBorder);

        setBackground(ColourManager.MEDIUM_GRAY);

        listModel = new DefaultListModel<>();
        if (initialHistory != null) {
            flattenAndAddToListModel(initialHistory);
        }

        historyList = new JList<>(listModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.getViewport().getView().setBackground(Color.GRAY);
        scrollPane.getViewport().getView().setForeground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateHistory(List<Map<String, Object>> newHistory) {
        listModel.clear();  // Clear existing history
        if (newHistory != null) {
            flattenAndAddToListModel(newHistory);  // Add new history data
        }
    }


    private void flattenAndAddToListModel(List<Map<String, Object>> historyList) {
        for (Map<String, Object> transaction : historyList) {
            String type = (String) transaction.get("type");
            String company = (String) transaction.get("company");

            // Add type conversion to handle potential Long
            int quantity = ((Number) transaction.get("quantity")).intValue();

            double price = ((Number) transaction.get("price")).doubleValue();

            String formattedTransaction = String.format("%s %s: %d shares at $%.2f", type, company, quantity, price);
            listModel.addElement(formattedTransaction);
            System.out.println(formattedTransaction);
        }
    }
}
