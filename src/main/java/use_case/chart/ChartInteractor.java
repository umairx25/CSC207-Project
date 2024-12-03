package use_case.chart;

import data_access.StockDataAccess;
import entity.ChartInfo;
import interface_adapter.chart.ChartPresenter;

import java.util.LinkedHashMap;

/**
 * Interactor for the Chart Use Case.
 * This class handles the interaction between the data layer and the presentation layer
 * for fetching and processing chart data.
 */
public class ChartInteractor implements ChartInputBoundary {

    private final StockDataAccess stockDataAccess;
    private final ChartPresenter chartPresenter;

    /**
     * Constructs a new ChartInteractor with the given data access and presenter.
     *
     * @param stockDataAccess The data access layer for fetching stock data.
     * @param chartPresenter  The presenter for updating the view with chart data.
     */
    public ChartInteractor(StockDataAccess stockDataAccess, ChartPresenter chartPresenter) {
        this.stockDataAccess = stockDataAccess;
        this.chartPresenter = chartPresenter;
    }

    /**
     * Fetches chart data based on the input parameters and presents it to the view.
     *
     * This method retrieves the historical stock price data, indicators (SMA, EMA, RSI),
     * current price, and price changes for a specific ticker symbol, and passes this data
     * to the presenter for display.
     *
     * @param chartInputData The input data containing the parameters to fetch chart data.
     */
    @Override
    public void fetchChartData(ChartInputData chartInputData) {
        // Extract ticker symbol from input data
        String ticker = chartInputData.getTicker();

        // Fetch the historical price data, indicators, and current price
        LinkedHashMap<Long, Double> price = stockDataAccess.getHistoricalData(ticker);
        LinkedHashMap<Long, Double> SMA = stockDataAccess.getIndicatorData("sma", ticker, 10);
        LinkedHashMap<Long, Double> EMA = stockDataAccess.getIndicatorData("ema", ticker, 10);
        LinkedHashMap<Long, Double> RSI = stockDataAccess.getIndicatorData("rsi", ticker, 10);
        double currPrice = stockDataAccess.getCurrentPrice(ticker);
        double pointIncrease = stockDataAccess.getPriceIncrease(ticker).get(1);
        double percentIncrease = stockDataAccess.getPriceIncrease(ticker).get(0);

        // Create a ChartInfo object with all the retrieved data
        ChartInfo chartInfo = new ChartInfo(ticker, price, SMA, EMA, RSI);

        // Create an output data object containing the chart info and other relevant data
        ChartOutputData outputData = new ChartOutputData(chartInfo, currPrice, pointIncrease, percentIncrease);

        // Pass the output data to the presenter for display
        chartPresenter.presentChartData(outputData);
    }
}