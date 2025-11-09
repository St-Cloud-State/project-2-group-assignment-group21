package com.stage1;

public class UIContext
{
    private int currentState;
    private UIState[] states;
    private int[][] nextState;
    private WarehouseSystem warehouse;
    private String clientID;
    private int userType;
    private static UIContext _instance;

    public static final int LOGIN_STATE = 0;
    public static final int CLIENT_STATE = 1;
    public static final int CLERK_STATE = 2;
    public static final int MANAGER_STATE = 3;

    public static final int EXIT_STATE = -1;
    public static final int ERROR_STATE = -2;

    public static final int CLIENT_USER = 0;
    public static final int CLERK_USER = 1;
    public static final int MANAGER_USER = 2;

    public UIContext()
    {
        _instance = this;
        warehouse = new WarehouseSystem();
        userType = CLIENT_USER;

        states = new UIState[4];
        states[LOGIN_STATE] = new LoginState();
        states[CLIENT_STATE] = ClientMenuState.instance();
        states[CLERK_STATE] = new ClerkMenuState();
        states[MANAGER_STATE] = ManagerMenuState.instance();

        currentState = 0;

        nextState = new int[4][4];

        nextState[LOGIN_STATE][0] = EXIT_STATE;
        nextState[LOGIN_STATE][1] = CLIENT_STATE;
        nextState[LOGIN_STATE][2] = CLERK_STATE;
        nextState[LOGIN_STATE][3] = MANAGER_STATE;

        nextState[CLIENT_STATE][0] = LOGIN_STATE;
        nextState[CLIENT_STATE][1] = ERROR_STATE;
        nextState[CLIENT_STATE][2] = CLERK_STATE;
        nextState[CLIENT_STATE][3] = ERROR_STATE;

        nextState[CLERK_STATE][0] = LOGIN_STATE;
        nextState[CLERK_STATE][1] = CLIENT_STATE;
        nextState[CLERK_STATE][2] = ERROR_STATE;
        nextState[CLERK_STATE][3] = MANAGER_STATE;

        nextState[MANAGER_STATE][0] = LOGIN_STATE;
        nextState[MANAGER_STATE][1] = ERROR_STATE;
        nextState[MANAGER_STATE][2] = CLERK_STATE;
        nextState[MANAGER_STATE][3] = ERROR_STATE;
    }

    public static UIContext instance()
    {
        if (_instance == null)
        {
            _instance = new UIContext();
        }

        return _instance;
    }

    public void changeState(int transition)
    {
        int newState = nextState[currentState][transition];
        if (newState == EXIT_STATE)
        {
            System.exit(0);
        }
        if (newState == ERROR_STATE)
        {
            System.out.println("Error: Invalid Transition from " + currentState + " to " + transition);
        }
        currentState = newState;
        states[newState].run();
    }

    public int getUserType()
    {
        return userType;
    }

    public void setUserType(int userType)
    {
        this.userType = userType;
    }

    public WarehouseSystem getWarehouse()
    {
        return warehouse;
    }

    public String  getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public void start()
    {
        states[currentState].run();
    }

    public static void main(String[] args)
    {
        UIContext.instance().start();
    }
}
