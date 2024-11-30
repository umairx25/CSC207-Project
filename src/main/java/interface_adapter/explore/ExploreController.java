package interface_adapter.explore;

import use_case.explore.CompanyInputData;
import use_case.explore.ExploreInputBoundary;
import use_case.explore.SearchInputData;



public class ExploreController {
    private final ExploreInputBoundary exploreInteractor;

    public ExploreController(ExploreInputBoundary interactor) {
        this.exploreInteractor = interactor;
    }

    public void searchCompanies(String query) {
        final SearchInputData searchInputData = new SearchInputData(query);
        exploreInteractor.searchCompanies(searchInputData); //implementation needed, interactor will determine the input type
    }

    public void getCompanyDetails(String ticker) {
        final CompanyInputData companyInputData = new CompanyInputData(ticker);
        try {
            exploreInteractor.getCompanyDetails(companyInputData); //implementation needed
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the "switch to LoginView" Use Case.
     */
    public void switchToHomeView() {
        exploreInteractor.switchToHomeView();
    }

}

