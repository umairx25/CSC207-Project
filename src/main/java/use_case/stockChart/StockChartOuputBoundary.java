package use_case.stockChart;

import entity.ChartData;

public interface StockChartOuputBoundary {
    void presentChartData(ChartData chartData);
    void presentError(String message);
}