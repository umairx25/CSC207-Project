//ChartOutputData
package use_case.chart;

import com.google.firebase.auth.UserInfo;
import entity.ChartInfo;

import java.util.LinkedHashMap;

public class ChartOutputData {
    private final ChartInfo chartInfo;


    public ChartOutputData(ChartInfo chartInfo) {
        this.chartInfo = chartInfo;
    }

    public LinkedHashMap<Long, Double> getPriceHistory() {
        return chartInfo.getPriceHistory();
    }

    public LinkedHashMap<Long, Double> getSmaData() {
        return chartInfo.getSma();
    }

    public LinkedHashMap<Long, Double> getEmaData() {
        return chartInfo.getEma();
    }

    public LinkedHashMap<Long, Double> getRsiData() {
        return chartInfo.getRsi();
    }
}