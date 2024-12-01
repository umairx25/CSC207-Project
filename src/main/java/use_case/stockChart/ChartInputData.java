package use_case.stockChart;

public class ChartInputData {
    private final String ticker;
    private final String timespan;
    private final boolean includePriceHistory;
    private final boolean includeSMA;
    private final boolean includeEMA;
    private final boolean includeRSI;

    public ChartInputData(String ticker, String timespan, boolean includePriceHistory, boolean includeSMA, boolean includeEMA, boolean includeRSI) {
        this.ticker = ticker;
        this.timespan = timespan;
        this.includePriceHistory = includePriceHistory;
        this.includeSMA = includeSMA;
        this.includeEMA = includeEMA;
        this.includeRSI = includeRSI;
    }

    public String getTicker() {
        return ticker;
    }

    public String getTimespan() {
        return timespan;
    }

    public boolean isIncludePriceHistory() {
        return includePriceHistory;
    }

    public boolean isIncludeSMA() {
        return includeSMA;
    }

    public boolean isIncludeEMA() {
        return includeEMA;
    }

    public boolean isIncludeRSI() {
        return includeRSI;
    }
}
