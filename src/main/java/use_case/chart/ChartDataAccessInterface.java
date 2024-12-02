package use_case.chart;

import java.util.LinkedHashMap;
import java.util.List;

public interface ChartDataAccessInterface {
    LinkedHashMap<Long, Double> getHistoricalData(String ticker);

    LinkedHashMap<Long, Double> getIndicatorData(String type, String ticker, int window);

    Double getCurrentPrice(String ticker);

    List<Double> getPriceIncrease(String ticker);
}