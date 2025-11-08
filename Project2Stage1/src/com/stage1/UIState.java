package com.stage1;

public abstract class UIState
{
    protected static UIContext context;
    protected static WarehouseSystem warehouse;
    public UIState()
    {
        context = UIContext.instance();
        warehouse = context.getWarehouse();
    }
    public abstract void run();
}
