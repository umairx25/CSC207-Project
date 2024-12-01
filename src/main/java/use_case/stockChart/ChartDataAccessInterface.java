package use_case.stockChart;

import java.util.LinkedHashMap;
import java.util.List;

public interface ChartDataAccessInterface {
    LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate);

    LinkedHashMap<Long, Double> getSMAData(String ticker, String timespan, String from, String to, int window);

    LinkedHashMap<Long, Double> getEMAData(String ticker, String timespan, String from, String to, int window);

    LinkedHashMap<Long, Double> getRSIData(String ticker, String timespan, String from, String to, int window);

    Double getCurrentPrice(String ticker);

    List<String> getTopTickers();
}
