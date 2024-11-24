package data_access;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.chart.ChartDataAccessInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartDataAccess implements ChartDataAccessInterface {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");

    private String makeHttpRequest(String urlString) {
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

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate) {
        LinkedHashMap<Long, Double> historicalData = new LinkedHashMap<>();
        String urlString = String.format(
                "https://api.polygon.io/v2/aggs/ticker/%s/range/1/%s/%s/%s?adjusted=true&sort=asc&apiKey=%s",
                ticker, timespan, startDate, endDate, API_KEY
        );

        String content = makeHttpRequest(urlString);
        if (content != null) {
            JSONObject json = new JSONObject(content);
            if (json.has("results")) {
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject dailyData = results.getJSONObject(i);
                    historicalData.put(dailyData.getLong("t"), dailyData.getDouble("c"));
                }
            }
        }
        return historicalData;
    }

    @Override
    public LinkedHashMap<Long, Double> getIndicatorData(String type, String ticker, String timespan, String from, String to, int window) {
        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/%s/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                type, ticker, timespan, from, to, window, API_KEY
        );

        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();
        String content = makeHttpRequest(urlString);
        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            if (jsonResponse.has("results")) {
                JSONArray values = jsonResponse.getJSONObject("results").getJSONArray("values");
                for (int i = 0; i < values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    data.put(obj.getLong("timestamp"), obj.getDouble("value"));
                }
            }
        }
        return data;
    }

    @Override
    public String getTickerName(String ticker) {
        String urlString = String.format(
                "https://api.polygon.io/v3/reference/tickers/%s?apiKey=%s", ticker, API_KEY
        );
        String content = makeHttpRequest(urlString);
        if (content != null) {
            JSONObject json = new JSONObject(content);
            return json.getJSONObject("results").getString("name");
        }
        return "";
    }

    @Override
    public Double getCurrentPrice(String ticker) {
        String urlString = String.format(
                "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/%s?apiKey=%s", ticker, API_KEY
        );
        String content = makeHttpRequest(urlString);
        if (content != null) {
            JSONObject json = new JSONObject(content);
            return json.getJSONObject("ticker").getJSONObject("day").getDouble("c");
        }
        return null;
    }

    @Override
    public List<Double> getPriceIncrease(String ticker) {
        String urlString = String.format(
                "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/%s?apiKey=%s", ticker, API_KEY
        );
        String content = makeHttpRequest(urlString);
        ArrayList<Double> increase = new ArrayList<>();
        if (content != null) {
            JSONObject json = new JSONObject(content);
            JSONObject tickerData = json.getJSONObject("ticker");
            increase.add(round(tickerData.getDouble("todaysChangePerc"), 4));
            increase.add(round(tickerData.getDouble("todaysChange"), 4));
        }
        return increase;
    }

    private Double round(double value, int places) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(places));
        return Double.parseDouble(df.format(value));
    }
}