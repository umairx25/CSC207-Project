package interface_adapter.chart;

import use_case.chart.ChartOutputBoundary;
import use_case.chart.ChartOutputData;

/**
 * Presenter that formats and updates chart data in the ViewModel.
 */
public class ChartPresenter implements ChartOutputBoundary {
    private final ChartViewModel viewModel;

    /**
     * Constructs a ChartPresenter with the specified ViewModel.
     *
     * @param viewModel the ViewModel to be updated with chart data
     */
    public ChartPresenter(ChartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Formats and updates the chart data in the ViewModel.
     *
     * @param outputData the chart data to be presented
     */
    @Override
    public void presentChartData(ChartOutputData outputData) {
        try {
            viewModel.updatePriceHistory(outputData.getPriceHistory());
            viewModel.updateSma(outputData.getSmaData());
            viewModel.updateEma(outputData.getEmaData());
            viewModel.updateRsi(outputData.getRsiData());
            viewModel.updateCurrPrice(outputData.getCurrPrice());

            String pointIncrease = outputData.getPointIncrease().toString();
            String percentIncrease = outputData.getPercentIncrease().toString();

            if (outputData.getPercentIncrease() > 0) {
                pointIncrease = "+" + pointIncrease;
                percentIncrease = "+" + percentIncrease;
            }

            viewModel.updatePointIncrease(pointIncrease);
            viewModel.updatePercentIncrease(percentIncrease);
        } catch (Exception e) {
            System.out.println("Error retrieving and updating data");
        }
    }
}