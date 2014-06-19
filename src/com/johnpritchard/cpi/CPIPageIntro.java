/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPageIntro
    extends ViewPage2D
{

    public final static CPIPageIntro Instance = new CPIPageIntro();





    private CPIPageIntro(){
        super(new ViewPage2DComponent[]{
                new ViewPage2DTextMultiline("From each word pair choose which appeals to you more, or describes you better.")
            });
    }


    @Override
    public String name(){
        return Page.intro.name();
    }
    @Override
    public Page value(){
        return Page.intro;
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
