package use_case.explore;

/**
 * The Input Data for the Search Use Case.
 */
public class SearchInputData {
    private final String query;

    public SearchInputData(String input) {
        this.query = input;
    }

    public String getQuery() {
        return query;
    }
}