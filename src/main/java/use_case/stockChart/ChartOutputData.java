package use_case.stockChart;

import org.jfree.data.category.DefaultCategoryDataset;

public class ChartOutputData {
    private final DefaultCategoryDataset dataset;
    private final String errorMessage;

    public ChartOutputData(DefaultCategoryDataset dataset, String errorMessage) {
        this.dataset = dataset;
        this.errorMessage = errorMessage;
    }

    public DefaultCategoryDataset getDataset() {
        return dataset;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isEmpty();
    }
}
