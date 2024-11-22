package interface_adapter.stockData;

import entity.ChartData;
import use_case.stockChart.StockChartOuputBoundary;

public class ChartDataPresenter implements StockChartOuputBoundary {
    private final ChartViewModel viewModel;

    public ChartDataPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChartData(ChartData data) {
        viewModel.setChartData(data);
    }

    @Override
    public void presentError(String message) {
        viewModel.setError(message);
    }
}