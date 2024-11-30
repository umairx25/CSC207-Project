package data_access;

import use_case.portfolio.PortfolioDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryPortfolioUserDataAccess implements PortfolioDataAccessInterface {
    private double cashBalance = 27000.00; // Starting cash balance
    private final Map<String, PortfolioPosition> portfolio = new HashMap<>();
    private final List<Transaction> transactionHistory = new ArrayList<>();

    @Override
    public void executeBuyOrder(String company, int quantity) {
        // Fetch the stock price for the company using ChartDataAccess
        StockDataAccess dataAccess = new StockDataAccess();
        double pricePerShare = dataAccess.getCurrentPrice(company);
        System.out.println(pricePerShare);
        if (pricePerShare == -1) {
            throw new RuntimeException("Failed to fetch stock price for " + company);
        }

        // Calculate total cost of buying the specified quantity
        double totalCost = pricePerShare * quantity;
        System.out.println(totalCost);
        if (cashBalance < totalCost) {
            throw new IllegalArgumentException("Not enough balance to execute buy order.");
        }

        // Update cash balance after the purchase
        cashBalance -= totalCost;

        // Update portfolio position
        PortfolioPosition position = portfolio.getOrDefault(company, new PortfolioPosition(company, 0, 0));
        double newAverageCost = ((position.getQuantity() * position.getAvgCost()) + totalCost) / (position.getQuantity() + quantity);
        position.addQuantity(quantity, newAverageCost);
        portfolio.put(company, position);

        // Add transaction to history
        transactionHistory.add(new Transaction("BUY", company, quantity, pricePerShare));
    }

    @Override
    public void executeSellOrder(String company, int quantity) {
        StockDataAccess dataAccess = new StockDataAccess();
        PortfolioPosition position = portfolio.get(company);
        if (position == null || position.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough shares to execute sell order.");
        }

        // Fetch the stock price for the company using StockDataAccess
        double pricePerShare = dataAccess.getCurrentPrice(company);
        if (pricePerShare == -1) {
            throw new RuntimeException("Failed to fetch stock price for " + company);
        }

        // Calculate total value from selling the specified quantity
        double totalValue = pricePerShare * quantity;

        // Update cash balance after the sale
        cashBalance += totalValue;

        // Update portfolio position
        position.subtractQuantity(quantity);

        // Remove company from portfolio if no shares are left
        if (position.getQuantity() == 0) {
            portfolio.remove(company);
        } else {
            portfolio.put(company, position);
        }

        // Add transaction to history
        transactionHistory.add(new Transaction("SELL", company, quantity, pricePerShare));
    }

    @Override
    public double getTotalBalance() {
        return cashBalance;
    }

    @Override
    public double getPortfolioBalance() {
        return portfolio.values().stream().mapToDouble(position -> position.getQuantity() * getStockPrice(position.getCompany())).sum();
    }

    /**
     */
    @Override
    public List<String[]> getPortfolioTableData() {
        return List.of();
    }

    @Override
    public Object[][] getPortfolioData() {
        Object[][] data = new Object[portfolio.size()][6];
        int index = 0;
        for (PortfolioPosition position : portfolio.values()) {
            double currentPrice = getStockPrice(position.getCompany());
            double totalValue = position.getQuantity() * currentPrice;
            double totalCost = position.getQuantity() * position.getAvgCost();
            double gainLossPercentage = totalCost == 0 ? 0 : ((totalValue - totalCost) / totalCost) * 100;

            data[index++] = new Object[]{
                    position.getCompany(),
                    position.getQuantity(),
                    position.getAvgCost(),
                    currentPrice,
                    totalValue,
                    String.format("%.2f%%", gainLossPercentage)
            };
        }
        return data;
    }

    @Override
    public List<String> getTransactionHistory() {
        List<String> history = new ArrayList<>();
        for (Transaction transaction : transactionHistory) {
            history.add(transaction.toString());
        }
        return history;
    }

    @Override
    public double getTotalGainLoss() {
        return getPortfolioBalance() - portfolio.values().stream().mapToDouble(position -> position.getQuantity() * position.getAvgCost()).sum();
    }

    @Override
    public double getTotalGainLossPercentage() {
        double totalCost = portfolio.values().stream().mapToDouble(position -> position.getQuantity() * position.getAvgCost()).sum();
        return totalCost == 0 ? 0 : (getTotalGainLoss() / totalCost) * 100;
    }

    @Override
    public boolean hasEnoughShares(String company, int quantity) {
        PortfolioPosition position = portfolio.get(company);
        return position != null && position.getQuantity() >= quantity;
    }

    @Override
    public boolean hasEnoughBalance(String company, int quantity) {
        double pricePerShare = getStockPrice(company);
        return cashBalance >= pricePerShare * quantity;
    }

    private double getStockPrice(String company) {
        try {
            // Use StockDataAccess to fetch the current price for the given company
            StockDataAccess stockDataAccess = new StockDataAccess();
            return stockDataAccess.getCurrentPrice(company); // Assume such a method exists
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static class PortfolioPosition {
        private final String company;
        private int quantity;
        private double avgCost;

        public PortfolioPosition(String company, int quantity, double avgCost) {
            this.company = company;
            this.quantity = quantity;
            this.avgCost = avgCost;
        }

        public String getCompany() {
            return company;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getAvgCost() {
            return avgCost;
        }

        public void addQuantity(int quantity, double avgCost) {
            this.quantity += quantity;
            this.avgCost = avgCost;
        }

        public void subtractQuantity(int quantity) {
            this.quantity -= quantity;
        }
    }

    private static class Transaction {
        private final String type;
        private final String company;
        private final int quantity;
        private final double price;

        public Transaction(String type, String company, int quantity, double price) {
            this.type = type;
            this.company = company;
            this.quantity = quantity;
            this.price = price;
        }

        @Override
        public String toString() {
            return type + ": " + quantity + " shares of " + company + " at $" + price;
        }
    }
}
