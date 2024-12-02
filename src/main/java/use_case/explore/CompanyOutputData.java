package use_case.explore;

import entity.Stock;

/**
 * Represents the output data for detailed company information.
 */
public class CompanyOutputData {

    private final Stock stats;

    /**
     * Constructs a new CompanyOutputData object with the given stock entity.
     *
     * @param stats the stock entity containing company details
     */
    public CompanyOutputData(Stock stats) {
        this.stats = stats;
    }

    /**
     * Returns the detailed stock information.
     *
     * @return the stock entity
     */
    public Stock getStats() {
        return stats;
    }
}
