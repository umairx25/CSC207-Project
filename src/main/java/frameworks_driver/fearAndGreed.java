package frameworks_driver;

import data_access.StockHelper;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class fearAndGreed extends StockHelper {

    static final Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("POLYGON_API_KEY");

    public static double calculateMarketMomentumScore() {
        double currentPrice = currPrice("SPY");
        LinkedHashMap<Long, Double> smaData = getSMAData("SPY", "day", LocalDate.now().minusDays(125).toString(), LocalDate.now().toString(), 125);

        double sma = smaData.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);

        // Normalize to a 0-100 scale with clamping
        double score = 50 + ((currentPrice - sma) / Math.abs(sma)) * 50;
        return Math.min(100, Math.max(0, score));
    }



    public static double calculateStockPriceStrengthScore() {
        List<String> tickers = getTop100Tickers();
        int highs = 0;
        int lows = 0;

        for (String ticker : tickers) {
            Double price = currPrice(ticker);
            LinkedHashMap<Long, Double> priceData = getSMAData(ticker, "day", LocalDate.now().minusDays(365).toString(), LocalDate.now().toString(), 365);
            double yearlyHigh = priceData.values().stream().mapToDouble(Double::doubleValue).max().orElse(price);
            double yearlyLow = priceData.values().stream().mapToDouble(Double::doubleValue).min().orElse(price);

            if (price >= yearlyHigh * 0.95) highs++;
            if (price <= yearlyLow * 1.05) lows++;
        }

        double strengthScore = (double) highs / (highs + lows) * 100;
        return Math.min(100, Math.max(0, strengthScore));
    }


    public static double calculateStockPriceBreadthScore() {
        List<String> tickers = getTop100Tickers();
        int advancing = 0;
        int declining = 0;

        for (String ticker : tickers) {
            ArrayList<Double> change = increase(ticker);
            if (change.get(1) > 0) advancing++;
            else if (change.get(1) < 0) declining++;
        }

        double breadthScore = (double) advancing / (advancing + declining) * 100;
        return Math.min(100, Math.max(0, breadthScore));
    }


    public static double getPutCallRatioScore() {
        // Placeholder until Polygon API supports put/call ratios
        return 50;
    }

    public static double getVolatilityScore() {
        double vix = currPrice("VIX");

        // Map VIX index to a 0-100 scale; adjust the multiplier to suit your scale
        return Math.max(0, Math.min(100, 100 - (vix * 2)));
    }

    public static double getSafeHavenDemandScore() {
        double stockReturn = currPrice("SPY");  // S&P 500 ETF
        double bondReturn = currPrice("TLT");   // 20-Year Treasury Bond ETF

        // Higher bond preference indicates fear
        return (bondReturn > stockReturn) ? 25 : 75;
    }

    public static double getJunkBondDemandScore() {
        // Placeholder for junk bond spread score
        return 50; // Neutral score
    }


    public static double calculateFearGreedIndex() {
        double marketMomentumScore = calculateMarketMomentumScore();
        double stockPriceStrengthScore = calculateStockPriceStrengthScore();
        double stockPriceBreadthScore = calculateStockPriceBreadthScore();
        double putCallRatioScore = getPutCallRatioScore();
        double marketVolatilityScore = getVolatilityScore();
        double safeHavenDemandScore = getSafeHavenDemandScore();
        double junkBondDemandScore = getJunkBondDemandScore();

        // Average the scores
        double fearGreedIndex = (marketMomentumScore + stockPriceStrengthScore + stockPriceBreadthScore +
                putCallRatioScore + marketVolatilityScore +
                safeHavenDemandScore + junkBondDemandScore) / 7;

        return fearGreedIndex;
    }

}


//    public static void printSentiment(double fearGreedIndex) {
//        if (fearGreedIndex >= 75) {
//            System.out.println("Extreme Greed");
//        } else if (fearGreedIndex >= 50) {
//            System.out.println("Greed");
//        } else if (fearGreedIndex >= 25) {
//            System.out.println("Fear");
//        } else {
//            System.out.println("Extreme Fear");
//        }
//    }
//
//    public static void main(String[] args) {
//        double fearGreedIndex = calculateFearGreedIndex();
//        System.out.println("Fear & Greed Index: " + fearGreedIndex);
//        printSentiment(fearGreedIndex);
//    }
//}