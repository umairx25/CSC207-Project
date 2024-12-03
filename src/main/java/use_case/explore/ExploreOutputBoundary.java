package use_case.explore;

/**
 * Interface defining the methods for output interactions in the Explore feature.
 */
public interface ExploreOutputBoundary {

    /**
     * Presents the search results to the user.
     *
     * @param searchOutputData the output data containing the list of company tickers
     */
    void presentCompanies(SearchOutputData searchOutputData);

    /**
     * Presents detailed company information to the user.
     *
     * @param companyOutputData the output data containing detailed company statistics
     */
    void presentCompanyDetails(CompanyOutputData companyOutputData);

    /**
     * Signals an error state to the presenter.
     *
     * @param errorState true if an error occurred, false otherwise
     */
    void presentError(boolean errorState);

    /**
     * Executes the use case to switch to the home view.
     */
    void switchToHomeView();
}
