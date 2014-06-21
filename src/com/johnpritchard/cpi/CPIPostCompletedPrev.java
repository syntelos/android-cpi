/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIPostCompletedPrev
    extends ObjectLog
    implements Runnable
{


    public CPIPostCompletedPrev(){
        super();
    }


    public void run(){
        try {
            if (CPIDatabase.CompletedPrev()){

                CPIPageView.View();
            }
        }
        catch (Exception exc){
            error("post",exc);
        }
    }

}
