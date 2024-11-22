package data_access;

import entity.ChartData;
import java.util.List;

public interface StockDataAccessInterface {
    ChartData fetchHistoricalData(String ticker, String timespan, String startDate, String endDate);
    ChartData fetchSMAData(String ticker, String timespan, String startDate, String endDate, int window);
    ChartData fetchEMAData(String ticker, String timespan, String startDate, String endDate, int window);
    ChartData fetchRSIData(String ticker, String timespan, String startDate, String endDate, int window);
    List<String> fetchTop100Tickers();
    Double fetchCurrentPrice(String ticker);
    List<Double> fetchPriceIncrease(String ticker);
}