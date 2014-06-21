/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPagePractice
    extends CPIPageInventory
{

    public final static CPIPagePractice Instance = new CPIPagePractice();

    protected final static String[] CatalogExample = {
        "Vanilla",
        "Chocolate"
    };

    public static void View(){

        //(page change process)
    }


    private CPIPagePractice(){
        super();
    }


    @Override
    protected String[] catalog(){

        return CatalogExample;
    }
    @Override
    protected void focus(){

        refocus();
    }
    @Override
    public String name(){
        return Page.practice.name();
    }
    @Override
    public Page value(){
        return Page.practice;
    }
}
