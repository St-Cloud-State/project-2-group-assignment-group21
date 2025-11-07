package com.stage1;

import java.io.*;
import java.util.*;

public class ClientMenuState extends UIState {
    private static ClientMenuState instance;
    private static WarehouseSystem warehouse = new WarehouseSystem();
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // menu options
    private static final int EXIT = 0;
    private static final int SHOW_DETAILS = 1;
    private static final int SHOW_PRODUCTS = 2;
    private static final int SHOW_TRANSACTIONS = 3;
    private static final int ADD_WISHLIST = 4;
    private static final int DISPLAY_WISHLIST = 5;
    private static final int PLACE_ORDER = 6;
    private static final int HELP = 7;

    private ClientMenuState() { super(); }

    public static ClientMenuState instance() {
        if (instance == null) instance = new ClientMenuState();
        return instance;
    }

    private String getToken(String prompt) {
        do {
            try {
                System.out.print(prompt + ": ");
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) return line.trim();
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    private int getNumber(String prompt) {
        do {
            try {
                return Integer.parseInt(getToken(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (true);
    }

    private boolean yesOrNo(String prompt) {
        String input = getToken(prompt + " (Y|y for yes, anything else for no)");
        return input.equalsIgnoreCase("y");
    }

    private void help() {
        System.out.println("\n--- CLIENT MENU COMMANDS ---");
        System.out.println(EXIT + " to Logout");
        System.out.println(SHOW_DETAILS + " to Show client details");
        System.out.println(SHOW_PRODUCTS + " to Show product list");
        System.out.println(SHOW_TRANSACTIONS + " to Show transaction history");
        System.out.println(ADD_WISHLIST + " to Add product to wishlist");
        System.out.println(DISPLAY_WISHLIST + " to Display wishlist");
        System.out.println(PLACE_ORDER + " to Place an order");
        System.out.println(HELP + " for help");
    }

    private int getCommand() {
        do {
            int cmd = getNumber("Enter command (" + HELP + " for help)");
            if (cmd >= EXIT && cmd <= HELP) return cmd;
            System.out.println("Invalid command. Try again.");
        } while (true);
    }

    private void showDetails() {
        Client c = warehouse.findClientById(context.getClientID());
        if (c != null) System.out.println(c);
        else System.out.println("Client not found.");
    }

    private void showProducts() {
        System.out.println("--- Available Products ---");
        for (Product p : warehouse.getProducts()) {
            System.out.println(p);
        }
    }

    private void showTransactions() {
        Client c = warehouse.findClientById(context.getClientID());
        if (c != null && c.getInvoices() != null) {
            for (Invoice inv : c.getInvoices()) System.out.println(inv);
        } else System.out.println("No transactions found.");
    }

    private void addWishlist() {
        Client c = warehouse.findClientById(context.getClientID());
        String pid = getToken("Enter Product ID");
        Product p = warehouse.findProductById(pid);
        if (p == null) {
            System.out.println("Invalid Product ID.");
            return;
        }
        int qty = getNumber("Enter quantity");
        c.getWishList().addItem(p, qty);
        System.out.println(qty + " x " + p.getName() + " added to wishlist.");
    }

    private void displayWishlist() {
        Client c = warehouse.findClientById(context.getClientID());
        System.out.println("--- Wishlist for " + c.getName() + " ---");
        for (WishListItem item : c.getWishList()) System.out.println(item);
    }

    private void placeOrder() {
        Client c = warehouse.findClientById(context.getClientID());
        Invoice inv = new Invoice(c.getId());
        for (WishListItem item : c.getWishList()) warehouse.orderProduct(c.getId(), item, inv);
        if (!inv.getItems().isEmpty()) {
            warehouse.recordInvoice(c, inv);
            System.out.println("Order placed successfully! Invoice total: $" + inv.getTotal());
        } else System.out.println("No items were processed.");
    }

    private void terminate(int nextState) {
        context.changeState(nextState);
    }

    public void process() {
        help();
        boolean done = false;
        while (!done) {
            switch (getCommand()) {
                case EXIT: done = true; terminate(UIContext.LOGIN_STATE); break;
                case SHOW_DETAILS: showDetails(); break;
                case SHOW_PRODUCTS: showProducts(); break;
                case SHOW_TRANSACTIONS: showTransactions(); break;
                case ADD_WISHLIST: addWishlist(); break;
                case DISPLAY_WISHLIST: displayWishlist(); break;
                case PLACE_ORDER: placeOrder(); break;
                case HELP: help(); break;
            }
        }
    }

    @Override
    public void run() { process(); }
}
