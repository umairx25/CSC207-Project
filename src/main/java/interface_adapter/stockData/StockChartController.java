package interface_adapter.stockData;

import use_case.stockChart.ChartDataInteractor;

public class StockChartController {
    private final ChartDataInteractor interactor;

    public StockChartController(ChartDataInteractor interactor) {
        this.interactor = interactor;
    }

    public void fetchPriceHistory(String ticker, String interval, String startDate, String endDate) {
        interactor.getPriceHistory(ticker, interval, startDate, endDate);
    }

    public void fetchSMA(String ticker, String interval, String startDate, String endDate, int window) {
        interactor.getSMA(ticker, interval, startDate, endDate, window);
    }

    public void fetchEMA(String ticker, String interval, String startDate, String endDate, int window) {
        interactor.getEMA(ticker, interval, startDate, endDate, window);
    }

    public void fetchRSI(String ticker, String interval, String startDate, String endDate, int window) {
        interactor.getRSI(ticker, interval, startDate, endDate, window);
    }
}