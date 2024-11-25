package interface_adapter.chart;

import java.util.LinkedHashMap;

public class ChartState {
    private String title;
    private LinkedHashMap<Long, Double> priceHistory;
    private LinkedHashMap<Long, Double> sma;
    private LinkedHashMap<Long, Double> ema;
    private LinkedHashMap<Long, Double> rsi;

    public boolean isPriceHistoryselected() {
        return priceHistoryselected;
    }

    public boolean isSmaselected() {
        return smaselected;
    }

    public boolean isEmaselected() {
        return emaselected;
    }

    public boolean isRsiselected() {
        return rsiselected;
    }

    private boolean priceHistoryselected;

    public void setSmaselected(boolean smaselected) {
        this.smaselected = smaselected;
    }

    public void setPriceHistoryselected(boolean priceHistoryselected) {
        this.priceHistoryselected = priceHistoryselected;
    }

    public void setEmaselected(boolean emaselected) {
        this.emaselected = emaselected;
    }

    public void setRsiselected(boolean rsiselected) {
        this.rsiselected = rsiselected;
    }

    private boolean smaselected;
    private boolean emaselected;
    private boolean rsiselected;
    private String timePeriod;

    // Getters and setters for all fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedHashMap<Long, Double> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(LinkedHashMap<Long, Double> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public LinkedHashMap<Long, Double> getSma() {
        return sma;
    }

    public void setSma(LinkedHashMap<Long, Double> sma) {
        this.sma = sma;
    }

    public LinkedHashMap<Long, Double> getEma() {
        return ema;
    }

    public void setEma(LinkedHashMap<Long, Double> ema) {
        this.ema = ema;
    }

    public LinkedHashMap<Long, Double> getRsi() {
        return rsi;
    }

    public void setRsi(LinkedHashMap<Long, Double> rsi) {
        this.rsi = rsi;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }
}