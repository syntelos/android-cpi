/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * 
 */
public final class CPITextInventory
    extends ViewPage2DComponentPath
{

    protected final RectF dst = new RectF();

    protected final RectF src = new RectF();

    protected final Matrix center = new Matrix();


    public CPITextInventory(String text){
        super();
        /*
         * not calling setText
         */
        this.fill.getTextPath(text,0,text.length(),0.0f,TextSize,this.path);
        this.appendName(text);
    }


    /**
     * Used by {@link CPIPageInventory} 
     */
    protected void dst(RectF dst){

        this.dst.set(dst);
    }
    /**
     * Used by {@link CPIPageInventory} 
     */
    @Override
    public ViewPage2DComponentPath setText(String text){

        this.reset();

        this.fill.getTextPath(text,0,text.length(),0.0f,TextSize,this.path);

        this.path.computeBounds(this.src,true);

        return this;
    }
    /**
     * Used by {@link CPIPageInventory} 
     */
    protected void center(RectF src){

        this.src.top = src.top;
        this.src.bottom = src.bottom;

        this.center.setRectToRect(this.src,this.dst,Matrix.ScaleToFit.CENTER);

        this.path.transform(this.center);

        this.path.computeBounds(this.bounds,true);
    }
    @Override
    public void draw(Canvas c){
        c.save();

        c.drawPath(this.path,this.fill);

        c.restore();
    }

}
