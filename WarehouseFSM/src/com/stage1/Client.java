package com.stage1;

public class Client {
    private static int nextId = 1;
    private final String id;
    private String name;
    private String address;
    private double balance = 0.0;
    private WishList wishList;
    private TransactionHistory transactionHistory; // Added for transaction tracking
    private final InvoiceHistory invoiceHistory;

    public Client(String name, String address) {
        if (name == null || name.isBlank() || address == null || address.isBlank())
            throw new IllegalArgumentException("Name/address required");
        this.id = "C" + (nextId++);
        this.name = name;
        this.address = address;
        this.wishList = new WishList();
        this.transactionHistory = new TransactionHistory(); // Initialize transaction history
        this.invoiceHistory = new InvoiceHistory();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getBalance() {
        return balance;
    }

    public WishList getWishList() { 
        return wishList; 
    }

    public InvoiceHistory getInvoices() {
         return invoiceHistory; 
    }

    public TransactionHistory getTransactions() {
         return transactionHistory; 
    }
    
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        this.name = name;
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("Address required");
        this.address = address;
    }
    
    public void addInvoice(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("invoice required");
        invoiceHistory.add(invoice);
        adjustBalance(invoice.getTotal(), "Debit for invoice " + invoice.getInvoiceNo());
    }

    /** Generic balance adjuster with note (positive = debit, negative = credit). */
    public void adjustBalance(double amount, String note) {
        balance = Math.round((balance + amount) * 100.0) / 100.0;
        if (transactionHistory != null) {
            transactionHistory.addTransaction(amount, note);
        }
    }
    /** Convenience: debit/credit without note. */
    public void addBalance(double amount) {
        balance = Math.round((balance + amount) * 100.0) / 100.0;
    }

     /** Records a payment; payment reduces balance (may go negative == credit). */
    public void payBalance(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        adjustBalance(-amount, "Payment");
    }

    @Override
    public String toString() {
        return String.format("Client[ID=%s, Name=%s, Address=%s, Balance=%.2f]",
                id, name, address, balance);
    }
}
