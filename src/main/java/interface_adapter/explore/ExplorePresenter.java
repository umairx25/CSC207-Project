package interface_adapter.explore;

import use_case.explore.CompanyOutputData;
import use_case.explore.ExploreOutputBoundary;
import use_case.explore.SearchOutputData;

public class ExplorePresenter implements ExploreOutputBoundary {
    private final ExploreViewModel viewModel;

    public ExplorePresenter(ExploreViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentCompanies(SearchOutputData searchOutputData) {
        viewModel.setSearchOuput(searchOutputData.getCompanies());
    }

    public void presentCompanyDetails(CompanyOutputData companyOutputData) {
        viewModel.setStockInfoOuput(companyOutputData.getStats());
    }

    public void presentError(boolean errorState) {
        viewModel.setErrorState(true);
    }
}
