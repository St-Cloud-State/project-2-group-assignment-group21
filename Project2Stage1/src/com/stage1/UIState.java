package com.stage1;

public abstract class UIState
{
    protected static UIContext context;
    public UIState()
    {
        context = UIContext.instance();
    }
    public abstract void run();
}
