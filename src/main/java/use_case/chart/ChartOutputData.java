package use_case.chart;

import entity.ChartInfo;

import java.util.LinkedHashMap;

/**
 * Represents the output data for the Chart Use Case.
 * This class holds the data that is presented to the view, including
 * historical stock price data, indicators (SMA, EMA, RSI), current price,
 * and price changes (both point and percent).
 */
public class ChartOutputData {

    private final ChartInfo chartInfo;
    private final Double currPrice;
    private final Double pointIncrease;
    private final Double percentIncrease;

    /**
     * Constructs a new ChartOutputData with the provided chart information,
     * current price, point increase, and percent increase.
     *
     * @param chartInfo     The chart information containing price history and indicators.
     * @param currPrice     The current price of the stock.
     * @param pointIncrease The point increase in the stock price.
     * @param percentIncrease The percent increase in the stock price.
     */
    public ChartOutputData(ChartInfo chartInfo, Double currPrice,
                           Double pointIncrease, Double percentIncrease) {
        this.chartInfo = chartInfo;
        this.currPrice = currPrice;
        this.pointIncrease = pointIncrease;
        this.percentIncrease = percentIncrease;
    }

    /**
     * Returns the price history data from the chart information.
     *
     * @return A LinkedHashMap containing the price history with timestamps as keys and prices as values.
     */
    public LinkedHashMap<Long, Double> getPriceHistory() {
        return chartInfo.getPriceHistory();
    }

    /**
     * Returns the SMA (Simple Moving Average) data from the chart information.
     *
     * @return A LinkedHashMap containing the SMA data with timestamps as keys and SMA values as values.
     */
    public LinkedHashMap<Long, Double> getSmaData() {
        return chartInfo.getSma();
    }

    /**
     * Returns the EMA (Exponential Moving Average) data from the chart information.
     *
     * @return A LinkedHashMap containing the EMA data with timestamps as keys and EMA values as values.
     */
    public LinkedHashMap<Long, Double> getEmaData() {
        return chartInfo.getEma();
    }

    /**
     * Returns the RSI (Relative Strength Index) data from the chart information.
     *
     * @return A LinkedHashMap containing the RSI data with timestamps as keys and RSI values as values.
     */
    public LinkedHashMap<Long, Double> getRsiData() {
        return chartInfo.getRsi();
    }

    /**
     * Returns the current price of the stock.
     *
     * @return The current price as a Double.
     */
    public Double getCurrPrice() {
        return currPrice;
    }

    /**
     * Returns the point increase in the stock price.
     *
     * @return The point increase as a Double.
     */
    public Double getPointIncrease() {
        return pointIncrease;
    }

    /**
     * Returns the percent increase in the stock price.
     *
     * @return The percent increase as a Double.
     */
    public Double getPercentIncrease() {
        return percentIncrease;
    }
}