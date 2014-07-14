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



    protected final static int VZ = 0;
    protected final static int OT = 1;
    protected final static int ST = 2;
    protected final static int NT = 3;
    protected final static int NF = 4;
    protected final static int SF = 5;


    private final CPIViz viz;

    private final CPIOutputTitle title;

    private volatile boolean interior;


    private CPIPageView(){
        super(new ViewPage2DComponent[]{
                CPIViz.Instance,
                CPIOutputTitle.Instance,
                CPIViz.Instance.Clip_ST,
                CPIViz.Instance.Clip_NT,
                CPIViz.Instance.Clip_NF,
                CPIViz.Instance.Clip_SF
            });

        viz = CPIViz.Instance;
        title = CPIOutputTitle.Instance;
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

        if (viz.update()){

            title.update();
        }
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

        if (interior){
            /*
             * nav within viz
             */
            switch (in.type()){

            case Enter:
                interior = false;
                current(ViewPage2DComponentAbstract.Nil);
                return;

            default:
                super.input(in);
                return;
            }
        }
        else {
            /*
             * nav over viz
             */
            switch(in.type()){

            case Up:
            case Left:
                view.script(new InputScript.Database(InputScript.Database.Op.CompletedPrev));
                return;

            case Down:
            case Right:
                view.script(new InputScript.Database(InputScript.Database.Op.CompletedNext));
                return;

            case Enter:
                interior = true;
                current(viz.primary);
                return;

            default:
                return;
            }
        }
    }
    protected void current(CPIQuadrant q){
        if (null == q){
            super.current(ViewPage2DComponentAbstract.Nil);
            viz.select(null);
        }
        else {
            switch(q){
            case ST:
                super.current(Clip_ST);
                viz.select(CPIQuadrant.ST);
                break;
            case NT:
                super.current(Clip_NT);
                viz.select(CPIQuadrant.NT);
                break;
            case NF:
                super.current(Clip_NF);
                viz.select(CPIQuadrant.NF);
                break;
            default:
                super.current(Clip_SF);
                viz.select(CPIQuadrant.SF);
                break;
            }
        }
    }
    @Override
    protected void current(ViewPage2DComponent next){
        super.current(next);

        if (null == next){
            viz.select(null);
        }
        else if (next == Clip_ST){
            viz.select(CPIQuadrant.ST);
        }
        else if (next == Clip_NT){
            viz.select(CPIQuadrant.NT);
        }
        else if (next == Clip_NF){
            viz.select(CPIQuadrant.NF);
        }
        else {
            viz.select(CPIQuadrant.SF);
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
