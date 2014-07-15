/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIOutputQuadrant
    extends ViewPage2DTextLabel
    implements ViewPageComponentGroup
{

    public final static CPIOutputQuadrant Instance = new CPIOutputQuadrant();

    public final static void Init(){
    }


    public CPIOutputQuadrant(){
        super();
    }


    protected void update(CPIQuadrant q, float screen_w, float screen_h){

        if (null == q){
            super.setText(null);
        }
        else {
            final float screen_dim = Math.max(screen_w,screen_h);
            final float dim = (screen_dim/10.0f);
            final float left = (screen_dim/20.0f);
            final float right = (left+dim);

            final RectF tgt = new RectF(left,left,right,right);

            setText(q.name());
            {
                final RectF src = new RectF(bounds());

                final Matrix m = new Matrix();
                {
                    m.setRectToRect(src,tgt,Matrix.ScaleToFit.CENTER);
                }
                this.transform(m);
            }
        }
    }
    public void group(RectF dim, float pad){
    }
}
