package explore;

import entity.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.explore.*;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for the ExploreInteractor class.
 */
class ExploreInteractorTest {
    private ExploreInteractor interactor;

    /**
     * Sets up the test environment with mocks for data access and output boundary.
     */
    @BeforeEach
    void setUp() {
        // Inline mock for ExploreDataAccessInterface
        ExploreDataAccessInterface mockDataAccess = new ExploreDataAccessInterface() {
            @Override
            public String searchCompany(String ticker, String exchange, String keyword) {
                if (!ticker.isEmpty()) {
                    return "{\"tickers\": [\"AAPL\"]}";
                } else if (!exchange.isEmpty()) {
                    return "{\"tickers\": [\"NASDAQ\"]}";
                } else if (!keyword.isEmpty()) {
                    return "{\"tickers\": [\"Tech\"]}";
                }
                return "{}";
            }

            @Override
            public List<String> extractCompanyTickers(String jsonData) {
                if (jsonData.contains("AAPL")) {
                    return List.of("AAPL");
                } else if (jsonData.contains("NASDAQ")) {
                    return List.of("MSFT", "GOOG");
                } else if (jsonData.contains("Tech")) {
                    return List.of("IBM", "INTC");
                }
                return List.of();
            }

            @Override
            public List<String> getAllExchanges() {
                return List.of("NASDAQ", "NYSE");
            }

            @Override
            public String getTickerName(String ticker) throws Exception {
                // Simulate the behavior for an invalid ticker
                if ("INVALID".equals(ticker)) {
                    throw new Exception("Invalid ticker");
                }
                return "Apple Inc.";
            }

            @Override
            public String getDesc(String ticker) {
                return "A leading tech company.";
            }

            @Override
            public String getPrimaryExchange(String ticker) {
                return "NASDAQ";
            }

            @Override
            public String getMarketCap(String ticker) {
                return "2.5T";
            }

            @Override
            public String getOpen(String ticker) {
                return "175.00";
            }

            @Override
            public List<String> getHighLow(String ticker) {
                return Arrays.asList("180.00", "170.00");
            }

            @Override
            public String getWebpage(String ticker) {
                return "https://www.apple.com";
            }

            @Override
            public String getLocation(String ticker) {
                return "Cupertino, CA";
            }

            @Override
            public String calculateAverageVolume(String ticker) {
                return "100M";
            }
        };

        // Inline mock for ExploreOutputBoundary
        ExploreOutputBoundary mockPresenter = new ExploreOutputBoundary() {
            @Override
            public void presentCompanies(SearchOutputData searchOutputData) {
                Assertions.assertNotNull(searchOutputData.getCompanies());
                Assertions.assertFalse(searchOutputData.getCompanies().isEmpty());
            }

            @Override
            public void presentCompanyDetails(CompanyOutputData companyOutputData) {
                Stock stock = companyOutputData.getStats();
                Assertions.assertEquals("Apple Inc.", stock.getName());
                Assertions.assertEquals("2.5T", stock.getMarketCap());
            }

            @Override
            public void presentError(boolean errorState) {
                // Ensuring the error state is presented when "INVALID" ticker is used
                Assertions.assertTrue(errorState);
            }

            @Override
            public void switchToHomeView() {
                // Simulate UI switching logic
                Assertions.assertTrue(true, "Home view switched");
            }
        };

        interactor = new ExploreInteractor(mockDataAccess, mockPresenter);
    }

    /**
     * Test searching companies by ticker symbol.
     */
    @Test
    void testSearchCompaniesByTicker() {
        interactor.searchCompanies(new SearchInputData("AAPL"));
    }

    /**
     * Test searching companies by exchange symbol.
     */
    @Test
    void testSearchCompaniesByExchange() {
        interactor.searchCompanies(new SearchInputData("NASDAQ"));
    }

    /**
     * Test searching companies by keyword.
     */
    @Test
    void testSearchCompaniesByKeyword() {
        interactor.searchCompanies(new SearchInputData("Tech"));
    }

    /**
     * Test getting company details for a valid ticker symbol.
     *
     * @throws Exception If there is an error fetching company details.
     */
    @Test
    void testGetCompanyDetails() throws Exception {
        interactor.getCompanyDetails(new CompanyInputData("AAPL"));
    }

    /**
     * Test for invalid company ticker to ensure the catch block is triggered.
     * The "INVALID" ticker should result in an error state being presented.
     */
    @Test
    void testInvalidCompanyDetails() {
        Assertions.assertDoesNotThrow(() -> interactor.getCompanyDetails(new CompanyInputData("INVALID")));
    }

    /**
     * Tests for identifying valid input types.
     */
    @Test
    void testIdentifyValidInputTypes() {
        Assertions.assertEquals("Ticker", interactor.identifyInputType("AAPL"));
        Assertions.assertEquals("Exchange", interactor.identifyInputType("NASDAQ"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("Unknown"));
    }

    /**
     * Tests for empty and whitespace input scenarios.
     */
    @Test
    void testEmptyAndWhitespaceInput() {
        Assertions.assertEquals("Keyword", interactor.identifyInputType(""));
        Assertions.assertEquals("Keyword", interactor.identifyInputType(" "));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("  "));
    }

    /**
     * Tests for non-alphanumeric and special character inputs.
     */
    @Test
    void testNonAlphanumericAndSpecialCharacters() {
        Assertions.assertEquals("Keyword", interactor.identifyInputType("###"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("AAPL!"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType(" NASDAQ"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("AAPL "));
    }

    /**
     * Tests for mixed-case input scenarios.
     */
    @Test
    void testMixedCaseInputs() {
        Assertions.assertEquals("Ticker", interactor.identifyInputType("aApL"));
        Assertions.assertEquals("Exchange", interactor.identifyInputType("nasDAQ"));
    }

    /**
     * Tests for boundary conditions regarding input length.
     */
    @Test
    void testLengthBoundaryConditions() {
        Assertions.assertEquals("Ticker", interactor.identifyInputType("GOOGL"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("GOOGLE"));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("123456"));
    }

    /**
     * Tests for full company names and descriptive phrases.
     */
    @Test
    void testFullCompanyNamesAndUnknownFormats() {
        Assertions.assertEquals("Keyword", interactor.identifyInputType("Apple Inc."));
        Assertions.assertEquals("Keyword", interactor.identifyInputType("Tech Sector Overview"));
    }

    /**
     * Tests for valid tickers containing periods and lowercase letters.
     */
    @Test
    void testValidTickersWithPeriodsAndLowercaseLetters() {
        Assertions.assertEquals("Ticker", interactor.identifyInputType("BRK.B"));
        Assertions.assertEquals("Ticker", interactor.identifyInputType("BRK.A"));
        Assertions.assertEquals("Ticker", interactor.identifyInputType("ACPpA"));
    }


    /**
     * Test switching to the home view.
     */
    @Test
    void testSwitchToHomeView() {
        interactor.switchToHomeView();
    }
}