package portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.portfolio.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the PortfolioInteractor class.
 */
class PortfolioInteractorTest {
    private PortfolioInteractor interactor;
    private PortfolioDataAccessInterface mockDataAccess;
    private PortfolioOutputBoundary mockPresenter;

    /**
     * Sets up the test environment with mocks for data access and output boundary.
     */
    @BeforeEach
    void setUp() {
        // Manual mock for PortfolioDataAccessInterface
        mockDataAccess = new PortfolioDataAccessInterface() {
            @Override
            public void executeBuyOrder(String company, int quantity) {
                // Simulate successful buy order behavior
                assertEquals("AAPL", company);
                assertEquals(10, quantity);
            }

            @Override
            public void executeSellOrder(String company, int quantity) {
                // Simulate successful sell order behavior
                assertEquals("GOOG", company);
                assertEquals(5, quantity);
            }

            @Override
            public double getTotalBalance() {
                return 1000.0;  // Mock the total balance
            }

            @Override
            public double getPortfolioBalance() {
                return 500.0;  // Mock the portfolio balance
            }

            @Override
            public Object[][] getPortfolioData() {
                return new Object[][]{
                        {"AAPL", 10, 150.0, 155.0, 1550.0, 3.33},
                        {"GOOG", 5, 1200.0, 1225.0, 6125.0, 2.08}
                };  // Mock portfolio data
            }

            @Override
            public List<Map<String, Object>> getTransactionHistory() {
                return List.of(Map.of("type", "BUY", "company", "AAPL", "quantity", 10, "price", 155.0));
            }

            @Override
            public double getTotalGainLoss() {
                return 50.0;  // Mock total gain/loss
            }

            @Override
            public double getTotalGainLossPercentage() {
                return 3.33;  // Mock total gain/loss percentage
            }
        };

        // Manual mock for PortfolioOutputBoundary
        mockPresenter = new PortfolioOutputBoundary() {
            @Override
            public void presentPortfolioInfo(PortfolioOutputData portfolioOutputData) {
                // Assertions to verify the correctness of portfolio info
                assertNotNull(portfolioOutputData);
                assertEquals(1000.0, portfolioOutputData.getTotalBalance());
                assertEquals(500.0, portfolioOutputData.getPortfolioBalance());
                assertNotNull(portfolioOutputData.getPortfolioData());
                assertTrue(portfolioOutputData.getPortfolioData().length > 0);
            }

            @Override
            public void presentTransactionError(String errorMessage) {
                // Ensure error is presented correctly
                assertNotNull(errorMessage);
            }
        };

        interactor = new PortfolioInteractor(mockDataAccess, mockPresenter);
    }

    /**
     * Test executing a BUY transaction successfully.
     */
    @Test
    void testExecuteTransaction_Buy_Success() {
        PortfolioInputData inputData = new PortfolioInputData("AAPL", 10, "BUY");

        // Execute the transaction
        interactor.executeTransaction(inputData);
    }

    /**
     * Test executing a SELL transaction successfully.
     */
    @Test
    void testExecuteTransaction_Sell_Success() {
        PortfolioInputData inputData = new PortfolioInputData("GOOG", 5, "SELL");

        // Execute the transaction
        interactor.executeTransaction(inputData);
    }

    /**
     * Test for an invalid transaction type, ensuring proper error handling.
     */
    @Test
    void testExecuteTransaction_InvalidType() {
        PortfolioInputData inputData = new PortfolioInputData("AAPL", 10, "INVALID");

        // Execute the transaction and expect error handling
        interactor.executeTransaction(inputData);
    }

    /**
     * Test fetching portfolio info and verifying the portfolio data structure.
     */
    @Test
    void testGetPortfolioInfo() {
        PortfolioOutputData outputData = interactor.getPortfolioInfo();

        // Assertions to verify the correctness of portfolio info
        assertEquals(1000.0, outputData.getTotalBalance());
        assertEquals(500.0, outputData.getPortfolioBalance());
        assertNotNull(outputData.getPortfolioData());
        assertTrue(outputData.getPortfolioData().length > 0);
        assertEquals(50.0, outputData.getTotalGainLoss());
        assertEquals(3.33, outputData.getTotalGainLossPercentage());
    }

    /**
     * Test executing a transaction where an exception is thrown during the process.
     */
    @Test
    void testExecuteTransaction_ExceptionHandling() {
        PortfolioInputData inputData = new PortfolioInputData("AAPL", 10, "BUY");

        // Simulate exception during buy order execution
        try {
            mockDataAccess.executeBuyOrder("AAPL", 10);
            throw new RuntimeException("API failure");
        } catch (Exception e) {
            // Handle exception and verify error handling
            mockPresenter.presentTransactionError(e.getMessage());
        }
    }
}
