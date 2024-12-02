package entity;

import java.util.LinkedHashMap;

public class ChartInfo {
    private String ticker;
    private LinkedHashMap<Long, Double> priceHistory;
    private LinkedHashMap<Long, Double> sma;
    private LinkedHashMap<Long, Double> ema;
    private LinkedHashMap<Long, Double> rsi;

    public ChartInfo(String ticker, LinkedHashMap<Long, Double> priceHistory, LinkedHashMap<Long, Double> sma,
                 LinkedHashMap<Long, Double> ema, LinkedHashMap<Long, Double> rsi) {
        this.ticker = ticker;
        this.priceHistory = priceHistory;
        this.sma = sma;
        this.ema = ema;
        this.rsi = rsi;
    }

    public String getTicker() {
        return ticker;
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
}