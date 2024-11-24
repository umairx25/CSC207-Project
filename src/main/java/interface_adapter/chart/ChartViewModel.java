package interface_adapter.chart;

import java.util.LinkedHashMap;

public class ChartViewModel {

    private LinkedHashMap<Long, Double> priceHistory;
    private LinkedHashMap<Long, Double> smaData;
    private LinkedHashMap<Long, Double> emaData;
    private LinkedHashMap<Long, Double> rsiData;

    public void updateChart(LinkedHashMap<Long, Double> priceHistory, LinkedHashMap<Long, Double> smaData,
                            LinkedHashMap<Long, Double> emaData, LinkedHashMap<Long, Double> rsiData) {
        this.priceHistory = priceHistory;
        this.smaData = smaData;
        this.emaData = emaData;
        this.rsiData = rsiData;
    }

    public LinkedHashMap<Long, Double> getPriceHistory() {
        return priceHistory;
    }

    public LinkedHashMap<Long, Double> getSmaData() {
        return smaData;
    }

    public LinkedHashMap<Long, Double> getEmaData() {
        return emaData;
    }

    public LinkedHashMap<Long, Double> getRsiData() {
        return rsiData;
    }
}