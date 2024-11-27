package entity;

public class Stock {
    private String ticker;
    private String name;
    private String description;
    private String primaryExchange;
    private String marketCap;
    private String open;
    private String high;
    private String low;
    private String volume;
    private String location;
    private String webpage;

    public Stock(String ticker, String name, String description, String primaryExchange, String marketCap, String open,
                 String high, String low, String volume, String location, String webpage) {
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.primaryExchange = primaryExchange;
        this.marketCap = marketCap;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.location = location;
        this.webpage = webpage;
    }

    // Getters and setters
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }
}
