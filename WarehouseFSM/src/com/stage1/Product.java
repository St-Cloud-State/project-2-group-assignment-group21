package com.stage1;

public class Product {
    private static int nextId = 1;
    private final String id;
    private String name;
    private int quantity;
    private double price;
    private WaitList waitList = new WaitList();

    public Product(String name, int quantity, double price) {
        this.id = "P" + (nextId++);
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public WaitList getWaitList() {
        return waitList;
    }

    @Override
    public String toString() {
        return String.format("Product[ID=%s, Name=%s, Quantity=%d, Price=%.2f]",
                id, name, quantity, price);
    }
}
