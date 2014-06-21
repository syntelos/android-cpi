/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public class CPIPostPractice
    extends ObjectLog
    implements Runnable
{


    public CPIPostPractice(){
        super();
    }


    public void run(){
        try {
            CPIDatabase.Practice();

            CPIPagePractice.View();

            ViewAnimation.Script(Page.practice);
        }
        catch (Exception exc){
            error("post",exc);
        }
    }

}
