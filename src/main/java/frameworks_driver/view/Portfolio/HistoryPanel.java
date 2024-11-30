package frameworks_driver.view.Portfolio;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.util.List;
public class HistoryPanel extends JPanel {
    private final DefaultListModel<String> listModel;
    private final JList<String> historyList;
    private final JScrollPane scrollPane;

    public HistoryPanel(List<String> initialHistory) {
        // Set up the panel layout
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Transaction History"));

        // Create list model
        listModel = new DefaultListModel<>();

        // Populate initial history
        if (initialHistory != null) {
            initialHistory.forEach(listModel::addElement);
        }

        // Create JList with the list model
        historyList = new JList<>(listModel);
        historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Customize list appearance
        historyList.setVisibleRowCount(15);

        // Add scroll pane
        scrollPane = new JScrollPane(historyList);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        add(scrollPane, BorderLayout.CENTER);

        // Optional: Add a listener to handle list changes
        listModel.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {
                scrollToBottom();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {}

            @Override
            public void contentsChanged(ListDataEvent e) {}
        });
    }

    public void updateHistory(List<String> newHistory) {
        // Clear existing entries
        listModel.clear();

        // Add new history entries
        if (newHistory != null) {
            newHistory.forEach(listModel::addElement);
        }
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    // Optional: Method to get selected transaction
    public String getSelectedTransaction() {
        return historyList.getSelectedValue();
    }
}