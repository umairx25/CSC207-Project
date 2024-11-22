package use_case;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockDataHelper {

    // Method to parse the JSON response and convert it into a list of StockData objects
    public static List<StockData> parseStockData(String jsonResponse) {
        List<StockData> stockDataList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);

            double volume = result.getDouble("v");
            double open = result.getDouble("o");
            double close = result.getDouble("c");
            double high = result.getDouble("h");
            double low = result.getDouble("l");
            long timestamp = result.getLong("t");

            StockData data = new StockData(volume, open, close, high, low, timestamp);
            stockDataList.add(data);
        }

        return stockDataList;
    }
}