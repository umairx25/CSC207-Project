package interface_adapter.explore;

import entity.Stock;
import interface_adapter.ViewModel;

import java.util.List;

public class ExploreViewModel extends ViewModel<ExploreState> {

    public static final String TITLE_LABEL = "Explore Page";
    public static final String ERROR_MESSAGE = "This company no longer exists or might have changed its ticker: ";

    private List<String> searchOutput;
    private Stock StockInfoOutput;
    private boolean errorState;

    public ExploreViewModel() {
        super("explore");
        setState(new ExploreState());
    }

    public List<String> getSearchOutput() {
        return searchOutput;
    }

    public void setSearchOutput(List<String> searchOuput) {
        this.searchOutput = searchOuput;
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
