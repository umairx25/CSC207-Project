package use_case.explore;

import java.util.List;

/**
 * Represents the output data for the company search use case.
 */
public class SearchOutputData {

    private final List<String> companies;

    /**
     * Constructs a new SearchOutputData object with the given list of company tickers.
     *
     * @param companies the list of company tickers
     */
    public SearchOutputData(List<String> companies) {
        this.companies = companies;
    }

    /**
     * Returns the list of company tickers.
     *
     * @return the list of tickers
     */
    public List<String> getCompanies() {
        return companies;
    }
}
