package com.stage1;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;

public class TransactionHistory implements Iterable<Transaction>, Serializable {
    private static final long serialVersionUID = 1L;
    private List<Transaction> transactions;

    public TransactionHistory() {
        transactions = new LinkedList<>();
    }

    // Add a new transaction (used when a client makes a payment or purchase)
    public void addTransaction(double amount, String note) {
        Transaction t = new Transaction(amount, note);
        transactions.add(t);
    }

    // Allow iteration over all transactions if needed
    public Iterator<Transaction> iterator() {
        return transactions.iterator();
    }

    // Check if the history is empty
    public boolean isEmpty() {
        return transactions.isEmpty();
    }

    // Show all transactions in a clear format
    @Override
    public String toString() {
        if (transactions.isEmpty()) {
            return "No transactions recorded yet.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("------ Transaction History ------\n");

        for (Transaction t : transactions) {
            sb.append(t.toString()).append("\n");
        }

        sb.append("---------------------------------\n");
        return sb.toString();
    }
}
