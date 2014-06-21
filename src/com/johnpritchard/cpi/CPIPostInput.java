/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIPostInput
    extends ObjectLog
    implements Runnable
{

    private final int index;
    private final CPIInventory input;


    public CPIPostInput(int index, CPIInventory input){
        super();
        if (-1 < index && null != input){
            this.index = index;
            this.input = input;
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    public void run(){
        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        try {
            if (CPIProcess.Practice == inventory.process){

                ViewAnimation.Script(Page.start);
            }
            else if (CPIDatabase.Input(this.index,this.input)){

                CPIPageInventory.Input();
            }
            else {
                ViewAnimation.Script(Page.start);
            }
        }
        catch (Exception exc){
            error("post",exc);
        }
    }
}
