package interface_adapter.chart;

import interface_adapter.ViewModel;

import java.util.LinkedHashMap;

/**
 * The ViewModel for the Chart View.
 * This class manages the chart data and updates the corresponding state for the view.
 */
public class ChartViewModel extends ViewModel<ChartState> {

    private double currPrice;
    private String pointIncrease;
    private String percentIncrease;
    public static final String CONTROL_PANEL_MESSAGE = "(Data represents the last 4 years)";

    /**
     * Constructs a new ChartViewModel and initializes the state.
     * Sets the state to a new instance of ChartState.
     */
    public ChartViewModel() {
        super("chart");
        setState(new ChartState());
    }

    /**
     * Updates the title of the chart in the state.
     *
     * @param title The new title for the chart.
     */
    public void updateTitle(String title) {
        getState().setTitle(title);
        firePropertyChanged("title");
    }

    /**
     * Updates the price history in the state.
     *
     * @param priceHistory The new price history as a LinkedHashMap.
     */
    public void updatePriceHistory(LinkedHashMap<Long, Double> priceHistory) {
        getState().setPriceHistory(priceHistory);
        firePropertyChanged("priceHistory");
    }

    /**
     * Updates the Simple Moving Average (SMA) data in the state.
     *
     * @param sma The new SMA data as a LinkedHashMap.
     */
    public void updateSma(LinkedHashMap<Long, Double> sma) {
        getState().setSma(sma);
        firePropertyChanged("sma");
    }

    /**
     * Updates the Exponential Moving Average (EMA) data in the state.
     *
     * @param ema The new EMA data as a LinkedHashMap.
     */
    public void updateEma(LinkedHashMap<Long, Double> ema) {
        getState().setEma(ema);
        firePropertyChanged("ema");
    }

    /**
     * Updates the Relative Strength Index (RSI) data in the state.
     *
     * @param rsi The new RSI data as a LinkedHashMap.
     */
    public void updateRsi(LinkedHashMap<Long, Double> rsi) {
        getState().setRsi(rsi);
        firePropertyChanged("rsi");
    }

    /**
     * Updates the current price.
     *
     * @param currPrice The new current price of the stock.
     */
    public void updateCurrPrice(double currPrice) {
        this.currPrice = currPrice;
    }

    /**
     * Updates the point increase (price change) in the state.
     *
     * @param pointIncrease The new point increase.
     */
    public void updatePointIncrease(String pointIncrease) {
        this.pointIncrease = pointIncrease;
    }

    /**
     * Updates the percent increase (percentage change) in the state.
     *
     * @param percentIncrease The new percent increase.
     */
    public void updatePercentIncrease(String percentIncrease) {
        this.percentIncrease = percentIncrease;
    }

    /**
     * Gets the current price.
     *
     * @return The current price of the stock.
     */
    public double getCurrPrice() {
        return currPrice;
    }

    /**
     * Gets the point increase (price change).
     *
     * @return The point increase.
     */
    public String getPointIncrease() {
        return pointIncrease;
    }

    /**
     * Gets the percent increase (percentage change).
     *
     * @return The percent increase.
     */
    public String getPercentIncrease() {
        return percentIncrease;
    }
}