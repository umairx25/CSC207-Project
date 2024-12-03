package interface_adapter.chart;

import use_case.chart.ChartInputBoundary;
import use_case.chart.ChartInputData;

/**
 * Controller for handling chart-related requests and delegating them to the use case layer.
 */
public class ChartController {
    private final ChartInputBoundary chartInteractor;

    /**
     * Constructs a ChartController with the given interactor.
     *
     * @param chartInteractor the interactor to handle chart requests
     */
    public ChartController(ChartInputBoundary chartInteractor) {
        this.chartInteractor = chartInteractor;
    }

    /**
     * Processes a chart request and forwards the input data to the interactor.
     *
     * @param ticker        the stock ticker
     * @param timespan      the timespan for the chart
     * @param startDate     the start date for the chart data
     * @param endDate       the end date for the chart data
     * @param includeSMA    whether to include SMA in the chart
     * @param includeEMA    whether to include EMA in the chart
     * @param includeRSI    whether to include RSI in the chart
     * @param includePrice  whether to include price data in the chart
     */
    public void handleChartRequest(String ticker, String timespan, String startDate, String endDate,
                                   boolean includeSMA, boolean includeEMA, boolean includeRSI, boolean includePrice) {
        ChartInputData inputData = new ChartInputData(ticker, timespan, startDate, endDate, includeSMA, includeEMA, includeRSI, includePrice);
        chartInteractor.fetchChartData(inputData);
    }
}
