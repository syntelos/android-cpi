/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIPostCompletedNext
    extends ObjectLog
    implements Runnable
{


    public CPIPostCompletedNext(){
        super();
    }


    public void run(){
        try {
            if (CPIDatabase.CompletedNext()){

                CPIPageView.View();
            }
        }
        catch (Exception exc){
            error("post",exc);
        }
    }

}
