package interface_adapter.chart;

import use_case.chart.ChartOutputBoundary;
import use_case.chart.ChartOutputData;

public class ChartPresenter implements ChartOutputBoundary {

    private final ChartViewModel viewModel;

    public ChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChartData(ChartOutputData outputData) {
        viewModel.updateChart(outputData.getPriceHistory(), outputData.getSmaData(),
                outputData.getEmaData(), outputData.getRsiData());
    }
}