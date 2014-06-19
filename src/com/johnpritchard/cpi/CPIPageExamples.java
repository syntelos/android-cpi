/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPageExamples
    extends CPIPageInventory
{

    public final static CPIPageExamples Instance = new CPIPageExamples();

    protected final static String[] CatalogExample = {
        "Vanilla",
        "Chocolate"
    };


    private CPIPageExamples(){
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
    protected void input(CPIInventory response){

        if (-1 < enter()){

            view.script(Page.start);
        }
    }
    @Override
    public String name(){
        return Page.examples.name();
    }
    @Override
    public Page value(){
        return Page.examples;
    }
}
