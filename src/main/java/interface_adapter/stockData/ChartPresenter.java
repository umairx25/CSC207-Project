package interface_adapter.stockData;

import org.jfree.data.category.DefaultCategoryDataset;
import use_case.stockChart.ChartOutputBoundary;
import use_case.stockChart.ChartOutputData;

public class ChartPresenter implements ChartOutputBoundary {
    private final ChartViewModel viewModel;

    public ChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChartData(ChartOutputData outputData) {
        if (outputData.hasError()) {
            viewModel.setErrorMessage(outputData.getErrorMessage());
        } else {
            DefaultCategoryDataset dataset = outputData.getDataset();
            viewModel.setDataset(dataset);
            viewModel.setErrorMessage(null); // Clear previous errors
        }
    }
}
