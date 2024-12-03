package use_case.explore;

/**
 * Interface defining the methods for input interactions with the Explore feature.
 */
public interface ExploreInputBoundary {

    /**
     * Searches for companies based on the given input data.
     *
     * @param inputData the search input data
     */
    void searchCompanies(SearchInputData inputData);

    /**
     * Retrieves detailed information about a specific company.
     *
     * @param inputData the company input data
     * @throws Exception if an error occurs during data retrieval
     */
    void getCompanyDetails(CompanyInputData inputData) throws Exception;

    /**
     * Identifies the type of input (e.g., ticker, exchange, or keyword).
     *
     * @param input the user-provided input
     * @return the input type as a string
     */
    String identifyInputType(String input);

    /**
     * Executes the use case to switch to the home view.
     */
    void switchToHomeView();
}
