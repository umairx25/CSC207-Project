package entity;

import java.util.LinkedHashMap;

/**
 * Represents chart information for a specific stock, including price history
 * and various technical indicators such as SMA, EMA, and RSI.
 */
public class ChartInfo {
    private final String ticker;
    private final LinkedHashMap<Long, Double> priceHistory;
    private final LinkedHashMap<Long, Double> sma;
    private final LinkedHashMap<Long, Double> ema;
    private final LinkedHashMap<Long, Double> rsi;

    /**
     * Constructs a new ChartInfo object.
     *
     * @param ticker       the stock ticker symbol
     * @param priceHistory the historical prices of the stock, where the key is the timestamp, and the value is the price
     * @param sma          the simple moving average (SMA) values, where the key is the timestamp, and the value is the SMA
     * @param ema          the exponential moving average (EMA) values, where the key is the timestamp, and the value is the EMA
     * @param rsi          the relative strength index (RSI) values, where the key is the timestamp, and the value is the RSI
     */
    public ChartInfo(String ticker, LinkedHashMap<Long, Double> priceHistory, LinkedHashMap<Long, Double> sma,
                     LinkedHashMap<Long, Double> ema, LinkedHashMap<Long, Double> rsi) {
        this.ticker = ticker;
        this.priceHistory = priceHistory;
        this.sma = sma;
        this.ema = ema;
        this.rsi = rsi;
    }

    /**
     * @return the stock ticker symbol
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @return the historical prices of the stock, where the key is the timestamp, and the value is the price
     */
    public LinkedHashMap<Long, Double> getPriceHistory() {
        return priceHistory;
    }

    /**
     * @return the simple moving average (SMA) values, where the key is the timestamp, and the value is the SMA
     */
    public LinkedHashMap<Long, Double> getSma() {
        return sma;
    }

    /**
     * @return the exponential moving average (EMA) values, where the key is the timestamp, and the value is the EMA
     */
    public LinkedHashMap<Long, Double> getEma() {
        return ema;
    }

    /**
     * @return the relative strength index (RSI) values, where the key is the timestamp, and the value is the RSI
     */
    public LinkedHashMap<Long, Double> getRsi() {
        return rsi;
    }
}
