package interface_adapter.stockData;

import entity.ChartData;

public class ChartViewModel {
    private ChartData chartData;
    private String error;

    public void setChartData(ChartData data) {
        this.chartData = data;
        this.error = null;
    }

    public ChartData getChartData() {
        return chartData;
    }

    public void setError(String error) {
        this.error = error;
        this.chartData = null;
    }

    public String getError() {
        return error;
    }
}