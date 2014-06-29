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
    private final static int ABOUT     = 4;


    private CPIPageStart(){
        super(new ViewPage2DComponent[]{
                new CPIButtonIntro(),
                new CPIButtonPractice(),
                new CPIButtonInventory(),
                new CPIButtonCompleted(),
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
                CPI.StartActivity(Page.intro);
                break;
            case PRACTICE:
                CPI.Post2D(new CPIPostPractice());
                break;
            case INVENTORY:
                CPI.Post2D(new CPIPostInventory());
                break;
            case COMPLETED:
                CPI.Post2D(new CPIPostCompleted());
                break;
            case ABOUT:
                CPI.StartActivity(Page.about);
                break;
            }
        }
        else {
            super.input(in);
        }
    }
}
