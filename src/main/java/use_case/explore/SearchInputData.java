package use_case.explore;

/**
 * Represents the input data for the company search use case.
 */
public class SearchInputData {

    private final String query;

    /**
     * Constructs a new SearchInputData object with the given query string.
     *
     * @param input the search query
     */
    public SearchInputData(String input) {
        this.query = input;
    }

    /**
     * Returns the search query.
     *
     * @return the query string
     */
    public String getQuery() {
        return query;
    }
}
