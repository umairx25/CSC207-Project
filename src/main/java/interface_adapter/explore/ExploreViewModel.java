package interface_adapter.explore;

import entity.Stock;
import interface_adapter.ViewModel;

import java.util.List;

public class ExploreViewModel extends ViewModel<Void> {

    public static final String TITLE_LABEL = "Explore Page";
    public static final String ERROR_MESSAGE = "This company no longer exists or might have changed its ticker: ";

    private List<String> searchOutput;
    private Stock StockInfoOutput;
    private boolean errorState;

    public ExploreViewModel() {
        super("explore");
    }

    @Override
    public Void getState() {
        return null; // Return null as we are not using state
    }

    @Override
    public void setState(Void state) {
        // Do nothing since Explore does not use state
     }

    public List<String> getSearchOutput() {
        return searchOutput;
    }

    public void setSearchOutput(List<String> searchOutput) {
        this.searchOutput = searchOutput;
    }

    public Stock getStockInfoOutput() {
        return StockInfoOutput;
    }

    public void setStockInfoOutput(Stock stockInfoOutput) {
        StockInfoOutput = stockInfoOutput;
    }

    public boolean isErrorState() {
        return errorState;
    }

    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
    }
}
