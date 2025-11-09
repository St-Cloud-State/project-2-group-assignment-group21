// Thomas Hoerger - Group 21
// WaitList.java
// Holds a list of clients waiting for this product.

package com.stage1;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WaitList implements Iterable<WaitListItem>, Serializable {
    private List<WaitListItem> items = new LinkedList<>();

    public void addItem(Client client, int quantity) {
        // If client already exists, combine quantities
        for (WaitListItem item : items) {
            if (item.getClient().equals(client)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new WaitListItem(client, quantity));
    }

    public void removeItem(Client client)
    {
        items.removeIf(item -> item.getClient().equals(client));
    }

    public Iterator<WaitListItem> getItems() {
        return items.iterator();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    @Override
    public Iterator<WaitListItem> iterator() {
        return getItems();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "Waitlist is empty.";
        StringBuilder sb = new StringBuilder("Waitlist:\n");
        for (WaitListItem item : items) {
            sb.append("  ").append(item.toString()).append("\n");
        }
        return sb.toString();
    }
    
    // Creates and returns a deep copy of WaitList.
    public WaitList copy() {
        WaitList newList = new WaitList();
        for (WaitListItem item : items) {
            newList.addItem(item.getClient(), item.getQuantity());
        }
        return newList;
    }
}



