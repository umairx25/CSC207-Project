package use_case.explore;

import entity.Stock;
import java.util.List;

public class ExploreOutputData {
    private List<Stock> companies;

    public void setCompanies(List<Stock> companies) {
        this.companies = companies;
    }

    public List<Stock> getCompanies() {
        return companies;
    }
}
