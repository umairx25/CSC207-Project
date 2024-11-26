package interface_adapter.chart;

import java.util.LinkedHashMap;

public class ChartState {
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private String title;
    private boolean smaSelected;
    private boolean emaSelected;
    private boolean rsiSelected;
    private boolean priceHistorySelected;

    public LinkedHashMap<Long, Double> getPriceHistory() {
        return priceHistory;
    }

    public LinkedHashMap<Long, Double> getEma() {
        return ema;
    }

    public LinkedHashMap<Long, Double> getSma() {
        return sma;
    }

    public LinkedHashMap<Long, Double> getRsi() {
        return rsi;
    }

    private LinkedHashMap<Long, Double> priceHistory;

    public void setEma(LinkedHashMap<Long, Double> ema) {
        this.ema = ema;
    }

    public void setSma(LinkedHashMap<Long, Double> sma) {
        this.sma = sma;
    }

    public void setRsi(LinkedHashMap<Long, Double> rsi) {
        this.rsi = rsi;
    }

    private LinkedHashMap<Long, Double> ema;
    private LinkedHashMap<Long, Double> sma;
    private LinkedHashMap<Long, Double> rsi;

    // Getters and setters for all fields ///////Important
    public boolean isSmaSelected() {
        return smaSelected;
    }

    public void setSmaSelected(boolean smaSelected) {
        this.smaSelected = smaSelected;
    }

    public boolean isEmaSelected() {
        return emaSelected;
    }

    public void setEmaSelected(boolean emaSelected) {
        this.emaSelected = emaSelected;
    }

    public boolean isRsiSelected() {
        return rsiSelected;
    }

    public void setRsiSelected(boolean rsiSelected) {
        this.rsiSelected = rsiSelected;
    }

    public boolean isPriceHistorySelected() {
        return priceHistorySelected;
    }

    public void setPriceHistorySelected(boolean priceHistorySelected) {
        this.priceHistorySelected = priceHistorySelected;
    }

    public void setPriceHistory(LinkedHashMap<Long, Double> priceHistory) {
        this.priceHistory = priceHistory;
    }
}

