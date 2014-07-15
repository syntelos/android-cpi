/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import static com.johnpritchard.cpi.View2DFontDPad.Char.*;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;

/**
 * 
 */
public final class CPIPageView
    extends ViewPage2D
{

    protected final static ViewPage2DNavigator Navigator = new ViewPage2DNavigator();


    public final static CPIPageView Instance = new CPIPageView();


    protected final static ViewPage2DComponentRect Clip_ST = CPIViz.Instance.clip_ST;
    protected final static ViewPage2DComponentRect Clip_NT = CPIViz.Instance.clip_NT;
    protected final static ViewPage2DComponentRect Clip_NF = CPIViz.Instance.clip_NF;
    protected final static ViewPage2DComponentRect Clip_SF = CPIViz.Instance.clip_SF;

    protected final static Uri U_ST = Uri.parse("http://www.cognitiveprofile.com/st/study");
    protected final static Uri U_NT = Uri.parse("http://www.cognitiveprofile.com/nt/study");
    protected final static Uri U_NF = Uri.parse("http://www.cognitiveprofile.com/nf/study");
    protected final static Uri U_SF = Uri.parse("http://www.cognitiveprofile.com/sf/study");

    protected final static CPIPostActionView2D UA_ST = new CPIPostActionView2D(U_ST);
    protected final static CPIPostActionView2D UA_NT = new CPIPostActionView2D(U_NT);
    protected final static CPIPostActionView2D UA_NF = new CPIPostActionView2D(U_NF);
    protected final static CPIPostActionView2D UA_SF = new CPIPostActionView2D(U_SF);


    protected final static int VZ = 0;
    protected final static int OT = 1;
    protected final static int NA = 2;
    protected final static int ST = 3;
    protected final static int NT = 4;
    protected final static int NF = 5;
    protected final static int SF = 6;


    private final CPIViz viz;

    private final CPIOutputTitle title;

    private final CPIOutputQuadrant quadrant;

    private final ViewPage2DNavigator navigator;

    private volatile boolean interior;


    private CPIPageView(){
        super(new ViewPage2DComponent[]{
                CPIViz.Instance,
                CPIOutputTitle.Instance,
                CPIPageView.Navigator,
                CPIOutputQuadrant.Instance,
                CPIViz.Instance.Clip_ST,
                CPIViz.Instance.Clip_NT,
                CPIViz.Instance.Clip_NF,
                CPIViz.Instance.Clip_SF
            });

        viz = CPIViz.Instance;
        title = CPIOutputTitle.Instance;
        navigator = CPIPageView.Navigator;
        quadrant = CPIOutputQuadrant.Instance;
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

        interior = false;

        if (viz.update()){

            title.update();
        }

        navigator.update(Left,Top,Right,Bottom,Square,width,height);
        quadrant.update(null,width,height);
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

        //info("input "+in);

        if (interior){
            /*
             * nav within viz
             */
            switch (in.type()){

            case Enter:
                /*
                 * This was better interaction without the web links,
                 * but then how to do the web links
                 */
                switch(enter()){
                case ST:
                    //info("interior <post>");
                    CPI.Post2D(UA_ST);
                    return;
                case NT:
                    //info("interior <post>");
                    CPI.Post2D(UA_NT);
                    return;
                case NF:
                    //info("interior <post>");
                    CPI.Post2D(UA_NF);
                    return;
                case SF:
                    //info("interior <post>");
                    CPI.Post2D(UA_SF);
                    return;
                default:
                    //info("interior <deselect>");
                    current(ViewPage2DComponentAbstract.Nil);
                    return;
                }

            default:
                //info("interior <*>");
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
                //info("exterior <prev>");
                view.script(new InputScript.Database(InputScript.Database.Op.CompletedPrev));
                return;

            case Down:
            case Right:
                //info("exterior <next>");
                view.script(new InputScript.Database(InputScript.Database.Op.CompletedNext));
                return;

            case Enter:
                //info("exterior <select>");
                current(viz.primary);
                return;

            default:
                //info("exterior <*>");
                super.input(in);
                return;
            }
        }
    }
    protected void current(CPIQuadrant q){
        if (null == q){
            interior = false;
            super.current(ViewPage2DComponentAbstract.Nil);
            viz.select(null);
            navigator.update(Left,Top,Right,Bottom,Square,width,height);
            quadrant.update(null,width,height);
        }
        else {
            interior = true;
            switch(q){
            case ST:
                super.current(Clip_ST);
                viz.select(CPIQuadrant.ST);
                navigator.update(Left,Top,Square,Square,Circle,width,height);
                quadrant.update(CPIQuadrant.ST,width,height);
                break;
            case NT:
                super.current(Clip_NT);
                viz.select(CPIQuadrant.NT);
                navigator.update(Left,Square,Square,Bottom,Circle,width,height);
                quadrant.update(CPIQuadrant.NT,width,height);
                break;
            case NF:
                super.current(Clip_NF);
                viz.select(CPIQuadrant.NF);
                navigator.update(Square,Square,Right,Bottom,Circle,width,height);
                quadrant.update(CPIQuadrant.NF,width,height);
                break;
            default:
                super.current(Clip_SF);
                viz.select(CPIQuadrant.SF);
                navigator.update(Square,Top,Right,Square,Circle,width,height);
                quadrant.update(CPIQuadrant.SF,width,height);
                break;
            }
        }
    }
    @Override
    protected void current(ViewPage2DComponent next){
        super.current(next);

        if (null == next){
            interior = false;
            viz.select(null);
            navigator.update(Left,Top,Right,Bottom,Square,width,height);
            quadrant.update(null,width,height);
        }
        else {
            interior = true;
            if (next == Clip_ST){
                viz.select(CPIQuadrant.ST);
                navigator.update(Left,Top,Square,Square,Circle,width,height);
                quadrant.update(CPIQuadrant.ST,width,height);
            }
            else if (next == Clip_NT){
                viz.select(CPIQuadrant.NT);
                navigator.update(Left,Square,Square,Bottom,Circle,width,height);
                quadrant.update(CPIQuadrant.NT,width,height);
            }
            else if (next == Clip_NF){
                viz.select(CPIQuadrant.NF);
                navigator.update(Square,Square,Right,Bottom,Circle,width,height);
                quadrant.update(CPIQuadrant.NF,width,height);
            }
            else {
                viz.select(CPIQuadrant.SF);
                navigator.update(Square,Top,Right,Square,Circle,width,height);
                quadrant.update(CPIQuadrant.SF,width,height);
            }
        }
    }
    @Override
    protected boolean navigationInclude(int index, ViewPage2DComponent c){
        return (ST <= index);
    }
    @Override
    protected int first(){
        return -1;
    }
}
