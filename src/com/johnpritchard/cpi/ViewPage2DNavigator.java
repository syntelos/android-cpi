/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Navigational indicator reflects navigation but is never a member of
 * the set of view page navigational components.
 */
public final class ViewPage2DNavigator
    extends RectF
    implements ViewPage2DComponent
{


    protected final String baseName;



    public ViewPage2DNavigator(){
        super();
        baseName = ObjectLog.Basename(this.getClass().getName());
    }
    public ViewPage2DNavigator(float left, float top, float right, float bottom){
        this();
        set(left,top,right,bottom);
    }

    public String getName(){
        return baseName;
    }
    public void setName(String name){
        throw new UnsupportedOperationException();
    }
    public boolean hasSelection(){
        return false;
    }
    public boolean hasSelectionGroup(){
        return false;
    }
    public ViewPageOperatorSelection getSelection(){
        return null;
    }
    public ViewPageOperatorGroup getSelectionGroup(){
        return null;
    }
    public void setSelection(ViewPageOperatorSelection selection){
        throw new UnsupportedOperationException();
    }
    public RectF bounds(){

        return this;
    }
    public final float getCX(){

        final float cx0 = this.left;
        final float cx1 = this.right;

        return (cx0+cx1)/2.0f;
    }
    public final float getCY(){

        final float cy0 = this.top;
        final float cy1 = this.bottom;

        return (cy0+cy1)/2.0f;
    }
    public final float getX(){

        return this.left;
    }
    public final float getY(){

        return this.top;
    }
    public final float getWidth(){

        return (this.right-this.left);
    }
    public final float getHeight(){

        return (this.bottom-this.top);
    }
    public final void translate(float x, float y){

        if (0.0f != Epsilon.Z(x) || 0.0f != Epsilon.Z(y)){

            this.left += x;
            this.top += y;
        }
    }
    public final void scale(float s){

        if (0.0f < Epsilon.Z(s) && 1e-7f < Math.abs(1.0f-s)){

            Matrix m = new Matrix();
            {
                m.setScale(s,s);
            }
            this.transform(m);
        }
    }
    public void transform(Matrix m){

        float[] src = {left,top,right,bottom};
        float[] dst = {0f,0f,0f,0f};

        m.mapPoints(dst,0,src,0,2);

        left   = dst[0];
        top    = dst[1];
        right  = dst[2];
        bottom = dst[3];
    }
    /**
     * Component coordinate space
     */
    public final float distance(float cx, float cy){

        final float dx = (cx-getCX());
        final float dy = (cy-getCY());

        return (float)Math.sqrt(Epsilon.Z(dx*dx) + Epsilon.Z(dy*dy));
    }
    public final float distance(ViewPageComponent c){

        if (c == this){

            return 0.0f;
        }
        else {
            return distance(c.getCX(),c.getCY());
        }
    }
    public final Input direction(ViewPageComponent c){

        return direction(c.getCX(),c.getCY());
    }
    public final boolean isCurrent(){
        return false;
    }
    public void setCurrent(){
    }
    public void clearCurrent(){
    }
    public ViewPage2DClip clip(){
        throw new UnsupportedOperationException();
    }
    public final void clearCardinals(){
    }
    public final int countCardinals(){
        return 0;
    }
    public final void setCardinal(Input direction, ViewPage2DComponent component){
        throw new UnsupportedOperationException();
    }
    public final ViewPage2DComponent getCardinal(Input direction){
        return null;
    }
    public final Input direction(float x, float y){
        throw new UnsupportedOperationException();
    }
    public void draw(Canvas c){
    }
}
