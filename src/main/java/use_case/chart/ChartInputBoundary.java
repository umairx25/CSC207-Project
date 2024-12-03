package use_case.chart;

/**
 * Interface for the Chart Input Boundary.
 * This interface defines the method for fetching chart data based on input parameters.
 */
public interface ChartInputBoundary {

    /**
     * Fetches chart data for a given set of input parameters.
     * This method is responsible for retrieving and processing the chart data,
     * including historical prices, indicators, and other relevant data for a stock ticker.
     *
     * @param inputData The input data containing parameters such as the stock ticker,
     *                  date range, and other configuration options for fetching chart data.
     */
    void fetchChartData(ChartInputData inputData);
}