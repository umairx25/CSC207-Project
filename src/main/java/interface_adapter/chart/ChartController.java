//package interface_adapter.chart;
//
//import use_case.chart.ChartInputBoundary;
//import use_case.chart.ChartInputData;
//
//public class ChartController {
//    private final ChartInputBoundary chartInputBoundary;
//
//    public ChartController(ChartInputBoundary chartInputBoundary) {
//        this.chartInputBoundary = chartInputBoundary;
//    }
//
//    /**
//     * Fetches chart data using the provided parameters.
//     *
//     * @param ticker    The stock ticker symbol.
//     * @param timespan  The granularity of the data (e.g., Day, Month).
//     * @param startDate The start date of the chart data.
//     * @param endDate   The end date of the chart data.
//     * @param includeSMA Whether to include Simple Moving Average (SMA).
//     * @param includeEMA Whether to include Exponential Moving Average (EMA).
//     * @param includeRSI Whether to include Relative Strength Index (RSI).
//     */
//    public void fetchChartData(String ticker, String timespan, String startDate, String endDate,
//                                      boolean includeSMA, boolean includeEMA, boolean includeRSI) {
//        // Create ChartInputData object with user input
//        ChartInputData inputData = new ChartInputData(ticker, timespan, startDate, endDate,
//                includeSMA, includeEMA, includeRSI);
//        // Delegate to the use case
//        chartInputBoundary.fetchChartData(inputData);
//    }
//}

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
