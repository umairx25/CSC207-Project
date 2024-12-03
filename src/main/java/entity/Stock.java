package entity;

/**
 * Represents a stock entity with detailed information including its ticker,
 * name, description, market data, and location details.
 */
public class Stock {
    private String ticker;
    private String name;
    private final String description;
    private final String primaryExchange;
    private final String marketCap;
    private final String open;
    private final String high;
    private final String low;
    private final String volume;
    private final String location;
    private final String webpage;

    /**
     * Constructs a new Stock object.
     *
     * @param ticker          the stock ticker symbol
     * @param name            the name of the company
     * @param description     the description of the company
     * @param primaryExchange the primary exchange where the stock is listed
     * @param marketCap       the market capitalization of the company
     * @param open            the stock's opening price
     * @param high            the stock's highest price of the day
     * @param low             the stock's lowest price of the day
     * @param volume          the trading volume of the stock
     * @param location        the location of the company (city, state)
     * @param webpage         the company's official webpage
     */
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

    /**
     * @return the stock ticker symbol
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Sets the stock ticker symbol.
     *
     * @param ticker the stock ticker symbol
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the name of the company
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description of the company
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the primary exchange where the stock is listed
     */
    public String getPrimaryExchange() {
        return primaryExchange;
    }

    /**
     * @return the market capitalization of the company
     */
    public String getMarketCap() {
        return marketCap;
    }

    /**
     * @return the stock's opening price
     */
    public String getOpen() {
        return open;
    }

    /**
     * @return the stock's highest price of the day
     */
    public String getHigh() {
        return high;
    }

    /**
     * @return the stock's lowest price of the day
     */
    public String getLow() {
        return low;
    }

    /**
     * @return the trading volume of the stock
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @return the location of the company (city, state)
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the company's official webpage
     */
    public String getWebpage() {
        return webpage;
    }
}
