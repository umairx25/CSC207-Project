package use_case.stockChart;

import data_access.StockDataAccess;
import entity.ChartData;

public class ChartDataInteractor {
    private final StockDataAccess information;
    private final StockChartOuputBoundary outputBoundary;

    public ChartDataInteractor(StockDataAccess information, StockChartOuputBoundary outputBoundary) {
        this.information = information;
        this.outputBoundary = outputBoundary;
    }

    public void getPriceHistory(String ticker, String interval, String startDate, String endDate) {
        try {
            ChartData data = information.fetchHistoricalData(ticker, interval, startDate, endDate);
            outputBoundary.presentChartData(data);
        } catch (Exception e) {
            outputBoundary.presentError("Failed to fetch price history: " + e.getMessage());
        }
    }

    public void getSMA(String ticker, String interval, String startDate, String endDate, int window) {
        try {
            ChartData data = information.fetchSMAData(ticker, interval, startDate, endDate, window);
            outputBoundary.presentChartData(data);
        } catch (Exception e) {
            outputBoundary.presentError("Failed to fetch SMA data: " + e.getMessage());
        }
    }

    public void getEMA(String ticker, String interval, String startDate, String endDate, int window) {
        try {
            ChartData data = information.fetchEMAData(ticker, interval, startDate, endDate, window);
            outputBoundary.presentChartData(data);
        } catch (Exception e) {
            outputBoundary.presentError("Failed to fetch EMA data: " + e.getMessage());
        }
    }

    public void getRSI(String ticker, String interval, String startDate, String endDate, int window) {
        try {
            ChartData data = information.fetchRSIData(ticker, interval, startDate, endDate, window);
            outputBoundary.presentChartData(data);
        } catch (Exception e) {
            outputBoundary.presentError("Failed to fetch RSI data: " + e.getMessage());
        }
    }
}