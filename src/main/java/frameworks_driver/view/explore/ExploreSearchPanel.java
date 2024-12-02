package frameworks_driver.view.explore;

import app.Builder;
import frameworks_driver.view.style_helpers.ColourManager;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Represents the search panel for the Explore view.
 * Contains a search bar, a search button, and a home button.
 */
public class ExploreSearchPanel extends JPanel {

    // UI Components
    private JTextField searchField;
    private JButton searchButton;
    private JButton homeButton;

    // Dependencies
    private final ExploreController controller;
    private final ExploreViewModel viewModel;
    private final DefaultListModel<String> companyListModel;

    /**
     * Constructs an ExploreSearchPanel with the given controller, view model, and company list.
     *
     * @param controller the controller that handles user actions
     * @param viewModel  the view model containing the data for this panel
     * @param companyList the JList displaying the companies found in the search
     */
    public ExploreSearchPanel(ExploreController controller, ExploreViewModel viewModel, JList<String> companyList) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.companyListModel = (DefaultListModel<String>) companyList.getModel();

        initializeComponents();
        setupLayout();
        setupListeners();
    }

    /**
     * Initializes the UI components.
     */
    private void initializeComponents() {
        // Initialize search field
        searchField = createSearchField();

        // Initialize buttons
        searchButton = createStyledButton("Search");
        homeButton = createStyledButton("Home");
    }

    /**
     * Creates and configures the search field.
     *
     * @return a styled JTextField for the search bar
     */
    private JTextField createSearchField() {
        JTextField field = new JTextField();
        field.setBackground(ColourManager.SEARCH_BAR_GRAY);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(ExploreViewModel.INITIAL_SEARCH_MESSAGE);
                }
            }
        });

        return field;
    }

    /**
     * Creates a styled button with the given text.
     *
     * @param text the text to display on the button
     * @return a styled JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ColourManager.MEDIUM_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    /**
     * Configures the layout of this panel.
     */
    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(searchButton, BorderLayout.WEST);
        buttonPanel.add(homeButton, BorderLayout.EAST);

        add(searchField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * Sets up the listeners for user interactions.
     */
    private void setupListeners() {
        homeButton.addActionListener(this::handleHomeAction);
        searchButton.addActionListener(this::handleSearchAction);
    }

    /**
     * Handles the action triggered by the Home button.
     *
     * @param e the action event
     */
    private void handleHomeAction(ActionEvent e) {
        controller.switchToHomeView();
    }

    /**
     * Handles the action triggered by the Search button.
     *
     * @param e the action event
     */
    private void handleSearchAction(ActionEvent e) {
        String query = searchField.getText().trim();
        if (!query.isEmpty() && !query.equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE)) {
            performSearch(query);
        }
    }

    /**
     * Executes a search using the given query and updates the company list.
     *
     * @param query the search query entered by the user
     */
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
