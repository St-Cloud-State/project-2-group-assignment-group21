package com.stage1;

import java.util.Scanner;

/**
 * Thomas Hoerger 2025
 * LoginState.java – Opening (Login) state for the Warehouse FSM interface.
 * Provides text-based options to log in as Client, Clerk, or Manager.
 */
public class LoginState extends UIState {

    @Override
    public void run() {
        UIContext context = UIContext.instance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n====== WAREHOUSE SYSTEM LOGIN ======");
        System.out.println("1) Login as Client");
        System.out.println("2) Login as Clerk");
        System.out.println("3) Login as Manager");
        System.out.println("0) Exit");
        System.out.println("====================================");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": // Login as Client
                System.out.print("Enter Client ID: ");
                String clientID = scanner.nextLine().trim();
                if (clientID.isEmpty()) {
                    System.out.println("Client ID cannot be empty.");
                    run();
                    return;
                }
                if (context.isValidClient(clientID)) {
                    context.setClientID(clientID);
                    System.out.println("Welcome, " + clientID + "!");
                    context.changeState(UIContext.CLIENT_STATE);
                } else {
                    System.out.println("Invalid Client ID. Please try again.");
                    run();
                }
                break;

            case "2": // Login as Clerk
                System.out.println("Logging in as Clerk...");
                context.changeState(UIContext.CLERK_STATE);
                break;

            case "3": // Login as Manager
                System.out.println("Logging in as Manager...");
                context.changeState(UIContext.MANAGER_STATE);
                break;

            case "0": // Exit program
                System.out.println("Exiting system. Goodbye!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid selection. Please enter 1–3 or 0.");
                run();
                break;
        }
    }
}
