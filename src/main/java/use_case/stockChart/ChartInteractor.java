package use_case.stockChart;

import org.jfree.data.category.DefaultCategoryDataset;

import java.util.LinkedHashMap;

public class ChartInteractor implements ChartInputBoundary {

    private final ChartOutputBoundary outputBoundary;
    private final ChartDataAccessInterface dataAccess;

    public ChartInteractor(ChartOutputBoundary outputBoundary, ChartDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }

    @Override
    public void fetchChartData(ChartInputData inputData) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String errorMessage = null;

        try {
            if (inputData.isIncludePriceHistory()) {
                LinkedHashMap<Long, Double> priceHistory = dataAccess.getHistoricalData(
                        inputData.getTicker(), inputData.getTimespan(), "2020-01-01", "2024-03-03"
                );
                for (var entry : priceHistory.entrySet()) {
                    dataset.addValue(entry.getValue(), "Price History", entry.getKey());
                }
            }

            if (inputData.isIncludeSMA()) {
                LinkedHashMap<Long, Double> smaData = dataAccess.getSMAData(
                        inputData.getTicker(), inputData.getTimespan(), "2020-01-01", "2024-03-03", 10
                );
                for (var entry : smaData.entrySet()) {
                    dataset.addValue(entry.getValue(), "SMA", entry.getKey());
                }
            }

            if (inputData.isIncludeEMA()) {
                LinkedHashMap<Long, Double> emaData = dataAccess.getEMAData(
                        inputData.getTicker(), inputData.getTimespan(), "2020-01-01", "2024-03-03", 10
                );
                for (var entry : emaData.entrySet()) {
                    dataset.addValue(entry.getValue(), "EMA", entry.getKey());
                }
            }

            if (inputData.isIncludeRSI()) {
                LinkedHashMap<Long, Double> rsiData = dataAccess.getRSIData(
                        inputData.getTicker(), inputData.getTimespan(), "2020-01-01", "2024-03-03", 10
                );
                for (var entry : rsiData.entrySet()) {
                    dataset.addValue(entry.getValue(), "RSI", entry.getKey());
                }
            }
        } catch (Exception e) {
            errorMessage = "Error fetching chart data: " + e.getMessage();
        }

        ChartOutputData outputData = new ChartOutputData(dataset, errorMessage);
        outputBoundary.presentChartData(outputData);
    }
}
