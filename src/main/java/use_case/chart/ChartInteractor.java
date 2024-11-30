package use_case.chart;

import data_access.chart.StockDataAccess;

import interface_adapter.chart.ChartPresenter;

import java.util.LinkedHashMap;

public class ChartInteractor implements ChartInputBoundary {

    StockDataAccess stockDataAccess;
    ChartPresenter chartPresenter;

    public ChartInteractor(StockDataAccess stockDataAccess, ChartPresenter chartPresenter) {
        this.stockDataAccess = stockDataAccess;
        this.chartPresenter = chartPresenter;
    }


    @Override
    public void fetchChartData(ChartInputData chartInputData){
        String ticker = chartInputData.getTicker();

        LinkedHashMap<Long, Double> price = stockDataAccess.getHistoricalData(ticker);
        LinkedHashMap<Long, Double> SMA = stockDataAccess.getIndicatorData("sma", ticker, 10);
        LinkedHashMap<Long, Double> EMA = stockDataAccess.getIndicatorData("ema", ticker, 10);
        LinkedHashMap<Long, Double>RSI = stockDataAccess.getIndicatorData("rsi", ticker, 10);
        double currPrice = stockDataAccess.getCurrentPrice(ticker);
        double percentIncrease = stockDataAccess.getPriceIncrease(ticker).get(0);
        double pointIncrease = stockDataAccess.getPriceIncrease(ticker).get(1);

        ChartOutputData outputData = new ChartOutputData(price, SMA, EMA, RSI, currPrice, percentIncrease, pointIncrease);
        chartPresenter.presentChartData(outputData);
    }

}