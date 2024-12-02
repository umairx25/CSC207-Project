//ChartOutputData
package use_case.chart;
import entity.ChartInfo;

import java.util.LinkedHashMap;

public class ChartOutputData {
    private final ChartInfo chartInfo;
    private final Double currPrice;
    private final Double pointIncrease;
    private final Double percentIncrease;

    public ChartOutputData(ChartInfo chartInfo, Double currPrice,
                           Double pointIncrease, Double percentIncrease) {
        this.chartInfo = chartInfo;
        this.currPrice = currPrice;
        this.pointIncrease = pointIncrease;
        this.percentIncrease = percentIncrease;
    }

    public LinkedHashMap<Long, Double> getPriceHistory() {
        return chartInfo.getPriceHistory();
    }

    public LinkedHashMap<Long, Double> getSmaData() {
        return chartInfo.getSma();
    }

    public LinkedHashMap<Long, Double> getEmaData() {
        return chartInfo.getEma();
    }

    public LinkedHashMap<Long, Double> getRsiData() {
        return chartInfo.getRsi();
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