package data_access;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class StockDataAccess {

    static String makeHttpRequest(String urlString) {
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

    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LinkedHashMap<Long, Double> getHistoricalData(String ticker, String timespan, String startDate, String endDate) {
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

//    public static String getTickerName(String ticker) throws Exception {
//        String urlString = "https://api.polygon.io/v3/reference/tickers/" + ticker + "?apiKey=" + API_KEY;
//        String result = HTTPRequest(urlString);
//        JSONObject jsonObject = new JSONObject(result);
//        JSONObject resultsObject = jsonObject.getJSONObject("results");
//        return resultsObject.getString("name");
//    }

    public static LinkedHashMap<Long, Double> getSMAData(String ticker, String timespan, String from, String to, int window) {
        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/sma/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                ticker, timespan, from, to, window, API_KEY
        );
        String content = makeHttpRequest(urlString);
        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();

        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            JSONObject result = jsonResponse.getJSONObject("results");
            JSONArray values = result.getJSONArray("values");

            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject obj = values.getJSONObject(i);
                data.put(obj.getLong("timestamp"), obj.getDouble("value"));
            }
        }
        return data;
    }

    public static LinkedHashMap<Long, Double> getEMAData(String ticker, String timespan, String from, String to, int window) {
        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/ema/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                ticker, timespan, from, to, window, API_KEY
        );
        String content = makeHttpRequest(urlString);
        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();

        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            JSONObject result = jsonResponse.getJSONObject("results");
            JSONArray values = result.getJSONArray("values");

            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject obj = values.getJSONObject(i);
                data.put(obj.getLong("timestamp"), obj.getDouble("value"));
            }
        }
        return data;
    }

    public static LinkedHashMap<Long, Double> getRSIData(String ticker, String timespan, String from, String to, int window) {
        String urlString = String.format(
                "https://api.polygon.io/v1/indicators/rsi/%s?timespan=%s&from=%s&to=%s&window=%d&limit=5000&apiKey=%s",
                ticker, timespan, from, to, window, API_KEY
        );
        String content = makeHttpRequest(urlString);
        LinkedHashMap<Long, Double> data = new LinkedHashMap<>();

        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            JSONObject result = jsonResponse.getJSONObject("results");
            JSONArray values = result.getJSONArray("values");

            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject obj = values.getJSONObject(i);
                data.put(obj.getLong("timestamp"), obj.getDouble("value"));
            }
        }
        return data;
    }

    public static List<String> getTop100Tickers() {
        List<String> tickers = new ArrayList<>();
        String urlString = "https://api.polygon.io/v3/reference/tickers?active=true&limit=100&apiKey=" + API_KEY;
        String content = makeHttpRequest(urlString);

        if (content != null) {
            JSONObject json = new JSONObject(content);
            if (json.has("results")) {
                JSONArray results = json.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    tickers.add(results.getJSONObject(i).getString("ticker"));
                }
            } else {
                System.out.println("No 'results' found in response. Full response: " + content);
            }
        }
        return tickers;
    }

    public static Double currPrice(String ticker) {
        String urlString = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/" + ticker + "?apiKey=" + API_KEY;
        String content = makeHttpRequest(urlString);

        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            JSONObject day = jsonResponse.getJSONObject("ticker").getJSONObject("day");
            return round(day.getDouble("c"), 4);
        }
        return null;
    }

    public static ArrayList<Double> increase(String ticker) {
        String urlString = "https://api.polygon.io/v2/snapshot/locale/us/markets/stocks/tickers/" + ticker + "?apiKey=" + API_KEY;
        String content = makeHttpRequest(urlString);
        ArrayList<Double> increase = new ArrayList<>();

        if (content != null) {
            JSONObject jsonResponse = new JSONObject(content);
            JSONObject tickerData = jsonResponse.getJSONObject("ticker");
            increase.add(round(tickerData.getDouble("todaysChangePerc"), 4));
            increase.add(round(tickerData.getDouble("todaysChange"), 4));
        }
        return increase;
    }

    public static Double round(double value, int places) {
        DecimalFormat df = new DecimalFormat("#." + "#".repeat(places));
        return Double.parseDouble(df.format(value));
    }

}
