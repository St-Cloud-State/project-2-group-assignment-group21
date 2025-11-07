package com.stage1;

import java.util.Scanner;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;

public class UserInterface {

    private WarehouseSystem warehouse;
    private Scanner scanner;

    public static void main(String[] args)
    {
        new UserInterface().start();
    }

    public UserInterface()
    {
        warehouse = new WarehouseSystem();
        scanner = new Scanner(System.in);
    }

    public void start()
    {
        boolean running = true;
        System.out.println("Welcome to Warehouse Management System");

        while (running) {
            System.out.println("Select an option:");
            System.out.println("0. Exit");
            System.out.println("1. Create Client");
            System.out.println("2. Create Product");
            System.out.println("3. List Clients");
            System.out.println("4. List Products");
            System.out.println("5. Add Product to Wishlist");
            System.out.println("6. List Client Wishlist");
            System.out.println("7. List Product Waitlist");
            System.out.println("8. List Client Invoices");
            System.out.println("9. View Client Transaction History");
            System.out.println("10. Place Order");
            System.out.println("11. Pay Balance");
            System.out.println("12. Receive Shipment");

            System.out.print("Enter option: ");

            String input = scanner.nextLine();
            int choice;
            try
            {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 0: // Exit
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                case 1:
                    addClient();
                    break;

                case 2:
                    addProduct();
                    break;

                case 3: // List all clients
                    System.out.println("Client List:");
                    for(Client c : warehouse.getClients())
                    {
                        System.out.println(c.toString());
                    }
                    break;

                case 4: // List all products
                    System.out.println("Product List:");
                    for (Product p : warehouse.getProducts())
                    {
                        System.out.println(p.toString());
                    }
                    break;
                case 5:
                    addProductToWishlist();
                    break;

                case 6:
                    listClientWishlist();
                    break;

                case 7:
                    listProductWaitlist();
                    break;

                case 8:
                    listClientInvoices();
                    break;

                case 9:
                    listClientTransactions();
                    break;

                case 10:
                    placeOrder();
                    break;

                case 11:
                    payBalance();
                    break;

                case 12:
                    receiveShipment();
                    break;

                default:
                    System.out.println("Invalid option. Please select between 0 and 12.");
                    break;
            }

            System.out.println("Press enter to continue...");
            scanner.nextLine();
        }

        scanner.close();
    }

    private boolean getBoolInput()
    {
        boolean result = false;
        boolean valid = true;
        do
        {
            valid = true;
            String input = scanner.nextLine();
            if (input.toLowerCase().startsWith("y")) result = true;
            else if (input.toLowerCase().startsWith("n")) result = false;
            else {
                System.out.println("Invalid input. Please enter a yes/no or y/n");
                valid = false;
            }
        } while (!valid);
        return result;
    }

    private int getIntInput()
    {
        while (!scanner.hasNextInt())
        {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    private int getIntInput(IntPredicate validator, String errorMessage)
    {
        int input = getIntInput();
        while (!validator.test(input))
        {
            System.out.println(errorMessage);
            input = getIntInput();
        }
        return input;
    }

    private double getDoubleInput()
    {
        while (!scanner.hasNextDouble())
        {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
        double result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }

    private double getDoubleInput(DoublePredicate validator, String errorMessage)
    {
        double input = getDoubleInput();
        while (!validator.test(input))
        {
            System.out.println(errorMessage);
            input = getDoubleInput();
        }
        return input;
    }

    private Client getClientInput()
    {
        Client client;
        do
        {
            System.out.print("Enter Client ID: ");
            String clientId = scanner.nextLine();
            client = warehouse.findClientById(clientId);
            if (client == null) System.out.println("Client not found. Please try again.");
        } while (client == null);
        return client;
    }

    private Product getProductInput()
    {
        Product product;
        do
        {
            System.out.print("Enter Product ID: ");
            String productId = scanner.nextLine();
            product = warehouse.findProductById(productId);
            if (product == null) System.out.println("Product not found. Please try again.");
        } while (product == null);
        return product;
    }

    private void addClient()
    {
        System.out.print("Enter Client Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Client Address: ");
        String address = scanner.nextLine();
        Client newClient = new Client(name, address);
        warehouse.addClient(newClient);
        System.out.println("Client Created: " + newClient);
    }

    private void addProduct() {
        System.out.print("Enter Product Name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = getIntInput(i -> i > 0, "Please enter a number greater than 0.");
        System.out.print("Enter Unit Price: ");
        double price = getDoubleInput(d -> d > 0, "Please enter a number greater than 0.");
        Product newProduct = new Product(productName, quantity, price);
        warehouse.addProduct(newProduct);
        System.out.println("Product Created: " + newProduct);
    }

    private void addProductToWishlist() {
        Client client = getClientInput();
        boolean addMore = false;
        do {
            Product product = getProductInput();
            if (client.getWishList().hasItem(product.getId()))
            {
                System.out.println("Product already in wishlist. Please try again.");
                return;
            }
            System.out.print("Enter quantity: ");
            int quantity = getIntInput(i -> i > 0, "Please enter a number greater than 0.");
            warehouse.addWishlistItem(client.getId(), product.getId(), quantity);
            System.out.println("Added " + product.getPrice() + " " + product.getName() + " to " + client.getName() + "'s wishlist");
            System.out.println("Do you want to add another item to " + client.getName() + "'s wishlist? (y/n)");
            addMore = getBoolInput();
        } while (addMore);
    }

    private void listClientWishlist() {
        Client client = getClientInput();
        System.out.println("Wishlist for: " + client.getName());
        for (WishListItem wishListItem : client.getWishList())
        {
            System.out.println(wishListItem.toString());
        }
    }

    private void listProductWaitlist() {
        Product product = getProductInput();
        System.out.println("Waitlist for: " + product.getName());
        for (WaitListItem waitListItem : product.getWaitList())
        {
            System.out.println(waitListItem.toString());
        }
    }

    private void listClientInvoices() {
        Client client = getClientInput();
        System.out.println("Invoices for: " + client.getName());
        for (Invoice invoice : client.getInvoices())
        {
            System.out.println(invoice.toString());
            for (InvoiceItem invoiceItem : invoice.getItems())
            {
                System.out.println(invoiceItem.toString());
            }
        }
    }

    private void placeOrder()
    {
        Client client = getClientInput();
        System.out.println("Placing order for: " + client.getName());
        Invoice invoice = new Invoice(client.getId());
        for (WishListItem item : client.getWishList())
        {
            System.out.println("Select an option for " + item.getProduct().getName() + ", Quantity: " + item.getQuantity());
            System.out.println("1. Order item");
            System.out.println("2. Change quantity");
            System.out.println("3. Remove item");
            int input = getIntInput(i -> i>0 && i<4, "Please enter a valid option (1-3).");
            switch (input)
            {
                case 3:
                    client.getWishList().removeItem(item.getProduct().getId());
                    System.out.println("Item removed from wishlist.");
                    break;
                case 2:
                    System.out.println("Please enter the new quantity: ");
                    int quantity = getIntInput(i -> i > 0, "Please enter a number greater than 0.");
                    item.setQuantity(quantity);
                case 1:
                    int result = warehouse.orderProduct(client.getId(), item, invoice);
                    if  (result == 0) System.out.println("Item order placed successfully.");
                    else if (result == 1) System.out.println("Item order partially fulfilled. Remaining quantity waitlisted.");
                    else if (result == 2) System.out.println("Item out of stock. Order waitlisted.");
                    break;
            }
        }
        if (invoice.getItems().isEmpty())
        {
            System.out.println("Reached end of wishlist. No items ordered.");
            return;
        }
        warehouse.recordInvoice(client, invoice);
        System.out.println("Reached end of wishlist. Invoice created.");
    }

    private void payBalance()
    {
        Client client = getClientInput();
        String message = String.format("Paying balance for: %s (Current balance: $%.2f)\nPlease enter an amount to pay: ", client.getName(), client.getBalance());
        System.out.println(message);
        double amount = getDoubleInput(d -> d > 0, "Please enter a number greater than 0.");
        client.payBalance(amount);
        System.out.println("Payment successful. New balance: " + client.getBalance());
    }

    private void listClientTransactions()
    {
        Client client = getClientInput();
        for (Transaction transaction : client.getTransactions())
        {
            System.out.println(transaction.toString());
        }
    }

    private void receiveShipment()
    {
        Product product = getProductInput();
        System.out.println("Please enter the quantity of the shipment: ");
        int quantity =  getIntInput(i -> i > 0, "Please enter a number greater than 0.");
        int quantityRemaining = warehouse.processShipment(product, quantity);
        System.out.println("Shipment successful. Remaining stock after fulfilling backorders: " + quantityRemaining);
    }
}
