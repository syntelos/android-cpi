/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public final class CPIOutputTitle
    extends ViewPage2DTextLabel
{

    public final static CPIOutputTitle Instance = new CPIOutputTitle();


    public CPIOutputTitle(){
        super();

        setText("");
    }


    protected void update(){
        String text = CPIInventoryRecord.Instance.getTitle();
        if (null != text){

            setText(text);
        }
        else {
            setText("");
        }
    }
}
