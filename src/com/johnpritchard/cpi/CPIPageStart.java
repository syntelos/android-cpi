/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public final class CPIPageStart
    extends ViewPage2D
{
    public final static CPIPageStart Instance = new CPIPageStart();


    private final static int INTRO     = 0;
    private final static int PRACTICE  = 1;
    private final static int INVENTORY = 2;
    private final static int COMPLETED = 3;
    private final static int SHARING   = 4;
    private final static int ABOUT     = 5;


    private CPIPageStart(){
        super(new ViewPage2DComponent[]{
                new CPIButtonIntro(),
                new CPIButtonPractice(),
                new CPIButtonInventory(),
                new CPIButtonCompleted(),
                new CPIButtonSharing(),
                new CPIButtonAbout(),
            });
    }

    @Override
    protected void init(){

        group_vertical();
    }
    @Override
    protected int first(){

        return PRACTICE;
    }
    @Override
    public String name(){
        return Page.start.name();
    }
    @Override
    public Page value(){
        return Page.start;
    }
    @Override
    public void input(InputScript in){

        final Input type = in.type();

        if (Input.Enter == type){

            switch(enter()){
            case INTRO:
                view.script(Page.intro);
                break;
            case PRACTICE:
                view.script(Page.examples);
                break;
            case INVENTORY:
                view.script(Page.inventory);
                break;
            case COMPLETED:
                view.script(Page.view);
                break;
            case SHARING:
                view.script(Page.sharing);
                break;
            case ABOUT:
                view.script(Page.about);
                break;
            }
        }
        else {
            super.input(in);
        }
    }
}
