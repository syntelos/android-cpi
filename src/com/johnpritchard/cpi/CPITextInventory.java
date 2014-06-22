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

    protected final RectF src = new RectF();

    protected final Matrix center = new Matrix();


    public CPITextInventory(String text){
        super();
        /*
         * not calling setText
         */
        this.fill.getTextPath(text,0,text.length(),0.0f,TextSize,this.path);
        this.path.computeBounds(this.src,true);

        this.appendName(text);
    }


    /**
     * Used by {@link CPIInventoryCatalog} 
     */
    protected CPITextInventory group(RectF group){

        this.src.top = group.top;
        this.src.bottom = group.bottom;

        this.center.setRectToRect(this.src,group,Matrix.ScaleToFit.CENTER);

        this.path.transform(this.center);

        this.bounds.set(group);

        return this;
    }
    /**
     * Used by {@link CPIPageInventory} 
     */
    protected CPITextInventory center(RectF dst){

        this.center.setRectToRect(this.bounds,dst,Matrix.ScaleToFit.CENTER);

        this.path.transform(this.center);

        this.bounds.set(dst);

        return this;
    }
    @Override
    public void draw(Canvas c){
        c.save();

        c.drawPath(this.path,this.fill);

        c.restore();
    }
    @Override
    public RectF bounds(){

        return this.bounds;
    }
    @Override
    public void transform(Matrix m){

        path.transform(m);

        Transform(bounds,m);
    }
    @Override
    public void reset(){
    }
    @Override
    public ViewPage2DComponentPath setText(String text){
        return this;
    }
    @Override
    public void group(final RectF dim, final float pad){
    }
}
