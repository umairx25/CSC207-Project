package use_case.explore;

import entity.Stock;
import interface_adapter.explore.ExplorePresenter;

import java.util.List;


public class ExploreInteractor implements ExploreInputBoundary {
    private final ExploreDataAccessInterface dataAccess;
    private final ExploreOutputBoundary presenter;

    public ExploreInteractor(ExploreDataAccessInterface dataAccess, ExploreOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void searchCompanies(SearchInputData inputData) {
        // Call PolygonAPI and return search results
        String inputType = identifyInputType(inputData.getQuery());
        String results = switch (inputType) {
            case "Ticker" -> dataAccess.searchCompany(inputData.getQuery(), "", "");
            case "Exchange" -> dataAccess.searchCompany("", inputData.getQuery(), "");
            case "Keyword" -> dataAccess.searchCompany("", "", inputData.getQuery());
            default -> throw new IllegalArgumentException("Invalid input type!");
        };
        List<String> tickers = dataAccess.extractCompanyTickers(results);
        final SearchOutputData searchOutputData = new SearchOutputData(tickers);
        presenter.presentCompanies(searchOutputData);
    }

    @Override
    public void getCompanyDetails(CompanyInputData inputData) throws Exception {
        // Fetch company details and construct a Stock entity
        Stock stock = null; //possible null pointer exception
        try {
            stock = new Stock(
                    inputData.getSelectedCompany(),
                    dataAccess.getTickerName(inputData.getSelectedCompany()),
                    dataAccess.getDesc(inputData.getSelectedCompany()),
                    dataAccess.getPrimaryExchange(inputData.getSelectedCompany()),
                    dataAccess.getMarketCap(inputData.getSelectedCompany()),
                    dataAccess.getOpen(inputData.getSelectedCompany()),
                    dataAccess.getHighLow(inputData.getSelectedCompany()).get(0),
                    dataAccess.getHighLow(inputData.getSelectedCompany()).get(1),
                    dataAccess.getWebpage(inputData.getSelectedCompany()),
                    dataAccess.getLocation(inputData.getSelectedCompany()),
                    dataAccess.calculateAverageVolume(inputData.getSelectedCompany())
            );
        } catch (Exception e) {
            presenter.presentError(true);
        }
        final CompanyOutputData companyOutputData = new CompanyOutputData(stock);
        presenter.presentCompanyDetails(companyOutputData);
    }


    // helper for searchCompanies
    @Override
    public String identifyInputType(String input) {
        // Regex pattern for tickers (alphanumeric, periods, 1-5 characters)
        String tickerPattern = "^[A-Za-z0-9.]{1,5}$";
        // List of known exchanges
        List<String> exchanges = dataAccess.getAllExchanges();

        if (exchanges.contains(input.toUpperCase())) {
            return "Exchange";
        } else if (input.matches(tickerPattern)) {
            return "Ticker";
        } else {
            return "Keyword";
        }
    }
}
