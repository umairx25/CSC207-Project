package interface_adapter.chart;

import interface_adapter.ViewManagerModel;
import use_case.chart.ChartOutputBoundary;
import use_case.chart.ChartOutputData;

public class ChartPresenter implements ChartOutputBoundary {

    private final ChartViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public ChartPresenter(ViewManagerModel viewManagerModel, ChartViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
    }

    @Override
    public void presentChartData(ChartOutputData outputData) {
        viewModel.updateEma(outputData.getEmaData());
        viewModel.updateRsi(outputData.getRsiData());
        viewModel.updatePriceHistory(outputData.getPriceHistory());
        viewModel.updateSma(outputData.getSmaData());
    }
}