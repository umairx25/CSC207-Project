package interface_adapter.stockData;

import use_case.stockChart.ChartInputBoundary;
import use_case.stockChart.ChartInputData;

public class ChartController {
    private final ChartInputBoundary interactor;

    public ChartController(ChartInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void fetchChartData(String ticker, String timespan, boolean includePriceHistory, boolean includeSMA, boolean includeEMA, boolean includeRSI) {
        ChartInputData inputData = new ChartInputData(ticker, timespan, includePriceHistory, includeSMA, includeEMA, includeRSI);
        interactor.fetchChartData(inputData);
    }
}
