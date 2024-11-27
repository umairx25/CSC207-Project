package use_case.explore;

public class CompanyInputData {
    private String selectedCompany;

    public CompanyInputData(String ticker) {
        this.selectedCompany = ticker;
    }

    public String getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(String selectedCompany) {
        this.selectedCompany = selectedCompany;
    }
}
