package use_case.explore;

/**
 * Represents the input data for retrieving company details.
 */
public class CompanyInputData {

    private final String selectedCompany;

    /**
     * Constructs a new CompanyInputData object with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the selected company
     */
    public CompanyInputData(String ticker) {
        this.selectedCompany = ticker;
    }

    /**
     * Returns the selected company's ticker symbol.
     *
     * @return the ticker symbol
     */
    public String getSelectedCompany() {
        return selectedCompany;
    }
}
