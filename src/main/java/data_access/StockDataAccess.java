package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chart.ChartDataAccessInterface;


public class StockDataAccess implements ChartDataAccessInterface {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    //Ticker Information: if limit is left empty the api will default it to 100
    //you can search by either the ticker or exchange name (mic codes only) or keyword but only one at a time!
    public static String searchCompany(String ticker, String exchange, String keyword) {
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
            throw new IllegalArgumentException("You must enter either a ticker, an exchange, or a keyword, but not " +
                    "more than one!");
        }

        return HTTPRequest(urlString);
    }

    public static List<String> extractCompanyTickers(String jsonData) {
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

    // Logic for getting average volume data for the last 30 days
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

    public static List<String> getAllExchanges() {
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

    public static String getOpen(String ticker) {
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

    public static List<String> getHighLow(String ticker) throws Exception {
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

    //All the methods that use company overview endpoint
    public static String getCompanyOverview(String ticker) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    public static String getPrimaryExchange(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("primary_exchange");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getMarketCap(String ticker) throws Exception {
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

    public String getTickerName(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");
        return resultsObject.getString("name");
    }

    @Override
    public Double getCurrentPrice(String ticker) {
        String content = getTickerSnapshot(ticker);

        JSONObject jsonResponse = new JSONObject(content);
        JSONObject day = jsonResponse.getJSONObject("ticker").getJSONObject("day");
        return round(day.getDouble("c"), 2);
    }
//    public Double getCurrentPrice(String ticker) {
//        return 100.0; // Fixed price for testing purposes
//    }

    @Override
    public List<Double> getPriceIncrease(String ticker) {
        String content = getTickerSnapshot(ticker);
        ArrayList<Double> increase = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(content);
        JSONObject tickerData = jsonResponse.getJSONObject("ticker");
        increase.add(round(tickerData.getDouble("todaysChangePerc"), 4));
        increase.add(round(tickerData.getDouble("todaysChange"), 4));
        return increase;
    }

    public static String getDesc(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("description");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getWebpage(String ticker) throws Exception {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");

        try {
            return resultsObject.getString("homepage_url");
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String getLocation(String ticker) throws Exception {
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

//    public LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate) {
//        LinkedHashMap<Long, Double> historicalData = new LinkedHashMap<>();
//        String content = getAggregateData(ticker, 1,timespan, startDate, endDate);
//        JSONObject jsonObject = new JSONObject(content);
//        System.out.println("Fetching historical data for " + ticker);
//
//        if (jsonObject.has("results")) {
//            JSONArray results = jsonObject.getJSONArray("results");
//            for (int i = 0; i < results.length(); i++) {
//                JSONObject dailyData = results.getJSONObject(i);
//                Long date = dailyData.getLong("t");
//                double closePrice = dailyData.getDouble("c");
//                historicalData.put(date, closePrice);
//            }
//        } else {
//            System.out.println("No 'results' found in response. Full response: " + content);
//        }
//        return historicalData;
//    }

    public LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate) {
        LinkedHashMap<Long, Double> historicalData = new LinkedHashMap<>();
        String baseURL = "https://api.polygon.io/v2/aggs/ticker/";
        String urlString = baseURL + ticker + "/range/1/" + timespan + "/" + startDate + "/" + endDate + "?adjusted=true&sort=asc&apiKey=" + API_KEY;

        System.out.println("Fetching historical data for " + ticker);
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject json = new JSONObject(content.toString());
            if (json.has("results")) {
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject dailyData = results.getJSONObject(i);
//                    String date = FORMATTER.format(
//                            LocalDate.ofEpochDay(dailyData.getLong("t") / 86400000L)
//                    );
                    Long date = dailyData.getLong("t");
                    double closePrice = dailyData.getDouble("c");
                    historicalData.put(date, closePrice);
                }
            } else {
                System.out.println("No 'results' found in response. Full response: " + content);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return historicalData;
    }

    @Override
    public LinkedHashMap<Long, Double> getIndicatorData(String indicator, String ticker, String timespan, String from, String to, int window) {
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

    public static String identifyInputType(String input) {
        // Regex pattern for tickers (alphanumeric, periods, 1-5 characters)
        String tickerPattern = "^[A-Za-z0-9.]{1,5}$";
        // List of known exchanges
        List<String> exchanges = getAllExchanges();

        if (exchanges.contains(input.toUpperCase())) {
            return "Exchange";
        } else if (input.matches(tickerPattern)) {
            return "Ticker";
        } else {
            return "Keyword";
        }
    }

    public static void main(String[] args) {
        StockDataAccess stockDataAccess = new StockDataAccess();

        // Get the current price for AAPL
        Double currentPrice = stockDataAccess.getCurrentPrice("AAPL");

        // Display the result
        if (currentPrice != null) {
            System.out.println("The current price of AAPL is: $" + currentPrice);
        } else {
            System.out.println("Failed to retrieve the current price for AAPL.");
        }
    }

}