package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.explore.ExploreDataAccessInterface;

/**
 * Provides access to financial data using the Polygon API.
 * Implements methods for retrieving and processing stock and market data.
 */
public class ExploreDataAccess implements ExploreDataAccessInterface {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");


    /**
     * Retrieves aggregate data for a given stock ticker.
     *
     * @param ticker     the stock ticker
     * @param multiplier the interval multiplier
     * @param timespan   the type of interval (e.g., day, minute)
     * @param from       the start date in YYYY-MM-DD format
     * @param to         the end date in YYYY-MM-DD format
     * @return JSON response as a String
     */
    public static String getAggregateData(String ticker, int multiplier, String timespan, String from, String to) {
        String baseURL = "https://api.polygon.io/v2/aggs/ticker/";
        String urlString = baseURL + ticker + "/range/" + multiplier + "/" + timespan + "/" + from + "/" + to + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    /**
     * Searches for a company using the Polygon API.
     *
     * @param ticker   the stock ticker
     * @param exchange the exchange's MIC code
     * @param keyword  a keyword to search for
     * @return JSON response as a String
     * @throws IllegalArgumentException if more than one parameter is non-empty
     */
    @Override
    public String searchCompany(String ticker, String exchange, String keyword) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers";
        String limit = "1000";
        String urlString;

        if (!Objects.equals(ticker, "") && Objects.equals(exchange, "") && Objects.equals(keyword, "")) {
            urlString = baseURL + "?ticker=" + ticker + "&market=stocks&limit=" + limit + "&apiKey=" + API_KEY;
        } else if (Objects.equals(ticker, "") && !Objects.equals(exchange, "") && Objects.equals(keyword, "")) {
            urlString = baseURL + "?market=stocks&exchange=" + exchange + "&limit=" + limit + "&apiKey=" + API_KEY;
        } else if (Objects.equals(ticker, "") && Objects.equals(exchange, "") && !Objects.equals(keyword, "")) {
            urlString = baseURL + "?market=stocks&search=" + keyword + "&limit=" + limit + "&apiKey=" + API_KEY;
        } else {
            throw new IllegalArgumentException("You must enter either a ticker, an exchange, or a keyword, but not more than one!");
        }

        return HTTPRequest(urlString);
    }

    /**
     * Extracts company tickers from a JSON response.
     *
     * @param jsonData the JSON response
     * @return a list of unique company tickers
     */
    @Override
    public List<String> extractCompanyTickers(String jsonData) {
        List<String> tickers = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject company = results.getJSONObject(i);
            String ticker = company.getString("ticker");
            tickers.add(ticker);
        }

        return new ArrayList<>(new LinkedHashSet<>(tickers));
    }

    /**
     * Calculates the average volume for a given stock over the last 30 days.
     *
     * @param ticker the stock ticker
     * @return the average volume as a formatted String, or "N/A" if unavailable
     */
    @Override
    public String calculateAverageVolume(String ticker) {
        JSONArray resultsArray = fetchVolumeData(ticker);

        if (resultsArray.isEmpty()) {
            return "N/A";
        }

        double totalVolume = 0;
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject dataPoint = resultsArray.getJSONObject(i);
            totalVolume += dataPoint.getDouble("v");
        }
        return formatNumber(totalVolume / resultsArray.length());
    }

    /**
     * Sends an HTTP GET request and retrieves the response.
     *
     * @param urlString the URL to send the request to
     * @return the response as a String
     */
    static String HTTPRequest(String urlString) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    /**
     * Formats a number into a human-readable format (e.g., 1.2B for billions).
     *
     * @param value the number to format
     * @return the formatted number as a String
     */
    public static String formatNumber(double value) {
        if (value >= 1_000_000_000_000.0) {
            return String.format("%.1fT", value / 1_000_000_000_000.0);
        } else if (value >= 1_000_000_000.0) {
            return String.format("%.1fB", value / 1_000_000_000.0);
        } else if (value >= 1_000_000.0) {
            return String.format("%.2fM", value / 1_000_000.0);
        } else {
            return String.format("%.2f", value);
        }
    }
    /**
     * Retrieves all exchanges available in the market.
     *
     * @return a list of exchange MIC codes
     */
    @Override
    public List<String> getAllExchanges() {
        List<String> exchanges = new ArrayList<>();
        String urlString = "https://api.polygon.io/v3/reference/exchanges?asset_class=stocks&apiKey=" + API_KEY;

        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject exchange = results.getJSONObject(i);
            if (exchange.has("mic")) { // Check if the "mic" key exists
                exchanges.add(exchange.getString("mic"));
            }
        }
        return exchanges;
    }

    /**
     * Retrieves the name of a company by its ticker.
     *
     * @param ticker the stock ticker
     * @return the name of the company
     */
    @Override
    public String getTickerName(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");
        return resultsObject.getString("name");
    }

    /**
     * Retrieves the description of a company by its ticker.
     *
     * @param ticker the stock ticker
     * @return the description of the company, or an empty string if unavailable
     */
    @Override
    public String getDesc(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("description");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Retrieves the primary exchange for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return the primary exchange MIC code, or "N/A" if unavailable
     */
    @Override
    public String getPrimaryExchange(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("primary_exchange");
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Retrieves the market capitalization for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return the market capitalization, formatted as a String
     */
    @Override
    public String getMarketCap(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            double marketCap = resultsObject.getDouble("market_cap");
            return formatNumber(marketCap);
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Retrieves the opening price for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return the opening price as a String, or a message indicating the stock is closed
     */
    @Override
    public String getOpen(String ticker) {
        String result = getTickerSnapshot(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject tickerObject = jsonObject.getJSONObject("ticker");
        JSONObject dayObject = tickerObject.getJSONObject("day");

        double open = dayObject.getDouble("o");
        if (open != 0) {
            return formatNumber(open);
        } else {
            return "Stock is currently closed.";
        }
    }

    /**
     * Retrieves the high and low prices for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return a list containing the high and low prices
     */
    @Override
    public List<String> getHighLow(String ticker) {
        String result = getTickerSnapshot(ticker);
        JSONObject jsonObject = new JSONObject(result);

        JSONObject tickerObject;
        try {
            tickerObject = jsonObject.getJSONObject("ticker");
        } catch (Exception e) {
            return Arrays.asList("N/A", "N/A");
        }

        JSONObject dayObject;
        try {
            dayObject = tickerObject.getJSONObject("day");
        } catch (Exception e) {
            return Arrays.asList("N/A", "N/A");
        }

        List<String> highLow = new ArrayList<>();
        highLow.add(formatNumber(dayObject.getDouble("h")));
        highLow.add(formatNumber(dayObject.getDouble("l")));
        return highLow;
    }

    /**
     * Retrieves the website URL for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return the website URL, or "N/A" if unavailable
     */
    @Override
    public String getWebpage(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("homepage_url");
        } catch (Exception e) {
            return "N/A";
        }
    }

    /**
     * Retrieves the location (city and state) for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return the location as "City, State", or "N/A" if unavailable
     */
    @Override
    public String getLocation(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        if (!resultsObject.has("address")) {
            return "N/A";
        } else {
            JSONObject addressObject = resultsObject.getJSONObject("address");
            try {
                return addressObject.getString("city") + ", " + addressObject.getString("state");
            } catch (Exception e) {
                return "N/A";
            }
        }
    }
    /**
     * Retrieves the company overview for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return JSON response as a String containing company overview details
     */
    public static String getCompanyOverview(String ticker) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    /**
     * Retrieves the snapshot data for a given stock ticker.
     *
     * @param ticker the stock ticker
     * @return JSON response as a String containing ticker snapshot details
     */
    public static String getTickerSnapshot(String ticker) {
        String baseURL = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    /**
     * Fetches volume data for a given stock ticker over the last 30 days.
     *
     * @param ticker the stock ticker
     * @return JSONArray containing volume data
     */
    public static JSONArray fetchVolumeData(String ticker) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String from = startDate.format(formatter);
        String to = endDate.format(formatter);
        int multiplier = 1;
        String timespan = "day";

        String result = getAggregateData(ticker, multiplier, timespan, from, to);
        JSONObject jsonResponse = new JSONObject(result);

        if (jsonResponse.has("results")) {
            return jsonResponse.getJSONArray("results");
        } else {
            return new JSONArray();
        }
    }


    }


