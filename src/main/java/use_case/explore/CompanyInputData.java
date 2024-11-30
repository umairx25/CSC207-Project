package use_case.explore;

public class CompanyInputData {
    private final String selectedCompany;

    public CompanyInputData(String ticker) {
        this.selectedCompany = ticker;
    }

    public String getSelectedCompany() {
        return selectedCompany;
    }
}
