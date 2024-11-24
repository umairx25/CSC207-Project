package interface_adapter.stockData;

import org.jfree.data.category.DefaultCategoryDataset;

public class ChartViewModel {
    private DefaultCategoryDataset dataset;
    private String errorMessage;

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }

    public void setDataset(DefaultCategoryDataset dataset) {
        this.dataset = dataset;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
