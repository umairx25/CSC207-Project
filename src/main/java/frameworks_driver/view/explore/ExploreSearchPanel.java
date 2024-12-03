package frameworks_driver.view.explore;

import frameworks_driver.view.style_helpers.ColourManager;
import interface_adapter.explore.ExploreController;
import interface_adapter.explore.ExploreViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Represents the search panel for the Explore feature, allowing users to search for companies.
 * The panel includes a search field, a search button, and a home button.
 * It handles user interactions and updates the company list based on search results.
 */
public class ExploreSearchPanel extends JPanel {

    private JTextField searchField;
    private Button searchButton;
    private Button homeButton;

    private final ExploreController controller;
    private final ExploreViewModel viewModel;
    private final DefaultListModel<String> companyListModel;

    /**
     * Constructs a new ExploreSearchPanel object.
     * Initializes the components, sets up the layout, and adds necessary listeners for user interaction.
     *
     * @param controller   the controller for handling user interactions
     * @param viewModel    the view model providing data and state for the search
     * @param companyList  the JList displaying the list of companies in the Explore view
     */
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

    /**
     * Initializes the components of the search panel, including the search field and buttons.
     */
    private void initializeComponents() {
        searchField = createSearchField();
        searchButton = createStyledButton("Search");
        homeButton = createStyledButton("Home");
    }

    /**
     * Creates and returns a styled JTextField for the search input.
     * The search field has a default placeholder text and changes behavior when focused.
     *
     * @return a styled JTextField for entering search queries
     */
    private JTextField createSearchField() {
        JTextField field = new JTextField();
        field.setBackground(ColourManager.SEARCH_BAR_GRAY);

        field.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText(ExploreViewModel.INITIAL_SEARCH_MESSAGE);
                }
            }
        });

        return field;
    }

    /**
     * Creates and returns a styled button with the specified text.
     * The button has a background and foreground color set for visual consistency.
     *
     * @param text the text label for the button
     * @return a styled Button with the specified text
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setBackground(ColourManager.MEDIUM_GRAY);
        button.setForeground(Color.WHITE);
        return button;
    }

    /**
     * Sets up the layout for the ExploreSearchPanel.
     * It arranges the search field and buttons within the panel.
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
     * Sets up the action listeners for the buttons in the panel.
     * Listens for the home button and search button actions.
     */
    private void setupListeners() {
        homeButton.addActionListener(this::handleHomeAction);
        searchButton.addActionListener(this::handleSearchAction);
    }

    /**
     * Handles the action when the home button is clicked.
     * It switches the view to the home view using the controller.
     *
     * @param e the action event triggered by the home button click
     */
    private void handleHomeAction(ActionEvent e) {
        controller.switchToHomeView();
    }

    /**
     * Handles the action when the search button is clicked.
     * It retrieves the text from the search field and triggers the search if the query is not empty.
     *
     * @param e the action event triggered by the search button click
     */
    private void handleSearchAction(ActionEvent e) {
        String query = searchField.getText().trim();
        if (!query.isEmpty() && !(query.equals(ExploreViewModel.INITIAL_SEARCH_MESSAGE))) {
            performSearch(query);
        }
    }

    /**
     * Performs the search operation by querying the companies using the controller.
     * It updates the company list model with the search results.
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
