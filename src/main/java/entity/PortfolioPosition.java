package entity;

public class PortfolioPosition {
    private String company;
    private static int quantity;
    private static double avgCost;

    // Constructor
    public PortfolioPosition(String company, int quantity, double avgCost) {
        this.company = company;
        this.quantity = quantity;
        this.avgCost = avgCost;
    }

    // Add shares to the portfolio
    public void addQuantity(int quantityToAdd, double newCostPerShare) {
        double totalExistingCost = this.quantity * this.avgCost;
        double totalNewCost = quantityToAdd * newCostPerShare;

        this.quantity += quantityToAdd;
        this.avgCost = (totalExistingCost + totalNewCost) / this.quantity;
    }

    // Remove shares from the portfolio
    public void removeQuantity(int quantityToRemove) {
        if (this.quantity < quantityToRemove) {
            throw new IllegalArgumentException("Not enough shares to remove.");
        }
        this.quantity -= quantityToRemove;
    }

    // Getters
    public String getCompany() {
        return company;
    }

    public static int getQuantity() {
        return quantity;
    }

    public static double getAvgCost() {
        return avgCost;
    }
}
