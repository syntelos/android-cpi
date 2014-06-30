/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIPostCompleted
    extends ObjectLog
    implements Runnable
{


    public CPIPostCompleted(){
        super();
    }


    public void run(){
        try {
            CPIDatabase.Completed();

            CPI.StartActivity(Page.view);
        }
        catch (Exception exc){
            error("post",exc);
        }
    }

}
