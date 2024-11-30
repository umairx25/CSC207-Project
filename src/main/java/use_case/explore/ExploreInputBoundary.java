package use_case.explore;

public interface ExploreInputBoundary {
    void searchCompanies(SearchInputData inputData);
    void getCompanyDetails(CompanyInputData inputData) throws Exception;
    String identifyInputType(String input);


    /**
     * Executes the switch to login view use case.
     */
    void switchToHomeView();

}