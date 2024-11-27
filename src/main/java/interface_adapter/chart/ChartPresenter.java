package interface_adapter.chart;

import use_case.chart.ChartOutputBoundary;
import use_case.chart.ChartOutputData;

public class
ChartPresenter implements ChartOutputBoundary {
    private final ChartViewModel viewModel;

    public ChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentChartData(ChartOutputData outputData) {
        try {
            viewModel.updatePriceHistory(outputData.getPriceHistory());
            viewModel.updateSma(outputData.getSmaData());
            viewModel.updateEma(outputData.getEmaData());
            viewModel.updateRsi(outputData.getRsiData());
        }

        catch (Exception e) {
            System.out.println("Error retrieving and updating data");
        }
    }

}
