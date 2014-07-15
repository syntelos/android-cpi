/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public class CPIPostToastLong2D
    extends Object
    implements Runnable
{

    private final String msg;


    public CPIPostToastLong2D(String msg){
        super();
        if (null != msg){
            this.msg = msg;
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    public void run(){
        try {
            CPI.ToastLong2D(msg);
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
