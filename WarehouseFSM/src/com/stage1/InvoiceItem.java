package com.stage1;

public class InvoiceItem {
    private final String productId;
    private final String productName;
    private final int quantity;
    private final double unitPrice;

    public InvoiceItem(Product product, int quantity, double unitPrice) {
        if (product == null) throw new IllegalArgumentException("product required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity > 0 required");
        if (unitPrice <= 0) throw new IllegalArgumentException("unitPrice > 0 required");
        this.productId = product.getId();
        this.productName = product.getName();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }

    public double getLineTotal() {
        return Math.round(quantity * unitPrice * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%s x%d @ %.2f = %.2f",
                productName, quantity, unitPrice, getLineTotal());
    }
}
