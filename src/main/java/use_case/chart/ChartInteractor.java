package use_case.chart;


import java.util.LinkedHashMap;

public class ChartInteractor implements ChartInputBoundary {

    private final ChartDataAccessInterface dataAccess;
    private final ChartOutputBoundary outputBoundary;

    public ChartInteractor(ChartDataAccessInterface dataAccess, ChartOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void fetchChartData(ChartInputData inputData) {
        LinkedHashMap<Long, Double> priceHistory = dataAccess.getHistoricalData(
                inputData.getTicker(),
                inputData.getTimespan(),
                inputData.getStartDate(),
                inputData.getEndDate()
        );

        LinkedHashMap<Long, Double> smaData = inputData.isIncludeSMA() ?
                dataAccess.getIndicatorData("sma", inputData.getTicker(),
                        inputData.getTimespan(), inputData.getStartDate(),
                        inputData.getEndDate(), 10) : new LinkedHashMap<>();

        LinkedHashMap<Long, Double> emaData = inputData.isIncludeEMA() ?
                dataAccess.getIndicatorData("ema", inputData.getTicker(),
                        inputData.getTimespan(), inputData.getStartDate(),
                        inputData.getEndDate(), 10) : new LinkedHashMap<>();

        LinkedHashMap<Long, Double> rsiData = inputData.isIncludeRSI() ?
                dataAccess.getIndicatorData("rsi", inputData.getTicker(),
                        inputData.getTimespan(), inputData.getStartDate(),
                        inputData.getEndDate(), 10) : new LinkedHashMap<>();

        System.out.println("Interactor: " + smaData);

        ChartOutputData outputData = new ChartOutputData(priceHistory, smaData, emaData, rsiData);
        outputBoundary.presentChartData(outputData);
    }
}