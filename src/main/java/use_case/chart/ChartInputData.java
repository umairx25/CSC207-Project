package use_case.chart;

public class ChartInputData {
    private final String ticker;
    private final String timespan;
    private final String startDate;
    private final String endDate;
    private final boolean includeSMA;
    private final boolean includeEMA;
    private final boolean includeRSI;

    public ChartInputData(String ticker, String timespan, String startDate, String endDate,
                          boolean includeSMA, boolean includeEMA, boolean includeRSI) {
        this.ticker = ticker;
        this.timespan = timespan;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
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