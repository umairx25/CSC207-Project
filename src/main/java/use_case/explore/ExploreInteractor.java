package use_case.explore;

import entity.Stock;
import java.util.List;

/**
 * The interactor for the Explore feature, implementing the business logic
 * and coordinating between data access and presentation layers.
 */
public class ExploreInteractor implements ExploreInputBoundary {

    private final ExploreDataAccessInterface dataAccess;
    private final ExploreOutputBoundary presenter;

    /**
     * Constructs a new ExploreInteractor with the given data access and presenter.
     *
     * @param dataAccess the data access interface
     * @param presenter  the output boundary for presenting results
     */
    public ExploreInteractor(ExploreDataAccessInterface dataAccess, ExploreOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    /**
     * Handles the search for companies based on the provided query.
     * The query can be a ticker, exchange, or a keyword, and the interactor
     * delegates the search task to the appropriate method in the data access layer.
     *
     * @param inputData the data input containing the search query (ticker, exchange, or keyword)
     */
    @Override
    public void searchCompanies(SearchInputData inputData) {
        String inputType = identifyInputType(inputData.getQuery());
        String results = switch (inputType) {
            case "Ticker" -> dataAccess.searchCompany(inputData.getQuery(), "", "");
            case "Exchange" -> dataAccess.searchCompany("", inputData.getQuery(), "");
            case "Keyword" -> dataAccess.searchCompany("", "", inputData.getQuery());
            default -> throw new IllegalArgumentException("Invalid input type!");
        };
        List<String> tickers = dataAccess.extractCompanyTickers(results);
        presenter.presentCompanies(new SearchOutputData(tickers));
    }

    /**
     * Retrieves detailed information about a specific company based on its ticker.
     * If company data is successfully fetched, it constructs a `Stock` object and
     * presents it to the output boundary. If an error occurs, it presents an error state.
     *
     * @param inputData the data input containing the company ticker
     * @throws Exception if there is an issue fetching company details
     */
    @Override
    public void getCompanyDetails(CompanyInputData inputData) {
        try {
            Stock stock = new Stock(
                    inputData.getSelectedCompany(),
                    dataAccess.getTickerName(inputData.getSelectedCompany()),
                    dataAccess.getDesc(inputData.getSelectedCompany()),
                    dataAccess.getPrimaryExchange(inputData.getSelectedCompany()),
                    dataAccess.getMarketCap(inputData.getSelectedCompany()),
                    dataAccess.getOpen(inputData.getSelectedCompany()),
                    dataAccess.getHighLow(inputData.getSelectedCompany()).get(0),
                    dataAccess.getHighLow(inputData.getSelectedCompany()).get(1),
                    dataAccess.calculateAverageVolume(inputData.getSelectedCompany()),
                    dataAccess.getLocation(inputData.getSelectedCompany()),
                    dataAccess.getWebpage(inputData.getSelectedCompany())
            );
            presenter.presentCompanyDetails(new CompanyOutputData(stock));
        } catch (Exception e) {
            presenter.presentError(true);
        }
    }

    /**
     * Identifies the type of input (ticker, exchange, or keyword) based on the query.
     * It checks if the input matches an exchange, a ticker format, or defaults to a keyword.
     *
     * @param input the search query to be identified
     * @return a string representing the input type: "Ticker", "Exchange", or "Keyword"
     */
    @Override
    public String identifyInputType(String input) {
        String tickerPattern = "^[A-Za-z0-9.]{1,5}$";
        List<String> exchanges = dataAccess.getAllExchanges();
        if (exchanges.contains(input.toUpperCase())) {
            return "Exchange";
        } else if (input.matches(tickerPattern)) {
            return "Ticker";
        } else {
            return "Keyword";
        }
    }

    /**
     * Executes the use case to switch to the home view in the user interface.
     * This method delegates the action to the presenter.
     */
    @Override
    public void switchToHomeView() {
        presenter.switchToHomeView();
    }
}
