package frameworks_driver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;


public class PolygonAPI {
    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");

    public static void main(String[] args) {
        //Test for getAggregateData
        String ticker = "AAPL";
        String multiplier = "1";
        String timespan = "day";
        String from = "2023-01-01";
        String to = "2023-12-31";
        String jsonResponse = getAggregateData(ticker, multiplier, timespan, from, to);

        //Test for getSMAData
//        String stockTicker = "AAPL"; String timespan = "day";
//        String from = "2023-01-01"; String to = "2023-12-31";
//        int window = 50; // SMA window size
//        String jsonResponse = getSMAData(stockTicker, timespan, from, to, window);

        //Test for getCompanyOverview
//        String ticker = "AAPL";
//        String jsonResponse = getCompanyOverview(ticker);

        //Test for getRSIData
//        String stockTicker = "AAPL"; String timespan = "day";
//        String from = "2023-01-01";
//        String to = "2023-12-31";
//        int window = 14; // RSI window size
//        String jsonResponse = getRSIData(stockTicker, timespan, from, to, window);

        //Test for getAllStockTypes
//        String jsonResponse = getAllStockTypes();

        //Test for searchCompany
//        String jsonResponse = searchCompany("","XNAS" ); //searching with the exchange's MIC code ( for NASDAQ in this case)
//        String jsonResponse = searchCompany("APPL", ""); //searching with just the ticker

        System.out.println("Response: " +  formatJson(jsonResponse));
    }

    //Aggregate Data
    //multiplier: interval, and timespan: type of interval. So if you set multiplier to 5 and timespan
        // to minute then it will give data in 5 min intervals from start date to end date.
    //notation for dates is year-month-day, like 2024-02-14.
    public static String getAggregateData(String ticker, String multiplier, String timespan, String from, String to) {
        String baseURL = "https://api.polygon.io/v2/aggs/ticker/";
        String urlString = baseURL + ticker + "/range/" + multiplier + "/" + timespan + "/" + from + "/" + to + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }
    public static String getIntradayData(String ticker, String multiplier, String day) {
        String timespan = "day";
        return getAggregateData(ticker, multiplier, timespan, day, day);
    }

    //Indicators
    //window: used to calculate the simple moving average (SMA). i.e. a window size of 10 with daily aggregates would result in a 10 day moving average.
    public static String getSMAData(String ticker, String timespan, String from, String to, int window) {
        String baseURL = "https://api.polygon.io/v1/indicators/sma/";
        String urlString = baseURL + ticker + "?timespan=" + timespan + "&from=" + from + "&to=" + to + "&window=" + window + "&apiKey=" + API_KEY;
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
    public static String searchCompany(String ticker, String exchange) {
        String baseURL = "https://api.polygon.io/v3/reference/tickers";
        String urlString = "";
        if (Objects.equals(ticker, "")) {
            urlString = baseURL + "?market=stocks&exchange=" + exchange + "&apiKey=" + API_KEY;
        } else if (Objects.equals(exchange, "")) {
            urlString = baseURL + "?ticker=" + ticker + "&market=stocks&apiKey=" + API_KEY;
        } else {
            throw new IllegalArgumentException("You must enter either a ticker or an exchange, but not both!");
        }
        return HTTPRequest(urlString);
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
    public static String getRelatedCompanies(String ticker) {
        String baseURL ="https://api.polygon.io/v1/related-companies/";
        String urlString = baseURL + ticker + "?apiKey=" + API_KEY;
        return HTTPRequest(urlString);
    }


    //Sends requests to the server.
    private static String HTTPRequest(String urlString) {
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
}