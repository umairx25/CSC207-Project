package entity;

import java.util.Date;

public class Transaction {
    private String company;
    private int quantity;
    private double pricePerShare;
    private String type; // "BUY" or "SELL"
    private Date date;

    public Transaction(String company, int quantity, double pricePerShare) {
        this.company = company;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
    }

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
