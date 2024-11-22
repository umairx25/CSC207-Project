package use_case;

public class StockData {
    private double volume;
    private double open;
    private double close;
    private double high;
    private double low;
    private long timestamp;

    // Constructor
    public StockData(double volume, double open, double close, double high, double low, long timestamp) {
        this.volume = volume;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.timestamp = timestamp;
    }

    // Getters (optional, in case you need to access fields individually)
    public double getVolume() {
        return volume;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Date: " + timestamp +
                ", Volume: " + volume +
                ", Open: " + open +
                ", Close: " + close +
                ", High: " + high +
                ", Low: " + low;
    }
}