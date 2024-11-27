package use_case.explore;

import entity.Stock;
import java.util.List;

public class SearchOutputData {
    private List<String> companies;

    public SearchOutputData(List<String> companies) {
        this.companies = companies;
    }

    public List<String> getCompanies() {
        return companies;
    }
}
