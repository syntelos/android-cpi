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





    private CPIPageView(){
        super(new ViewPage2DComponent[]{
                new CPIViz()
            });
    }


    @Override
    protected void layout(){

        image();

        scale();
    }
    protected void image(){

        final CPIViz image = (CPIViz)components[0];

        image.update();
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

        case Back:
        case Up:
        case Down:
        case Enter:
            view.script(Page.start);
            return;

        default:
            return;
        }
    }
}
