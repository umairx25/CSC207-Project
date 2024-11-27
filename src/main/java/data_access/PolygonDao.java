package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.explore.ExploreDataAccessInterface;


public class PolygonDao implements ExploreDataAccessInterface {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
//        String jsonResponse = getTickerSnapshot("AAXN");
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

    //Ticker Information
    //if limit is left empty the api will default it to 100
    //you can search by either the ticker or exchange name (mic codes only) or keyword but only one at a time!
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


    public List<String> extractCompanyTickers(String jsonData) {
        List<String> tickers = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject company = results.getJSONObject(i);
            String ticker = company.getString("ticker");
            tickers.add(ticker);
        }

        // Remove duplicates
        LinkedHashSet<String> cleanSet = new LinkedHashSet<>(tickers);

        return new ArrayList<>(cleanSet);
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

        String result = getAggregateData(ticker, multiplier, timespan, from, to);
        JSONObject jsonResponse = new JSONObject(result);

        if (jsonResponse.has("results")) {
            return jsonResponse.getJSONArray("results");
        } else {
            return new JSONArray();
        }
    }

    public String calculateAverageVolume(String ticker) {
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

    public List<String> getAllExchanges() {
        List<String> exchanges = new ArrayList<>();
        String urlString = "https://api.polygon.io/v3/reference/exchanges?asset_class=stocks&apiKey=tRolQKcnnsS0ASS2_TFAZfjEjqHclpxU";

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


    //All the methods that use ticker snapshot endpoint
    public static String getTickerSnapshot(String ticker)   {
        String baseURL = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

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

    public List<String> getHighLow(String ticker) throws Exception {
        String result = getTickerSnapshot(ticker);
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

    //All the methods that use company overview endpoint
    public static String getCompanyOverview(String ticker) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    public String getPrimaryExchange(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("primary_exchange");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getMarketCap(String ticker) throws Exception {
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

    public String getTickerName(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");
        return resultsObject.getString("name");
    }
    public String getDesc(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("description");
        } catch (Exception e) {
            return "";
        }
    }

    public String getWebpage(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("homepage_url");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getLocation(String ticker) throws Exception {
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

    public static LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate) {
        LinkedHashMap<Long, Double> historicalData = new LinkedHashMap<>();
        String content = getAggregateData(ticker, 1,timespan, startDate, endDate);
        JSONObject jsonObject = new JSONObject(content);
        System.out.println("Fetching historical data for " + ticker);

        if (jsonObject.has("results")) {
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject dailyData = results.getJSONObject(i);
                Long date = dailyData.getLong("t");
                double closePrice = dailyData.getDouble("c");
                historicalData.put(date, closePrice);
            }
        } else {
            System.out.println("No 'results' found in response. Full response: " + content);
        }
        return historicalData;
    }


    @NotNull
    private static LinkedHashMap<Long, Double> getIndicator(String indicator, String ticker, String timespan, String from, String to, int window) {
        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();

        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/%s/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                indicator, ticker, timespan, from, to, window, API_KEY
        );

        String content = HTTPRequest(urlString);
        JSONObject jsonResponse = new JSONObject(content);

        JSONObject result;
        try {
            result = jsonResponse.getJSONObject("results");
        } catch (Exception e) {
            System.out.println("No 'results' found in response.");
            result = new JSONObject();
        }

        JSONArray values = result.getJSONArray("values");

        for (int i = values.length() - 1; i >= 0; i--) {
            JSONObject obj = values.getJSONObject(i);
            data.put(obj.getLong("timestamp"), obj.getDouble("value"));
        }
        return data;
    }

    public static Double currPrice(String ticker) {
        String content = getTickerSnapshot(ticker);

        JSONObject jsonResponse = new JSONObject(content);
        JSONObject day = jsonResponse.getJSONObject("ticker").getJSONObject("day");
        return round(day.getDouble("c"), 4);
    }

    //convert to a list of strings
    public static ArrayList<Double> increase(String ticker) {
        String content = getTickerSnapshot(ticker);
        ArrayList<Double> increase = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(content);
        JSONObject tickerData= jsonResponse.getJSONObject("ticker");
        increase.add(round(tickerData.getDouble("todaysChangePerc"), 4));
        increase.add(round(tickerData.getDouble("todaysChange"), 4));
        return increase;
    }

    public static Double round(double value, int places) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(places));
        return Double.parseDouble(df.format(value));
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
