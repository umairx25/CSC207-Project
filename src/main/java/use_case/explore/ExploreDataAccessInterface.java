package use_case.explore;

import java.util.List;

/**
 * Interface defining the methods for interacting with data sources for the Explore feature.
 */
public interface ExploreDataAccessInterface {

    /**
     * Searches for companies based on the provided parameters.
     *
     * @param ticker   the ticker symbol of the company
     * @param exchange the stock exchange identifier
     * @param keyword  the keyword for the search
     * @return the raw JSON string containing search results
     */
    String searchCompany(String ticker, String exchange, String keyword);

    /**
     * Extracts a list of company tickers from the raw JSON data.
     *
     * @param jsonData the raw JSON data
     * @return a list of ticker symbols
     */
    List<String> extractCompanyTickers(String jsonData);

    /**
     * Retrieves a list of all known stock exchanges.
     *
     * @return a list of exchange identifiers
     */
    List<String> getAllExchanges();

    /**
     * Fetches the name of the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the name of the company
     * @throws Exception if an error occurs while retrieving the company name
     */
    String getTickerName(String ticker) throws Exception;

    /**
     * Fetches the description of the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the company description
     * @throws Exception if an error occurs while retrieving the description
     */
    String getDesc(String ticker) throws Exception;

    /**
     * Fetches the primary stock exchange for the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the primary exchange of the company
     * @throws Exception if an error occurs while retrieving the primary exchange
     */
    String getPrimaryExchange(String ticker) throws Exception;

    /**
     * Fetches the market capitalization of the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the market cap of the company
     * @throws Exception if an error occurs while retrieving the market cap
     */
    String getMarketCap(String ticker) throws Exception;

    /**
     * Fetches the opening price for the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the opening price of the company
     */
    String getOpen(String ticker);

    /**
     * Fetches the high and low stock prices for the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return a list containing the high and low prices
     * @throws Exception if an error occurs while retrieving the high/low prices
     */
    List<String> getHighLow(String ticker) throws Exception;

    /**
     * Fetches the webpage URL for the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the company's webpage URL
     * @throws Exception if an error occurs while retrieving the webpage URL
     */
    String getWebpage(String ticker) throws Exception;

    /**
     * Fetches the location of the company associated with the given ticker symbol.
     *
     * @param ticker the ticker symbol of the company
     * @return the location of the company
     * @throws Exception if an error occurs while retrieving the location
     */
    String getLocation(String ticker) throws Exception;

    /**
     * Calculates the average trading volume for the company associated with the given ticker symbol
     * over the past 30 days.
     *
     * @param ticker the ticker symbol of the company
     * @return the average trading volume
     */
    String calculateAverageVolume(String ticker);
}
