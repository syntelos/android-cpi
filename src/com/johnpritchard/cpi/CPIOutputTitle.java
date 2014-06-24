/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIOutputTitle
    extends ViewPage2DTextLabel
    implements ViewPageComponentGroup
{

    public final static CPIOutputTitle Instance = new CPIOutputTitle();

    public final static void Update(){

        Instance.update();
    }


    public CPIOutputTitle(){
        super();

        update();
    }


    protected void update(){
        String text = CPIInventoryRecord.Instance.getTitle();
        if (null != text){

            setText(text);
        }
        else {
            setText(new java.util.Date().toString());
        }
    }
    public void group(RectF dim, float pad){

        final float square = Math.max((dim.bottom-dim.top), (dim.right-dim.left));
        final float x0 = dim.left, y0 = dim.top, x1 = (x0 + square), y1 = (y0 + square);

        final Matrix m = new Matrix();
        /*
         * translate
         */
        m.setTranslate(x0,(y1+pad));

        this.transform(m);
    }
}
