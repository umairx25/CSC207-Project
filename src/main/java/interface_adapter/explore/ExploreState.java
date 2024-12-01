package interface_adapter.explore;

import entity.Stock;
import java.util.List;

public class ExploreState {
    private List<Stock> searchResults;
    private Stock selectedStock;

    // Getters and setters

    public List<Stock> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Stock> searchResults) {
        this.searchResults = searchResults;
    }

    public Stock getSelectedStock() {
        return selectedStock;
    }

    public void setSelectedStock(Stock selectedStock) {
        this.selectedStock = selectedStock;
    }
}