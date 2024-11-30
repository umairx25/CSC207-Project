package use_case.explore;

import java.util.List;

public interface ExploreDataAccessInterface {
    String searchCompany(String ticker, String exchange, String keyword);
    List<String> extractCompanyTickers(String jsonData);
    List<String> getAllExchanges();

    String getTickerName(String ticker) throws Exception;
    String getDesc(String ticker) throws Exception;
    String getPrimaryExchange(String ticker) throws Exception;
    String getMarketCap(String ticker) throws Exception;
    String getOpen(String ticker);
    List<String> getHighLow(String ticker) throws Exception;
    String getWebpage(String ticker) throws Exception;
    String getLocation(String ticker) throws Exception;
    String calculateAverageVolume(String ticker);


}
