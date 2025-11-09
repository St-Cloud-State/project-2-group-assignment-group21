package com.stage1;

import java.io.*;

public class ManagerMenuState extends UIState {
    private static ManagerMenuState instance;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final int EXIT = 0;
    private static final int ADD_PRODUCT = 1;
    private static final int SHOW_WAITLIST = 2;
    private static final int RECEIVE_SHIPMENT = 3;
    private static final int BECOME_CLERK = 4;
    private static final int HELP = 5;

    private ManagerMenuState() { super(); }

    public static ManagerMenuState instance() {
        if (instance == null) instance = new ManagerMenuState();
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
        String input = getToken(prompt + " (Y|y for yes)");
        return input.equalsIgnoreCase("y");
    }

    private void help() {
        System.out.println("\n--- MANAGER MENU ---");
        System.out.println(EXIT + " to Logout");
        System.out.println(ADD_PRODUCT + " to Add a product");
        System.out.println(SHOW_WAITLIST + " to Display product waitlist");
        System.out.println(RECEIVE_SHIPMENT + " to Receive shipment");
        System.out.println(BECOME_CLERK + " to Become a clerk");
        System.out.println(HELP + " for help");
    }

    private int getCommand() {
        do {
            int cmd = getNumber("Enter command (" + HELP + " for help)");
            if (cmd >= EXIT && cmd <= HELP) return cmd;
            System.out.println("Invalid command.");
        } while (true);
    }

    private void addProduct() {
        String name = getToken("Enter product name");
        int qty = getNumber("Enter quantity");
        double price = Double.parseDouble(getToken("Enter price"));
        Product p = new Product(name, qty, price);
        warehouse.addProduct(p);
        System.out.println("Product added: " + p);
    }

    private void showWaitlist() {
        String pid = getToken("Enter Product ID");
        Product p = warehouse.findProductById(pid);
        if (p == null) {
            System.out.println("Invalid product ID.");
            return;
        }
        System.out.println("Waitlist for " + p.getName() + ":");
        for (WaitListItem item : p.getWaitList()) System.out.println(item);
    }

    private void receiveShipment() {
        String pid = getToken("Enter Product ID");
        Product p = warehouse.findProductById(pid);
        if (p == null) {
            System.out.println("Invalid product ID.");
            return;
        }
        int qty = getNumber("Enter quantity received");
        warehouse.processShipment(p, qty);
        System.out.println("Shipment processed. New stock for " + p.getName() + ": " + p.getQuantity());
    }

    private void becomeClerk() {
        System.out.println("Switching to clerk menu...");
        context.changeState(UIContext.CLERK_STATE);
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
                case ADD_PRODUCT: addProduct(); break;
                case SHOW_WAITLIST: showWaitlist(); break;
                case RECEIVE_SHIPMENT: receiveShipment(); break;
                case BECOME_CLERK: becomeClerk(); break;
                case HELP: help(); break;
            }
        }
    }

    @Override
    public void run() { process(); }
}
