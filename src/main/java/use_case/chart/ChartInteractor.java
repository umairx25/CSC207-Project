package use_case.chart;//package use_case.chart;
//
//
//import entity.Stock;
//import interface_adapter.chart.ChartState;
//
//import java.util.LinkedHashMap;
//
//public class ChartInteractor implements ChartInputBoundary {
//
//    private final ChartDataAccessInterface dataAccess;
//    private final ChartOutputBoundary outputBoundary;
//
//    public ChartInteractor(ChartDataAccessInterface dataAccess, ChartOutputBoundary outputBoundary) {
//        this.dataAccess = dataAccess;
//        this.outputBoundary = outputBoundary;
//    }
//
//    @Override
//    public void fetchChartData(ChartInputData inputData) {
//
//        LinkedHashMap<Long, Double> priceHistory = dataAccess.getHistoricalData(
//                inputData.getTicker(),
//                inputData.getTimespan(),
//                inputData.getStartDate(),
//                inputData.getEndDate()
//        );
//
//        LinkedHashMap<Long, Double> smaData = inputData.isIncludeSMA() ?
//                dataAccess.getIndicatorData("sma", inputData.getTicker(),
//                        inputData.getTimespan(), inputData.getStartDate(),
//                        inputData.getEndDate(), 10) : new LinkedHashMap<>();
//
//        LinkedHashMap<Long, Double> emaData = inputData.isIncludeEMA() ?
//                dataAccess.getIndicatorData("ema", inputData.getTicker(),
//                        inputData.getTimespan(), inputData.getStartDate(),
//                        inputData.getEndDate(), 10) : new LinkedHashMap<>();
//
//        LinkedHashMap<Long, Double> rsiData = inputData.isIncludeRSI() ?
//                dataAccess.getIndicatorData("rsi", inputData.getTicker(),
//                        inputData.getTimespan(), inputData.getStartDate(),
//                        inputData.getEndDate(), 10) : new LinkedHashMap<>();
//
//        Stock stockData = new Stock();
//        stockData.setPriceHistory(priceHistory);
//        stockData.setSma(smaData);
//        stockData.setEma(emaData);
//        stockData.setRsi(rsiData);
//
//        ChartOutputData outputData = new ChartOutputData(priceHistory, smaData, emaData, rsiData);
//        outputBoundary.presentChartData(outputData);
//    }
//
//    @Override
//    public void updateGraphState(ChartInputData inputData) {
//        System.out.println("Interactor: " + inputData.getCheckboxName() + " checkbox state changed to " + inputData.isChecked());
//        // Perform business logic, e.g., validation
//        ChartState chartState = new ChartState();
//        switch (inputData.getCheckboxName()) {
//            case "SMA":
//                chartState.setSmaSelected(inputData.isChecked());
//                break;
//            case "EMA":
//                chartState.setEmaSelected(inputData.isChecked());
//                break;
//            case "RSI":
//                chartState.setRsiSelected(inputData.isChecked());
//                break;
//            case "Price History":
//                chartState.setPriceHistorySelected(inputData.isChecked());
//                break;
//        }
//
//        // Create output data and present it
//        UpdateChartResponseModel responseModel = new UpdateChartResponseModel(chartState);
//        outputBoundary.presentUpdatedChart(responseModel);
//    }
//
//
//}

//ChartInteractor
import data_access.StockDataAccess;
import entity.Stock;
import java.time.LocalDate;
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

        boolean showPrice = chartInputData.isIncludePrice();
        boolean showSMA = chartInputData.isIncludeSMA();
        boolean showEMA = chartInputData.isIncludeEMA();
        boolean showRSI = chartInputData.isIncludeRSI();
        String ticker = chartInputData.getTicker();
        String startDate = LocalDate.now().minusYears(5).toString();
        String endDate = LocalDate.now().toString();


        LinkedHashMap<Long, Double> price = stockDataAccess.getHistoricalData(ticker, "week", startDate, endDate);
        LinkedHashMap<Long, Double> SMA = stockDataAccess.getIndicatorData("sma", ticker, "week", startDate, endDate, 10);
        LinkedHashMap<Long, Double> EMA = stockDataAccess.getIndicatorData("ema", ticker, "week", startDate, endDate, 10);
        LinkedHashMap<Long, Double>RSI = stockDataAccess.getIndicatorData("rsi", ticker, "week", startDate, endDate, 10);
        ChartOutputData outputData = new ChartOutputData(price, SMA, EMA, RSI);
        chartPresenter.presentChartData(outputData);
    }

}