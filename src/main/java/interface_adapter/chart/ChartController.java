package interface_adapter.chart;

import use_case.chart.ChartInputBoundary;
import use_case.chart.ChartInputData;

public class ChartController {
    private final ChartInputBoundary chartInteractor;

    public ChartController(ChartInputBoundary chartInteractor) {
        this.chartInteractor = chartInteractor;
    }

    public void handleChartRequest(String ticker, String timespan, String startDate, String endDate,
                                   boolean includeSMA, boolean includeEMA, boolean includeRSI, boolean includePrice) {

        ChartInputData inputData = new ChartInputData(ticker, timespan, startDate, endDate, includeSMA, includeEMA, includeRSI, includePrice);
        chartInteractor.fetchChartData(inputData);
    }
}
