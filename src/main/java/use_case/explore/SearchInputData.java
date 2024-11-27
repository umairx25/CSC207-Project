package use_case.explore;


/**
 * The Input Data for the Search Use Case.
 */
public class SearchInputData {
    private String query;

    public SearchInputData(String input) {
        this.query = input;
    }

    public String getQuery() {
        return query;
    }
}