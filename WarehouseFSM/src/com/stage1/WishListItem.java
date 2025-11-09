package com.stage1;

public class WishListItem
{
    private Product product;
    private int quantity;

    public WishListItem(Product product, int quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString()
    {
        return "WishlistItem[" + product.getId() + ", name=" + product.getName() + ", quantity=" + quantity + ']';
    }
}
