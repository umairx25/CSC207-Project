package entity;

public class Stock {
    private final String ticker;
    private final String name;

    public Stock(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }
}