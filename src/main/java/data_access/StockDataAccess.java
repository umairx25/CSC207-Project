package data_access;

import entity.ChartData;
import java.util.LinkedHashMap;
import java.util.List;

public class StockDataAccess implements StockDataAccessInterface {

    @Override
    public ChartData fetchHistoricalData(String ticker, String timespan, String startDate, String endDate) {
        LinkedHashMap<Long, Double> data = StockHelper.getHistoricalData(ticker, timespan, startDate, endDate);
        return new ChartData(data);
    }

    @Override
    public ChartData fetchSMAData(String ticker, String timespan, String startDate, String endDate, int window) {
        LinkedHashMap<Long, Double> data = StockHelper.getSMAData(ticker, timespan, startDate, endDate, window);
        return new ChartData(data);
    }

    @Override
    public ChartData fetchEMAData(String ticker, String timespan, String startDate, String endDate, int window) {
        LinkedHashMap<Long, Double> data = StockHelper.getEMAData(ticker, timespan, startDate, endDate, window);
        return new ChartData(data);
    }

    @Override
    public ChartData fetchRSIData(String ticker, String timespan, String startDate, String endDate, int window) {
        LinkedHashMap<Long, Double> data = StockHelper.getRSIData(ticker, timespan, startDate, endDate, window);
        return new ChartData(data);
    }

    @Override
    public List<String> fetchTop100Tickers() {
        return StockHelper.getTop100Tickers();
    }

    @Override
    public Double fetchCurrentPrice(String ticker) {
        return StockHelper.currPrice(ticker);
    }

    @Override
    public List<Double> fetchPriceIncrease(String ticker) {
        return StockHelper.increase(ticker);
    }
}
