/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.net.Uri;

/**
 * 
 */
public class CPIPostActionView2D
    extends Object
    implements Runnable
{

    private final Uri uri;


    public CPIPostActionView2D(Uri uri){
        super();
        if (null != uri){
            this.uri = uri;
        }
        else {
            throw new IllegalArgumentException();
        }
    }


    public void run(){
        try {
            CPI.ActionView2D(uri);
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
