package entity;

import java.util.LinkedHashMap;

public class ChartData {
    private final LinkedHashMap<Long, Double> data;

    public ChartData(LinkedHashMap<Long, Double> data) {
        this.data = data;
    }

    public LinkedHashMap<Long, Double> getData() {
        return data;
    }
}