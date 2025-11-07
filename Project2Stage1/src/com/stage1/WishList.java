package com.stage1;

import java.util.ArrayList;
import java.util.Iterator;

public class WishList implements Iterable<WishListItem>
{
    private ArrayList<WishListItem> wishListItems;

    public WishList()
    {
        wishListItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity)
    {
        WishListItem item = new WishListItem(product, quantity);
        wishListItems.add(item);
    }

    public boolean removeItem(String productId)
    {
       return wishListItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public boolean hasItem(String productId)
    {
        return wishListItems.stream().anyMatch(item -> item.getProduct().getId().equals(productId));
    }

    @Override
    public Iterator<WishListItem> iterator()
    {
        ArrayList<WishListItem> wishListCopy = new ArrayList<>();
        for (WishListItem item : wishListItems)
        {
            wishListCopy.add(new WishListItem(item.getProduct(), item.getQuantity()));
        }
        return wishListCopy.iterator();
    }
}
