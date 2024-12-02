package frameworks_driver.view.explore;

import frameworks_driver.view.style_helpers.ColourManager;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ExploreSearchPanel extends JPanel {

    // UI Components
    private JTextField searchField;
    private JButton searchButton;
    private JButton homeButton;

    // Dependencies
    private final ExploreController controller;
    private final ExploreViewModel viewModel;
    private final DefaultListModel<String> companyListModel;

    public ExploreSearchPanel(
            ExploreController controller,
            ExploreViewModel viewModel,
            JList<String> companyList
    ) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.companyListModel = (DefaultListModel<String>) companyList.getModel();

        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeComponents() {
        // Initialize search field
        searchField = createSearchField();

        // Initialize buttons
        searchButton = createStyledButton("Search");
        homeButton = createStyledButton("Home");
    }

    private JTextField createSearchField() {
        JTextField field = new JTextField();
        field.setBackground(ColourManager.SEARCH_BAR_GRAY);

        field.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            } @Override public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(ExploreViewModel.INITIAL_SEARCH_MESSAGE);
                }
            }
        });

        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ColourManager.MEDIUM_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(searchButton, BorderLayout.WEST);
        buttonPanel.add(homeButton, BorderLayout.EAST);

        add(searchField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void setupListeners() {
        homeButton.addActionListener(this::handleHomeAction);
        searchButton.addActionListener(this::handleSearchAction);
    }

    private void handleHomeAction(ActionEvent e) {
        controller.switchToHomeView();
    }

    private void handleSearchAction(ActionEvent e) {
        String query = searchField.getText().trim();
        if (!query.isEmpty() && !(query.equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE))) {
            performSearch(query);
        }
    }

    private void performSearch(String query) {
        controller.searchCompanies(query);
        SwingUtilities.invokeLater(() -> {
            companyListModel.clear();
            for (String result : viewModel.getSearchOutput()) {
                companyListModel.addElement(result);
            }
        });
    }

}
