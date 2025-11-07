package com.stage1;

public class WarehouseSystem {
    private ClientList clientList;
    private ProductList inventory;

    public WarehouseSystem() {
        clientList = new ClientList();
        inventory = new ProductList();
    }

    public void addClient(Client client) {
        clientList.insert(client);
    }

    public void addProduct(Product product) {
        inventory.insert(product);
    }

    public Client findClientById(String id) {
        return clientList.findById(id);
    }

    public Product findProductById(String id) {
        return inventory.findById(id);
    }

    public void addWishlistItem(String clientId, String productId, int quantity)
    {
        Client client = findClientById(clientId);
        Product product = findProductById(productId);
        client.getWishList().addItem(product, quantity);
    }

    // Returns 0 if order fully fulfilled
    // Returns 1 if order partially fulfilled
    // Returns 2 if order completely out of stock
    public int orderProduct(String clientId, WishListItem item, Invoice invoice)
    {
        Client client = findClientById(clientId);
        Product product = item.getProduct();
        int orderQuantity = item.getQuantity();
        int result = 0;
        if (product.getQuantity() < orderQuantity)
        {
            int shortQuantity = orderQuantity - product.getQuantity();
            orderQuantity = product.getQuantity();
            product.getWaitList().addItem(client, shortQuantity);
            result = 1;
            if (orderQuantity == 0)
            {
                result = 2;
                return result;
            }
        }
        product.setQuantity(product.getQuantity() - orderQuantity);
        invoice.addItem(new InvoiceItem(product, orderQuantity, product.getPrice()));
        return result;
    }

    public ProductList getProducts(){
        return inventory;
    }

    public ClientList getClients(){
        return clientList;
    }

    public void recordInvoice(Client client, Invoice invoice) {
        if (client == null || invoice == null || invoice.getItems().isEmpty()) return;
        client.addInvoice(invoice);
    }

    public int processShipment(Product product, int quantity)
    {
        for (WaitListItem item : product.getWaitList().copy())
        {
            if (quantity <= 0) break;
            int quantityFulfilled = Math.min(quantity, item.getQuantity());
            quantity -= quantityFulfilled;
            Invoice invoice = new Invoice(item.getClient().getId());
            invoice.addItem(new InvoiceItem(product, quantityFulfilled, product.getPrice()));
            item.getClient().addInvoice(invoice);

            item.setQuantity(item.getQuantity() - quantityFulfilled);
            if (item.getQuantity() == 0)
                product.getWaitList().removeItem(item.getClient());
        }
        product.setQuantity(product.getQuantity() + quantity);
        return product.getQuantity();
    }
}
