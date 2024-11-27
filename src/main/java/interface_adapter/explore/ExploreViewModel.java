package interface_adapter.explore;

import entity.Stock;
import interface_adapter.ViewModel;

import java.util.List;

public class ExploreViewModel extends ViewModel<ExploreState> {

    public static final String TITLE_LABEL = "Explore Page";
    public static final String ERROR_MESSAGE = "This company no longer exists or might have changed its ticker: ";

    private List<String> searchOuput;
    private Stock StockInfoOuput;
    private boolean errorState;

    public ExploreViewModel() {
        super("explore");
        setState(new ExploreState());
    }

    public List<String> getSearchOuput() {
        return searchOuput;
    }

    public void setSearchOuput(List<String> searchOuput) {
        this.searchOuput = searchOuput;
    }

    public Stock getStockInfoOuput() {
        return StockInfoOuput;
    }

    public void setStockInfoOuput(Stock stockInfoOuput) {
        StockInfoOuput = stockInfoOuput;
    }

    public boolean isErrorState() {
        return errorState;
    }

    public void setErrorState(boolean errorState) {
        this.errorState = errorState;
    }


}
