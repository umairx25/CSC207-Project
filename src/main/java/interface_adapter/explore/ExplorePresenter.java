package interface_adapter.explore;

import app.Builder;
import use_case.explore.CompanyOutputData;
import use_case.explore.ExploreOutputBoundary;
import use_case.explore.SearchOutputData;

/**
 * The presenter class responsible for translating use case output data
 * into a form suitable for the Explore ViewModel and facilitating
 * navigation to other views.
 */
public class ExplorePresenter implements ExploreOutputBoundary {

    private final ExploreViewModel exploreViewModel;
    private final Builder builder;

    /**
     * Constructs an ExplorePresenter.
     *
     * @param viewModel the ViewModel to store and provide data for the Explore View
     * @param builder   the application builder responsible for switching views
     */
    public ExplorePresenter(ExploreViewModel viewModel, Builder builder) {
        this.exploreViewModel = viewModel;
        this.builder = builder;
    }

    /**
     * Updates the ExploreViewModel with the list of companies from the search results.
     *
     * @param searchOutputData the search results containing the list of companies
     */
    @Override
    public void presentCompanies(SearchOutputData searchOutputData) {
        exploreViewModel.setSearchOutput(searchOutputData.getCompanies());
    }

    /**
     * Updates the ExploreViewModel with the details of a selected company.
     *
     * @param companyOutputData the data containing the company's details
     */
    @Override
    public void presentCompanyDetails(CompanyOutputData companyOutputData) {
        exploreViewModel.setStockInfoOutput(companyOutputData.getStats());
    }

    /**
     * Sets the error state in the ExploreViewModel to notify the view of an error.
     *
     * @param errorState a boolean indicating whether an error occurred
     */
    @Override
    public void presentError(boolean errorState) {
        exploreViewModel.setErrorState(true);
    }

    /**
     * Navigates the application to the home view using the Builder.
     */
    @Override
    public void switchToHomeView() {
        builder.showView("home");
    }
}
