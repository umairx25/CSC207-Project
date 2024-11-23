package frameworks_driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;


public class PolygonAPI {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");

    public static void main(String[] args) throws Exception {
        //Test for getAggregateData
//        String ticker = "AMZN";
//        int multiplier = 1;
//        String timespan = "";
//        String from = "2024-11-14";
//        String to = "2024-11-14";
//        String jsonResponse = getAggregateData(ticker, multiplier, timespan, from, to);

        //Test for getSMAData
//        String stockTicker = "AAPL";
//        String timespan = "day";
//        String from = "2023-01-01";
//        String to = "2023-12-31";
//        int window = 50; // SMA window size
//        String jsonResponse = getSMAData(stockTicker, timespan, from, to, window);

        //Test for getCompanyOverview
//        String ticker = "AAPL";
//        String jsonResponse = getCompanyOverview(ticker);

        //Test for getRSIData
//        String stockTicker = "MSFT";
//        String timespan = "day";
//        String from = "2017-01-01";
//        String to = "2017-01-03";
//        int window = 10; // RSI window size
//        String jsonResponse = getSMAData(stockTicker, timespan, from, to, window);

        //Test for getAllStockTypes
//        String jsonResponse = getAllStockTypes();

        //Test for getHistoricalClosingData
//        List<Double> historicalPrices = getHistoricalClosingData(ticker, multiplier, timespan, from, to);
//        System.out.println("Historical closing prices for " + ticker + ": " + historicalPrices);

        //Test for getAvgVolume
//        double averageVolume = calculateAverageVolume("META");
//        System.out.println(averageVolume);

        //Test for searchCompany
//        String jsonResponse = searchCompany("", "XNYS", ""); //searching with the exchange's MIC code ( for NASDAQ in this case)
//        String jsonResponse = searchCompany("AAPL", "", ""); //searching with just the ticker
//
//        System.out.println("Response: " +  formatJson(jsonResponse));
    }

    //Aggregate Data
    //multiplier: interval, and timespan: type of interval. So if you set multiplier to 5 and timespan
        // to minute then it will give data in 5 min intervals from start date to end date.
    //notation for dates is year-month-day, like 2024-02-14.
    public static String getAggregateData(String ticker, int multiplier, String timespan, String from, String to) {
        String baseURL = "https://api.polygon.io/v2/aggs/ticker/";
        String urlString = baseURL + ticker + "/range/" + multiplier + "/" + timespan + "/" + from + "/" + to + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getIntradayData(String ticker, int multiplier, String day) {
        String timespan = "day";
        return getAggregateData(ticker, multiplier, timespan, day, day);
    }
    public static List<Double> getHistoricalClosingData(String ticker, int multiplier, String timespan, String from, String to) {
        String urlString = "https://api.polygon.io/v2/aggs/ticker/" + ticker + "/range/" + multiplier + "/" + timespan + "/" + from + "/" + to + "?apiKey=" + API_KEY;
        List<Double> prices = new ArrayList<>();
        String results = HTTPRequest(urlString);
        JSONObject jsonResponse = new JSONObject(results);
        JSONArray resultsArray = jsonResponse.getJSONArray("results");
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject dataPoint = resultsArray.getJSONObject(i);
            double closePrice = dataPoint.getDouble("c"); // Closing price
            prices.add(closePrice);
        }
        return prices;
    }


    //Indicators
    //window: used to calculate the simple moving average (SMA). i.e. a window size of 10 with daily aggregates would result in a 10 day moving average.
    public static String getSMAData(String ticker, String timespan, String from, String to, int window) {
        String baseURL = "https://api.polygon.io/v1/indicators/sma/";
        String urlString = baseURL + ticker + "?timespan=" + timespan + "&from=" + from + "&to=" + to + "&window=" + window + "&limit=5000&apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getEMAData(String ticker, String timespan, String from, String to, int window) {
        String baseURL = "https://api.polygon.io/v1/indicators/ema/";
        String urlString = baseURL + ticker + "?timespan=" + timespan + "&from=" + from + "&to=" + to + "&window=" + window + "&apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getRSIData(String ticker, String timespan, String from, String to, int window) {
        String baseURL = "https://api.polygon.io/v1/indicators/rsi/";
        String urlString = baseURL + ticker + "?timespan=" + timespan + "&from=" + from + "&to=" + to + "&window=" + window + "&apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    //Ticker Information
    //if limit is left empty the api will default it to 100
    //you can search by either the ticker or exchange name (mic codes only) not both
    public static String searchCompany(String ticker, String exchange, String limit) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers";
        String urlString = "";
        if (Objects.equals(ticker, "") && !Objects.equals(exchange, "")) {
            urlString = baseURL + "?market=stocks&exchange=" + exchange + "&apiKey=" + API_KEY;
            if (!Objects.equals(limit, "")) {
                urlString = baseURL + "?market=stocks&exchange=" + exchange + "&limit=" + limit + "&apiKey=" + API_KEY;
            }
        } else if (Objects.equals(exchange, "") && !Objects.equals(ticker, "")) {
            urlString = baseURL + "?ticker=" + ticker + "&market=stocks&apiKey=" + API_KEY;
            if (!Objects.equals(limit, "")) {
                urlString = baseURL + "?ticker=" + ticker + "&market=stocks" + "&limit=" + limit + "&apiKey=" + API_KEY;
            }
        } else {
            throw new IllegalArgumentException("You must enter either a ticker or an exchange, but not both!");
        }
        return HTTPRequest(urlString);
    }
    //Search Company returns a long JSONObject, this collects all the tickers
    public static List<String> extractCompanyTickers(String jsonData) {
        List<String> tickers = new ArrayList<>();

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray results = jsonObject.getJSONArray("results");

        // Iterate through the array and collect tickers
        for (int i = 0; i < results.length(); i++) {
            JSONObject company = results.getJSONObject(i);
            tickers.add(company.getString("ticker"));
        }

        return tickers;
    }


//     Logic for getting average volume data for the last 30 days
    public static JSONArray fetchVolumeData(String ticker)  {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String from = startDate.format(formatter);
        String to = endDate.format(formatter);
        int multiplier = 1;
        String timespan = "day";

            String urlString = "https://api.polygon.io/v2/aggs/ticker/" + ticker +
                    "/range/" + multiplier + "/" + timespan + "/" + from + "/" + to + "?apiKey=" + API_KEY;

        String result = HTTPRequest(urlString);
        JSONObject jsonResponse = new JSONObject(result);

        if (jsonResponse.has("results")) {
            return jsonResponse.getJSONArray("results");
        } else {
            return new JSONArray();
        }
    }

    public static String calculateAverageVolume(String ticker) {
        JSONArray resultsArray = fetchVolumeData(ticker);

        if (resultsArray.isEmpty()) {
            return "N/A";
        }

        double totalVolume = 0;
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject dataPoint = resultsArray.getJSONObject(i);
            totalVolume += dataPoint.getDouble("v"); // Volume
        }
        return formatNumber(totalVolume / resultsArray.length());
    }

    public static String getCompanyOverview(String ticker) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getAllStockTypes() {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/types?asset_class=stocks&locale=us&apiKey=";
        String urlString = baseURL + API_KEY;
        return HTTPRequest(urlString);
    }

    public static List<String> getAllExchanges() {
        List<String> exchanges = new ArrayList<>();
        String urlString = "https://api.polygon.io/v3/reference/exchanges?asset_class=stocks&apiKey=tRolQKcnnsS0ASS2_TFAZfjEjqHclpxU";

        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject company = results.getJSONObject(i);
            if (company.has("mic")) { // Check if the "mic" key exists
                 exchanges.add(company.getString("mic"));
            }
        }

        return exchanges;
    }

    public static String getRelatedCompanies(String ticker) {
        String baseURL ="https://api.polygon.io/v1/related-companies/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getMarketCap(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            double marketCap = resultsObject.getDouble("market_cap");
            return formatNumber(marketCap);
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getOpen(String ticker) {
        String urlString = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject tickerObject = jsonObject.getJSONObject("ticker");
        JSONObject dayObject = tickerObject.getJSONObject("day");

        double open = dayObject.getDouble("o");
        if (open != 0) {
            return formatNumber(open);
        } else {
            return "Stock exchange has not yet opened.";
        }
    }
    public static List<String> getHighLow(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);

        JSONObject tickerObject;
        try {
            tickerObject = jsonObject.getJSONObject("ticker");
        } catch (Exception e){
            return Arrays.asList("N/A", "N/A");
        }

        JSONObject dayObject;
        try {
            dayObject = tickerObject.getJSONObject("day");
        } catch (Exception e){
            return Arrays.asList("N/A", "N/A");
        }

        List<String> highLow = new ArrayList<>();
        highLow.add(formatNumber(dayObject.getDouble("h")));
        highLow.add(formatNumber(dayObject.getDouble("l")));
        return highLow;
    }
    public static String getPrimaryExchange(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("primary_exchange");
        } catch (Exception e) {
            return "N/A";
        }
    }
    public static String getTickerName(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");
        return resultsObject.getString("name");
    }
    public static String getDesc(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("description");
        } catch (Exception e) {
            return "N/A";
        }
    }
    public static String getWebpage(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("homepage_url");
        } catch (Exception e) {
            return "N/A";
        }
    }
    public static String getLocation(String ticker) throws Exception {
        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
        String result = HTTPRequest(urlString);
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

    //Sends requests to the server.
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

    //Makes the json output look pretty.
    public static String formatJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(gson.fromJson(json, Object.class));
    }

    //Utility method to round numbers
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

}
