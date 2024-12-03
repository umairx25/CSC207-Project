package use_case.chart;

/**
 * Represents the input data required to fetch chart data.
 * This class holds all the parameters necessary to retrieve chart data for a given stock ticker.
 */
public class ChartInputData {

    private final String ticker;
    private final String timespan;
    private final String startDate;
    private final String endDate;
    private final boolean includeSMA;
    private final boolean includeEMA;
    private final boolean includeRSI;
    private final boolean includePrice;

    /**
     * Constructs a new instance of ChartInputData with the specified parameters.
     *
     * @param ticker      The stock ticker symbol (e.g., "AAPL").
     * @param timespan    The timespan for the chart data (e.g., "1y" for 1 year).
     * @param startDate   The start date for the data range (in format "YYYY-MM-DD").
     * @param endDate     The end date for the data range (in format "YYYY-MM-DD").
     * @param includeSMA  Whether to include Simple Moving Average (SMA) data.
     * @param includeEMA  Whether to include Exponential Moving Average (EMA) data.
     * @param includeRSI  Whether to include Relative Strength Index (RSI) data.
     * @param includePrice Whether to include price data.
     */
    public ChartInputData(String ticker, String timespan, String startDate, String endDate,
                          boolean includeSMA, boolean includeEMA, boolean includeRSI, boolean includePrice) {
        this.ticker = ticker;
        this.timespan = timespan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeSMA = includeSMA;
        this.includeEMA = includeEMA;
        this.includeRSI = includeRSI;
        this.includePrice = includePrice;
    }

    /**
     * Gets the stock ticker symbol.
     *
     * @return The stock ticker symbol (e.g., "AAPL").
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets the timespan for the chart data.
     *
     * @return The timespan for the chart data (e.g., "1y").
     */
    public String getTimespan() {
        return timespan;
    }

    /**
     * Gets the start date for the data range.
     *
     * @return The start date in format "YYYY-MM-DD".
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date for the data range.
     *
     * @return The end date in format "YYYY-MM-DD".
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Checks whether SMA data is included in the chart.
     *
     * @return True if SMA data is included, otherwise false.
     */
    public boolean isIncludeSMA() {
        return includeSMA;
    }

    /**
     * Checks whether EMA data is included in the chart.
     *
     * @return True if EMA data is included, otherwise false.
     */
    public boolean isIncludeEMA() {
        return includeEMA;
    }

    /**
     * Checks whether RSI data is included in the chart.
     *
     * @return True if RSI data is included, otherwise false.
     */
    public boolean isIncludeRSI() {
        return includeRSI;
    }

    /**
     * Checks whether price data is included in the chart.
     *
     * @return True if price data is included, otherwise false.
     */
    public boolean isIncludePrice() {
        return includePrice;
    }
}