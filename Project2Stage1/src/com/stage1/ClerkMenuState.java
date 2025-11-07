package com.stage1;

public class ClerkMenuState implements UIState {
    @Override
    public void run() {
        System.out.println("\n--- CLERK MENU ---");
        System.out.println("(placeholder – implemented by teammate later)");
        UIContext.instance().changeState(UIContext.LOGIN_STATE);
    }
}
