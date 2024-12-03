package interface_adapter.chart;

import java.util.LinkedHashMap;

/**
 * Represents the state of a chart, including its title, selected indicators,
 * and corresponding data such as price history, SMA, EMA, and RSI.
 */
public class ChartState {
    private String title;
    private boolean smaSelected;
    private boolean emaSelected;
    private boolean rsiSelected;
    private boolean priceHistorySelected;

    private LinkedHashMap<Long, Double> priceHistory;
    private LinkedHashMap<Long, Double> ema;
    private LinkedHashMap<Long, Double> sma;
    private LinkedHashMap<Long, Double> rsi;

    /**
     * Retrieves the title of the chart.
     *
     * @return the chart title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the chart.
     *
     * @param title the new title of the chart
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the price history data for the chart.
     *
     * @return a {@link LinkedHashMap} containing price history data, where keys are timestamps and values are prices
     */
    public LinkedHashMap<Long, Double> getPriceHistory() {
        return priceHistory;
    }

    /**
     * Sets the price history data for the chart.
     *
     * @param priceHistory a {@link LinkedHashMap} containing price history data, where keys are timestamps and values are prices
     */
    public void setPriceHistory(LinkedHashMap<Long, Double> priceHistory) {
        this.priceHistory = priceHistory;
    }

    /**
     * Retrieves the EMA (Exponential Moving Average) data for the chart.
     *
     * @return a {@link LinkedHashMap} containing EMA data, where keys are timestamps and values are EMA values
     */
    public LinkedHashMap<Long, Double> getEma() {
        return ema;
    }

    /**
     * Sets the EMA (Exponential Moving Average) data for the chart.
     *
     * @param ema a {@link LinkedHashMap} containing EMA data, where keys are timestamps and values are EMA values
     */
    public void setEma(LinkedHashMap<Long, Double> ema) {
        this.ema = ema;
    }

    /**
     * Retrieves the SMA (Simple Moving Average) data for the chart.
     *
     * @return a {@link LinkedHashMap} containing SMA data, where keys are timestamps and values are SMA values
     */
    public LinkedHashMap<Long, Double> getSma() {
        return sma;
    }

    /**
     * Sets the SMA (Simple Moving Average) data for the chart.
     *
     * @param sma a {@link LinkedHashMap} containing SMA data, where keys are timestamps and values are SMA values
     */
    public void setSma(LinkedHashMap<Long, Double> sma) {
        this.sma = sma;
    }

    /**
     * Retrieves the RSI (Relative Strength Index) data for the chart.
     *
     * @return a {@link LinkedHashMap} containing RSI data, where keys are timestamps and values are RSI values
     */
    public LinkedHashMap<Long, Double> getRsi() {
        return rsi;
    }

    /**
     * Sets the RSI (Relative Strength Index) data for the chart.
     *
     * @param rsi a {@link LinkedHashMap} containing RSI data, where keys are timestamps and values are RSI values
     */
    public void setRsi(LinkedHashMap<Long, Double> rsi) {
        this.rsi = rsi;
    }

    /**
     * Checks if the SMA (Simple Moving Average) indicator is selected for the chart.
     *
     * @return {@code true} if SMA is selected, {@code false} otherwise
     */
    public boolean isSmaSelected() {
        return smaSelected;
    }

    /**
     * Sets the SMA (Simple Moving Average) selection state for the chart.
     *
     * @param smaSelected {@code true} to select SMA, {@code false} to deselect it
     */
    public void setSmaSelected(boolean smaSelected) {
        this.smaSelected = smaSelected;
    }

    /**
     * Checks if the EMA (Exponential Moving Average) indicator is selected for the chart.
     *
     * @return {@code true} if EMA is selected, {@code false} otherwise
     */
    public boolean isEmaSelected() {
        return emaSelected;
    }

    /**
     * Sets the EMA (Exponential Moving Average) selection state for the chart.
     *
     * @param emaSelected {@code true} to select EMA, {@code false} to deselect it
     */
    public void setEmaSelected(boolean emaSelected) {
        this.emaSelected = emaSelected;
    }

    /**
     * Checks if the RSI (Relative Strength Index) indicator is selected for the chart.
     *
     * @return {@code true} if RSI is selected, {@code false} otherwise
     */
    public boolean isRsiSelected() {
        return rsiSelected;
    }

    /**
     * Sets the RSI (Relative Strength Index) selection state for the chart.
     *
     * @param rsiSelected {@code true} to select RSI, {@code false} to deselect it
     */
    public void setRsiSelected(boolean rsiSelected) {
        this.rsiSelected = rsiSelected;
    }

    /**
     * Checks if the price history is selected for the chart.
     *
     * @return {@code true} if price history is selected, {@code false} otherwise
     */
    public boolean isPriceHistorySelected() {
        return priceHistorySelected;
    }

    /**
     * Sets the price history selection state for the chart.
     *
     * @param priceHistorySelected {@code true} to select price history, {@code false} to deselect it
     */
    public void setPriceHistorySelected(boolean priceHistorySelected) {
        this.priceHistorySelected = priceHistorySelected;
    }
}