package chart;

import data_access.StockDataAccess;
import interface_adapter.chart.ChartPresenter;
import interface_adapter.chart.ChartViewModel;
import org.junit.Before;
import org.junit.Test;
import use_case.chart.ChartInputData;
import use_case.chart.ChartInteractor;

import static org.junit.Assert.*;
import java.util.LinkedHashMap;

/**
 * Unit tests for the ChartInteractor class.
 * Tests interactions between the ChartInteractor, StockDataAccess, and ChartPresenter.
 */
public class ChartInteractorTest {

    private StockDataAccess stockDataAccess;
    private ChartPresenter chartPresenter;
    private ChartViewModel chartViewModel;
    private ChartInteractor chartInteractor;

    /**
     * Sets up the test environment by initializing dependencies.
     * This method is called before each test.
     */
    @Before
    public void setUp() {
        chartViewModel = new ChartViewModel();
        chartPresenter = new ChartPresenter(chartViewModel);
        stockDataAccess = new StockDataAccess(); // Using real data access here for test
        chartInteractor = new ChartInteractor(stockDataAccess, chartPresenter);
    }

    /**
     * Tests the fetchChartData method of the ChartInteractor class.
     * Verifies that all relevant data is fetched and correctly populated in the ViewModel.
     */
    @Test
    public void testFetchChartData() {
        // Prepare test input data
        ChartInputData chartInputData = new ChartInputData("AAPL", "1y", "2023-01-01", "2023-12-31", true, true, true, true);

        // Call the method we want to test
        chartInteractor.fetchChartData(chartInputData);

        // Assert that the data has been correctly fetched and populated in the ViewModel
        assertNotNull(chartViewModel.getState().getPriceHistory());
        assertTrue(chartViewModel.getState().getPriceHistory().size() > 0);

        assertNotNull(chartViewModel.getState().getSma());
        assertTrue(chartViewModel.getState().getSma().size() > 0);

        assertNotNull(chartViewModel.getState().getEma());
        assertTrue(chartViewModel.getState().getEma().size() > 0);

        assertNotNull(chartViewModel.getState().getRsi());
        assertTrue(chartViewModel.getState().getRsi().size() > 0);

        // Verify current price and point increase are updated
        assertTrue(chartViewModel.getCurrPrice() > 0);
        assertNotNull(chartViewModel.getPointIncrease());
        assertTrue(chartViewModel.getPointIncrease().length() > 0);

        assertNotNull(chartViewModel.getPercentIncrease());
        assertTrue(chartViewModel.getPercentIncrease().length() > 0);
    }

    /**
     * Tests the fetchChartData method with a different stock ticker.
     * Verifies that the data is fetched and updated correctly for a different ticker symbol.
     */
    @Test
    public void testDifferentStockTicker() {
        ChartInputData chartInputData = new ChartInputData("GOOG", "1y", "2023-01-01", "2023-12-31", true, true, true, true);
        chartInteractor.fetchChartData(chartInputData);

        // Ensure that data has been updated for a different ticker symbol
        assertNotNull(chartViewModel.getState().getPriceHistory());
        assertTrue(chartViewModel.getState().getPriceHistory().size() > 0);
    }

    /**
     * Tests the error handling when the API call fails.
     * Simulates an exception in the StockDataAccess class and verifies that the exception is correctly handled.
     */
    @Test
    public void testErrorHandling() {
        // Mock StockDataAccess to simulate an API call failure
        StockDataAccess mockDataAccess = new StockDataAccess() {
            @Override
            public LinkedHashMap<Long, Double> getHistoricalData(String ticker) {
                throw new RuntimeException("API call failed");
            }
        };

        chartInteractor = new ChartInteractor(mockDataAccess, chartPresenter);

        ChartInputData chartInputData = new ChartInputData("AAPL", "1y", "2023-01-01", "2023-12-31", true, true, true, true);

        // Try fetching data and expect error handling
        try {
            chartInteractor.fetchChartData(chartInputData);
        } catch (RuntimeException e) {
            // Assert that the exception was thrown and handled
            assertEquals("API call failed", e.getMessage());
        }
    }
}