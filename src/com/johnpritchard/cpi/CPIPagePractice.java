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
    static {
        CPIInventoryCatalog.Init();
    }
    protected final static CPITextInventory[] CatalogExample = {
        (new CPITextInventory("Vanilla").group(CPIInventoryCatalog.GROUP)),
        (new CPITextInventory("Chocolate").group(CPIInventoryCatalog.GROUP))
    };

    public static void View(){

        //(page change process)
    }


    public final static CPIPagePractice Instance = new CPIPagePractice();


    private CPIPagePractice(){
        super();
    }


    @Override
    protected CPITextInventory[] catalog(){

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
