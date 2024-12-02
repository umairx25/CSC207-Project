package data_access;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chart.ChartDataAccessInterface;

/**
 * Provides data access for stock-related information and charting,
 * including fetching historical data, current prices, and technical indicators.
 */
public class StockDataAccess implements ChartDataAccessInterface {
    final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("POLYGON_API_KEY");

    /**
     * Retrieves the ticker snapshot data for a given stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return JSON response as a String containing snapshot details
     */
    public String getTickerSnapshot(String ticker) {
        String baseURL = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    /**
     * Retrieves the company overview for a given stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return JSON response as a String containing company overview details
     */
    public String getCompanyOverview(String ticker) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }

    /**
     * Retrieves the company name for a given stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return the name of the company
     */
    public String getTickerName(String ticker) {
        String result = getCompanyOverview(ticker);
        JSONObject jsonObject = new JSONObject(result);
        JSONObject resultsObject = jsonObject.getJSONObject("results");
        return resultsObject.getString("name");
    }

    /**
     * Retrieves the current price for a given stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return the current price as a Double, rounded to 2 decimal places
     */
    @Override
    public Double getCurrentPrice(String ticker) {
        String content = getTickerSnapshot(ticker);
        JSONObject jsonResponse = new JSONObject(content);
        JSONObject day = jsonResponse.getJSONObject("ticker").getJSONObject("day");
        return round(day.getDouble("c"), 2);
    }

    /**
     * Retrieves the percentage and absolute price change for a given stock ticker.
     *
     * @param ticker the stock ticker symbol
     * @return a list containing the percentage and absolute price change
     */
    @Override
    public List<Double> getPriceIncrease(String ticker) {
        String content = getTickerSnapshot(ticker);
        ArrayList<Double> increase = new ArrayList<>();
        JSONObject jsonResponse = new JSONObject(content);
        JSONObject tickerData = jsonResponse.getJSONObject("ticker");
        increase.add(round(tickerData.getDouble("todaysChangePerc"), 2));
        increase.add(round(tickerData.getDouble("todaysChange"), 2));
        return increase;
    }

    /**
     * Fetches historical stock price data for the last 5 years.
     *
     * @param ticker the stock ticker symbol
     * @return a LinkedHashMap where keys are timestamps and values are closing prices
     */
    public LinkedHashMap<Long, Double> getHistoricalData(String ticker) {
        LinkedHashMap<Long, Double> historicalData = new LinkedHashMap<>();
        final String startDate = java.time.LocalDate.now().minusYears(5).toString();
        final String endDate = java.time.LocalDate.now().toString();
        String baseURL = "https://api.polygon.io/v2/aggs/ticker/";
        String urlString = baseURL + ticker + "/range/1/" + "week" + "/" + startDate + "/" + endDate + "?adjusted=true&sort=asc&apiKey=" + API_KEY;

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

    /**
     * Fetches technical indicator data for a given stock ticker.
     *
     * @param indicator the name of the indicator (e.g., SMA, EMA)
     * @param ticker    the stock ticker symbol
     * @param window    the time window for the indicator
     * @return a LinkedHashMap where keys are timestamps and values are indicator values
     */
    @Override
    public LinkedHashMap<Long, Double> getIndicatorData(String indicator, String ticker, int window) {
        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();
        final String startDate = java.time.LocalDate.now().minusYears(5).toString();
        final String endDate = java.time.LocalDate.now().toString();
        String timespan = "week";

        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/%s/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                indicator, ticker, timespan, startDate, endDate, window, API_KEY
        );

        String content = HTTPRequest(urlString);
        JSONObject jsonResponse = new JSONObject(content);

        JSONObject result;
        try {
            result = jsonResponse.getJSONObject("results");
        } catch (Exception e) {
            System.out.println("No 'results' found in response.");
            return data;
        }

        JSONArray values;
        try {
            values = result.getJSONArray("values");
        } catch (Exception e) {
            System.out.println("No 'values' array found in results.");
            return data;
        }

        for (int i = values.length() - 1; i >= 0; i--) {
            JSONObject obj = values.getJSONObject(i);
            data.put(obj.getLong("timestamp"), obj.getDouble("value"));
        }
        return data;
    }

    /**
     * Rounds a given value to a specified number of decimal places.
     *
     * @param value  the value to round
     * @param places the number of decimal places
     * @return the rounded value as a Double
     */
    public Double round(double value, int places) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(places));
        return Double.parseDouble(df.format(value));
    }

    /**
     * Sends an HTTP GET request to the specified URL.
     *
     * @param urlString the URL to send the request to
     * @return the response as a String
     */
    public String HTTPRequest(String urlString) {
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
}
