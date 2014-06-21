/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPageAbout
    extends ViewPage2D
{

    public final static CPIPageAbout Instance = new CPIPageAbout();



    private CPIPageAbout(){
        super(new ViewPage2DComponent[]{
                new ViewPage2DTextMultiline("CPI for Android\nCopyright (C) 2014 John Pritchard.\nAll rights reserved.")
            });
    }


    @Override
    public String name(){
        return Page.about.name();
    }
    @Override
    public Page value(){
        return Page.about;
    }
    @Override
    public void input(InputScript in){

        switch(in.type()){

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
