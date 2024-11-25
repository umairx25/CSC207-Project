package interface_adapter.chart;

import interface_adapter.ViewModel;

import java.util.LinkedHashMap;

/**
 * The ViewModel for the Chart View.
 */
public class ChartViewModel extends ViewModel<ChartState> {

    public static final String TITLE_LABEL = "Chart Label";
    public static final String PRICE = "Price History";
    public static final String SMA = "Simple Moving Average (SMA)";
    public static final String EMA = "Exponential Moving Average (EMA)";
    public static final String RSI = "Relative Strength Index (RSI)";
    public static final String DAY = "Day";
    public static final String MONTH = "Month";
    public static final String YEAR = "Year";

    public ChartViewModel() {
        super("chart");
        setState(new ChartState());
    }

    /**
     * Updates the title of the chart.
     *
     * @param title The new title.
     */
    public void updateTitle(String title) {
        getState().setTitle(title);
        firePropertyChanged("title");
    }

    /**
     * Updates the price history of the chart.
     *
     * @param priceHistory The new price history as a LinkedHashMap.
     */
    public void updatePriceHistory(LinkedHashMap<Long, Double> priceHistory) {
        getState().setPriceHistory(priceHistory);
        firePropertyChanged("priceHistory");
    }

    /**
     * Updates the SMA (Simple Moving Average) data.
     *
     * @param sma The new SMA data as a LinkedHashMap.
     */
    public void updateSma(LinkedHashMap<Long, Double> sma) {
        getState().setSma(sma);
        firePropertyChanged("sma");
    }

    /**
     * Updates the EMA (Exponential Moving Average) data.
     *
     * @param ema The new EMA data as a LinkedHashMap.
     */
    public void updateEma(LinkedHashMap<Long, Double> ema) {
        getState().setEma(ema);
        firePropertyChanged("ema");
    }

    /**
     * Updates the RSI (Relative Strength Index) data.
     *
     * @param rsi The new RSI data as a LinkedHashMap.
     */
    public void updateRsi(LinkedHashMap<Long, Double> rsi) {
        getState().setRsi(rsi);
        firePropertyChanged("rsi");
    }

    /**
     * Updates the time period of the chart (e.g., Day, Month, Year).
     *
     * @param timePeriod The new time period.
     */
    public void updateTimePeriod(String timePeriod) {
        getState().setTimePeriod(timePeriod);
        firePropertyChanged("timePeriod");
    }

}