/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPageView
    extends ViewPage2D
{

    public final static CPIPageView Instance = new CPIPageView();

    public static void View(){

        CPIViz.Instance.update();

        CPIOutputTitle.Instance.update();

        ViewAnimation.Script();
    }



    private CPIPageView(){
        super(new ViewPage2DComponent[]{
                CPIViz.Instance,
                CPIOutputTitle.Instance
            });
    }


    @Override
    protected void init(){

        vertical();
    }
    @Override
    protected void layout(){

        CPIViz.Instance.update();

        scale();
    }
    @Override
    public String name(){
        return Page.view.name();
    }
    @Override
    public Page value(){
        return Page.view;
    }
    @Override
    public void input(InputScript in){

        switch(in.type()){

        case Left:
            CPI.Post2D(new CPIPostCompletedPrev());
            return;

        case Right:
            CPI.Post2D(new CPIPostCompletedNext());
            return;

        case Enter:
            view.script(Page.start);
            return;

        default:
            return;
        }
    }
    @Override
    protected boolean navigationInclude(int index, ViewPage2DComponent c){
        return false;
    }
}
