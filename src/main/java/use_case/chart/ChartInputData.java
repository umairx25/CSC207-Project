package use_case.chart;

public class ChartInputData {
    private final String ticker;
    private final String timespan;
    private final String startDate;
    private final String endDate;
    private final boolean includeSMA;
    private final boolean includeEMA;
    private final boolean includeRSI;
    private final boolean includePrice;

    public ChartInputData(String ticker, String timespan, String startDate, String endDate,
                          boolean includeSMA, boolean includeEMA, boolean includeRSI, boolean includePrice) {
        this.ticker = ticker;
        this.timespan = timespan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.includeSMA = includeSMA;
        this.includeEMA = includeEMA;
        this.includeRSI = includeRSI;
        this.includePrice = includePrice;
    }

    public String getTicker() {
        return ticker;
    }

}

