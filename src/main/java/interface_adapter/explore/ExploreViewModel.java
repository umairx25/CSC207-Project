package interface_adapter.explore;

import entity.Stock;
import interface_adapter.ViewModel;

import java.util.List;

/**
 * A ViewModel for the Explore view, responsible for holding data
 * and state related to company search results and selected company details.
 */
public class ExploreViewModel extends ViewModel<Void> {

    /**
     * Message displayed when the search bar is initialized.
     */
    public static final String INITIAL_SEARCH_MESSAGE =
            "Enter a ticker symbol, the Market Identifier Code (MIC) for a particular stock exchange, or a keyword";

    /**
     * Message displayed when an error occurs while retrieving company details.
     */
    public static final String ERROR_MESSAGE =
            "This company no longer exists or might have changed its ticker: ";

    private List<String> searchOutput;  // List of search result company names or tickers
    private Stock StockInfoOutput;     // Detailed information about a selected company
    private boolean errorState;        // Flag indicating whether the view is in an error state

    /**
     * Constructs an ExploreViewModel with the initial view name.
     */
    public ExploreViewModel() {
        super("explore view");
    }

    /**
     * This ViewModel does not maintain state, so getState always returns null.
     *
     * @return null, as no state is used
     */
    @Override
    public Void getState() {
        return null;
    }

    /**
     * This ViewModel does not maintain state, so setState does nothing.
     *
     * @param state the state to set, which is unused
     */
    @Override
    public void setState(Void state) {
        // No state management is required for this ViewModel
    }

    /**
     * Retrieves the search results output.
     *
     * @return a list of company names or tickers from the search results
     */
    public List<String> getSearchOutput() {
        return searchOutput;
    }

    /**
     * Sets the search results output.
     *
     * @param searchOutput a list of company names or tickers
     */
    public void setSearchOutput(List<String> searchOutput) {
        this.searchOutput = searchOutput;
    }

    /**
     * Retrieves the detailed stock information for a selected company.
     *
     * @return a Stock entity containing the company details
     */
    public Stock getStockInfoOutput() {
        return StockInfoOutput;
    }

    /**
     * Sets the detailed stock information for a selected company.
     *
     * @param stockInfoOutput a Stock entity containing the company details
     */
    public void setStockInfoOutput(Stock stockInfoOutput) {
        StockInfoOutput = stockInfoOutput;
    }

    /**
     * Checks if the view is in an error state.
     *
     * @return true if the view is in an error state, false otherwise
     */
    public boolean isErrorState() {
        return errorState;
    }

    /**
     * Sets the error state of the view.
     *
     * @param errorState a boolean indicating whether an error occurred
     */
    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
    }
}
