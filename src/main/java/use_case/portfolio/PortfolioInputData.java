package use_case.portfolio;

public class PortfolioInputData {
    private final String company;
    private final int quantity;
    private final String transactionType;

    public PortfolioInputData(String company, int quantity, String transactionType) {
        this.company = company;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }

    public String getCompany() {
        return company;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }
}