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

    protected final static ViewPage2DComponentRect Clip_ST = CPIViz.Instance.clip_ST;
    protected final static ViewPage2DComponentRect Clip_NT = CPIViz.Instance.clip_NT;
    protected final static ViewPage2DComponentRect Clip_NF = CPIViz.Instance.clip_NF;
    protected final static ViewPage2DComponentRect Clip_SF = CPIViz.Instance.clip_SF;

    public static void View(){

        CPIViz.Update();

        CPIOutputTitle.Update();
    }


    protected final static int VZ = 0;
    protected final static int OT = 1;
    protected final static int ST = 2;
    protected final static int NT = 3;
    protected final static int NF = 4;
    protected final static int SF = 5;


    private CPIPageView(){
        super(new ViewPage2DComponent[]{
                CPIViz.Instance,
                CPIOutputTitle.Instance,
                CPIViz.Instance.Clip_ST,
                CPIViz.Instance.Clip_NT,
                CPIViz.Instance.Clip_NF,
                CPIViz.Instance.Clip_SF
            });
    }


    @Override
    protected void init(){

        group_vertical(0,2);
    }
    @Override
    protected void layout(){

        scale(0,2);
    }
    @Override
    protected void focus(){
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

        // switch(in.type()){

        // case Left:
        //     view.script(new InputScript.Database(InputScript.Database.Op.CompletedPrev));
        //     return;

        // case Right:
        //     view.script(new InputScript.Database(InputScript.Database.Op.CompletedNext));
        //     return;

        // case Enter:
        //     CPI.StartActivity(Page.start);
        //     return;

        // default:
        //     super.input(in);
        //     return;
        // }

        switch (in.type()){

        case Enter:

            switch(enter()){
            case ST:

                return;
            case NT:

                return;
            case NF:

                return;
            case SF:

                return;
            default:
                return;
            }

        default:
            info("input "+in);
            super.input(in);
            return;
        }
    }
    @Override
    protected void current(ViewPage2DComponent next){
        super.current(next);

        if (next == Clip_ST){
            CPIViz.Instance.select(CPIQuadrant.ST);
        }
        else if (next == Clip_NT){
            CPIViz.Instance.select(CPIQuadrant.NT);
        }
        else if (next == Clip_NF){
            CPIViz.Instance.select(CPIQuadrant.NF);
        }
        else if (next == Clip_SF){
            CPIViz.Instance.select(CPIQuadrant.SF);
        }
        else {
            CPIViz.Instance.select(null);
        }
    }
    @Override
    protected boolean navigationInclude(int index, ViewPage2DComponent c){
        return (1 < index);
    }
    @Override
    protected int first(){
        return -1;
    }
}
