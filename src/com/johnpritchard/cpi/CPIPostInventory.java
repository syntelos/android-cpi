/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIPostInventory
    extends ObjectLog
    implements Runnable
{


    public CPIPostInventory(){
        super();
    }


    public void run(){
        try {
            CPIDatabase.Inventory();

            CPIPageInventory.View();

            ViewAnimation.Script(Page.inventory);
        }
        catch (Exception exc){
            error("post",exc);
        }
    }

}
