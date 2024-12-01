package use_case.chart;

import data_access.StockDataAccess;

import entity.ChartInfo;
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
        ChartInfo chartInfo = new ChartInfo(ticker,price,SMA,EMA,RSI);
        ChartOutputData outputData = new ChartOutputData(chartInfo);
        chartPresenter.presentChartData(outputData);
    }

}