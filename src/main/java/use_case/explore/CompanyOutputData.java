package use_case.explore;

import entity.Stock;

public class CompanyOutputData {
    private Stock stats;

    public CompanyOutputData(Stock stats) {
        this.stats = stats;
    }

    public void setStats(Stock stats) {
        this.stats = stats;
    }

    public Stock getStats() {
        return stats;
    }
}
