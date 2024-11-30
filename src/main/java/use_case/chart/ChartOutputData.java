//ChartOutputData
package use_case.chart;

import java.util.LinkedHashMap;

public class ChartOutputData {
    private final LinkedHashMap<Long, Double> priceHistory;
    private final LinkedHashMap<Long, Double> smaData;
    private final LinkedHashMap<Long, Double> emaData;
    private final LinkedHashMap<Long, Double> rsiData;
    private final Double currPrice;
    private final Double pointIncrease;
    private final Double percentIncrease;

    public ChartOutputData(LinkedHashMap<Long, Double> priceHistory, LinkedHashMap<Long, Double> smaData,
                           LinkedHashMap<Long, Double> emaData, LinkedHashMap<Long, Double> rsiData, Double currPrice,
                           Double pointIncrease, Double percentIncrease) {
        this.priceHistory = priceHistory;
        this.smaData = smaData;
        this.emaData = emaData;
        this.rsiData = rsiData;
        this.currPrice = currPrice;
        this.pointIncrease = pointIncrease;
        this.percentIncrease = percentIncrease;
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

    public Double getCurrPrice() {
        return currPrice;
    }

    public Double getPointIncrease() {
        return pointIncrease;
    }

    public Double getPercentIncrease() {
        return percentIncrease;
    }
}