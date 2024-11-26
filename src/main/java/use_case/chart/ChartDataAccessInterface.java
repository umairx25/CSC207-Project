//ChartDataAccessInterface
package use_case.chart;

import java.util.LinkedHashMap;
import java.util.List;

public interface ChartDataAccessInterface {
    LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate);

    LinkedHashMap<Long, Double> getIndicatorData(String type, String ticker, String timespan, String from, String to, int window);

    String getTickerName(String ticker);

    Double getCurrentPrice(String ticker);

    List<Double> getPriceIncrease(String ticker);
}