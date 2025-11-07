package com.stage1;

public class ManagerMenuState implements UIState {
    @Override
    public void run() {
        System.out.println("\n--- MANAGER MENU ---");
        System.out.println("(placeholder – implemented by teammate later)");
        UIContext.instance().changeState(UIContext.LOGIN_STATE);
    }
}
