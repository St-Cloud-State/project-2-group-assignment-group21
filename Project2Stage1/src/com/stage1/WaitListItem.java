// Thomas Hoerger - Group 21
// WaitListItem.java
// Represents a single entry in a product's waitlist.

package com.stage1;

import java.io.Serializable;

public class WaitListItem implements Serializable {
    private Client client;
    private int quantity;

    public WaitListItem(Client client, int quantity) {
        this.client = client;
        this.quantity = quantity;
    }

    public Client getClient() {
        return client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Client ID: " + client.getId() + " | Qty: " + quantity;
    }
}

