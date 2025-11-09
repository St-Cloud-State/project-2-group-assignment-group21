package com.stage1;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date date;
    private double amount;
    private String description;

    public Transaction(double amount, String description) {
        this.date = new Date();
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String type = (amount <= 0) ? "Credit" : "Debit";
        return sdf.format(date) + " | " + type + " | $" + String.format("%.2f", Math.abs(amount)) + " | " + description;
    }
}
