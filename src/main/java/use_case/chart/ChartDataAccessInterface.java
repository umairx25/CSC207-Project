package use_case.chart;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Interface for accessing chart-related data.
 * This interface provides methods for retrieving historical data, indicator data,
 * the current price of a stock, and price increase information.
 */
public interface ChartDataAccessInterface {

    /**
     * Retrieves historical data for a given stock ticker.
     * The data is returned as a LinkedHashMap, where the key is the timestamp,
     * and the value is the price at that timestamp.
     *
     * @param ticker The stock ticker symbol (e.g., "AAPL").
     * @return A LinkedHashMap containing historical data with timestamps as keys
     *         and corresponding stock prices as values.
     */
    LinkedHashMap<Long, Double> getHistoricalData(String ticker);

    /**
     * Retrieves indicator data (e.g., SMA, EMA, RSI) for a given stock ticker.
     * The data is returned as a LinkedHashMap, where the key is the timestamp,
     * and the value is the indicator value at that timestamp.
     *
     * @param type   The type of indicator (e.g., "SMA", "EMA", "RSI").
     * @param ticker The stock ticker symbol (e.g., "AAPL").
     * @param window The window size for the indicator (e.g., 14 for SMA or EMA).
     * @return A LinkedHashMap containing indicator data with timestamps as keys
     *         and the corresponding indicator values as values.
     */
    LinkedHashMap<Long, Double> getIndicatorData(String type, String ticker, int window);

    /**
     * Retrieves the current price for a given stock ticker.
     *
     * @param ticker The stock ticker symbol (e.g., "AAPL").
     * @return The current price of the stock.
     */
    Double getCurrentPrice(String ticker);

    /**
     * Retrieves the price increase data for a given stock ticker.
     * The data is returned as a list of percentage price changes over time.
     *
     * @param ticker The stock ticker symbol (e.g., "AAPL").
     * @return A List of price increase percentages.
     */
    List<Double> getPriceIncrease(String ticker);
}