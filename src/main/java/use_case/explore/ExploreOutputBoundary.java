package use_case.explore;

public interface ExploreOutputBoundary {
    void presentCompanies(SearchOutputData searchOutputData);
    void presentCompanyDetails(CompanyOutputData companyOutputData);
    void presentError(boolean errorState);
}