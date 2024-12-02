package interface_adapter.explore;

import use_case.explore.CompanyInputData;
import use_case.explore.ExploreInputBoundary;
import use_case.explore.SearchInputData;

/**
 * The controller for the Explore feature. It acts as an intermediary between
 * the view and the use case layer, converting user input into data structures
 * that the interactor can process.
 */
public class ExploreController {

    private final ExploreInputBoundary exploreInteractor;

    /**
     * Constructs an ExploreController with the given interactor.
     *
     * @param interactor the input boundary for the Explore feature
     */
    public ExploreController(ExploreInputBoundary interactor) {
        this.exploreInteractor = interactor;
    }

    /**
     * Initiates a search for companies based on the given query.
     *
     * @param query the user-provided query string, which can include a ticker,
     *              keyword, or Market Identifier Code (MIC)
     */
    public void searchCompanies(String query) {
        final SearchInputData searchInputData = new SearchInputData(query);
        exploreInteractor.searchCompanies(searchInputData); // Delegates processing to the interactor
    }

    /**
     * Retrieves detailed information for a specific company using its ticker.
     *
     * @param ticker the ticker symbol of the company
     */
    public void getCompanyDetails(String ticker) {
        final CompanyInputData companyInputData = new CompanyInputData(ticker);
        try {
            exploreInteractor.getCompanyDetails(companyInputData); // Delegates processing to the interactor
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving company details", e);
        }
    }

    /**
     * Executes the "switch to LoginView" use case, directing the application
     * to return to the home view.
     */
    public void switchToHomeView() {
        exploreInteractor.switchToHomeView();
    }
}
