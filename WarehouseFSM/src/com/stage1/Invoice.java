package com.stage1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Invoice {
    private static int nextNo = 1;

    private final String invoiceNo;
    private final String clientId;
    private final LocalDateTime createdAt;
    private final List<InvoiceItem> items = new ArrayList<>();
    private double total = 0.0;

    public Invoice(String clientId) {
        if (clientId == null || clientId.isBlank())
            throw new IllegalArgumentException("clientId required");
        this.invoiceNo = "I" + (nextNo++);
        this.clientId = clientId;
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(InvoiceItem item) {
        if (item == null) throw new IllegalArgumentException("item required");
        items.add(item);
        total = Math.round((total + item.getLineTotal()) * 100.0) / 100.0;
    }

    public String getInvoiceNo() { return invoiceNo; }
    public String getClientId() { return clientId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<InvoiceItem> getItems() { return Collections.unmodifiableList(items); }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return String.format("Invoice[%s, client=%s, total=%.2f, items=%d]",
                invoiceNo, clientId, total, items.size());
    }
}
