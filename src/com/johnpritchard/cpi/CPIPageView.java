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

        CPIViz.Update();

        CPIOutputTitle.Update();

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

        //info("init/group_vertical");

        group_vertical();

        //info("CPIViz "+CPIViz.Instance.bounds());
        //info("CPIOutputTitle "+CPIOutputTitle.Instance.bounds());
    }
    @Override
    protected void layout(){

        //info("layout/scale");
        scale();

        //info("CPIViz "+CPIViz.Instance.bounds());
        //info("CPIOutputTitle "+CPIOutputTitle.Instance.bounds());
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
            CPI.StartActivity(Page.start);
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
