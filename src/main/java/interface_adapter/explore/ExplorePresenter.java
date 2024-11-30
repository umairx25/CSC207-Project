package interface_adapter.explore;

import use_case.explore.CompanyOutputData;
import use_case.explore.ExploreOutputBoundary;
import use_case.explore.SearchOutputData;

public class ExplorePresenter implements ExploreOutputBoundary {
    private final ExploreViewModel exploreViewModel;
//    private final HomeViewModel homeViewModel;
//    private final ViewManagerModel viewManagerModel;

    public ExplorePresenter(ExploreViewModel viewModel) {
        this.exploreViewModel = viewModel;
//        this.homeViewModel = homeViewModel;
//        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentCompanies(SearchOutputData searchOutputData) {
        exploreViewModel.setSearchOutput(searchOutputData.getCompanies());
    }

    @Override
    public void presentCompanyDetails(CompanyOutputData companyOutputData) {
        exploreViewModel.setStockInfoOutput(companyOutputData.getStats());
    }

    @Override
    public void presentError(boolean errorState) {
        exploreViewModel.setErrorState(true);
    }

    @Override
    public void switchToHomeView() {
//        viewManagerModel.setState(homeViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
        System.out.println("not implemented yet");
    }
}
