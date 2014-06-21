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
    private final static int Terminal = (CPIInventory.Size-1);


    private final int index;
    private final CPIInventory input;
    private final boolean completion;


    public CPIPostInput(int index, CPIInventory input){
        super();
        if (-1 < index && null != input){
            this.index = index;
            this.input = input;
            this.completion = (Terminal == index);
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
            else if (this.completion){

                CPIDatabase.Completion(this.index,this.input);

                ViewAnimation.Script(Page.view);
            }
            else if (CPIDatabase.Input(this.index,this.input)){

                CPIPageInventory.Input();
            }
            else {
                throw new IllegalStateException();
            }
        }
        catch (Exception exc){
            error("post",exc);

            ViewAnimation.Script(Page.start);
        }
    }
}
