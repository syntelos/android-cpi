/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Static CPI graphic similar to the application icon.
 */
public final class CPIViz
    extends ViewPage2DComponentAbstract
    implements ViewPageComponentGroup
{
    /**
     * 
     */
    public static class Color
        extends android.graphics.Color
    {

        public static final int SF = 0xffe00000;
        public static final int ST = 0xfff0f000;
        public static final int NF = 0xff30b000;
        public static final int NT = 0xff0000b0;
        public static final int BORDER = 0xff909090;


        public static final int GRAY        = Color.BORDER;

        public static final int RED         = Color.SF;
        public static final int GREEN       = Color.NF;
        public static final int BLUE        = Color.NT;
        public static final int YELLOW      = Color.ST;

    }


    public final static CPIViz Instance = new CPIViz();

    protected final static ViewPage2DComponentRect Clip_ST = Instance.clip_ST;
    protected final static ViewPage2DComponentRect Clip_NT = Instance.clip_NT;
    protected final static ViewPage2DComponentRect Clip_NF = Instance.clip_NF;
    protected final static ViewPage2DComponentRect Clip_SF = Instance.clip_SF;


    public final static void Init(){
    }
    public final static void Update(){

        Instance.update();
    }


    protected final ViewPage2DComponentRect clip_ST = new ViewPage2DComponentRect();
    protected final ViewPage2DComponentRect clip_NT = new ViewPage2DComponentRect();
    protected final ViewPage2DComponentRect clip_NF = new ViewPage2DComponentRect();
    protected final ViewPage2DComponentRect clip_SF = new ViewPage2DComponentRect();

    protected final ViewPage2DPath outside = new ViewPage2DPath();

    protected final ViewPage2DPath inside = new ViewPage2DPath();

    protected final ViewPage2DPath axes = new ViewPage2DPath();

    protected final Paint p_BG = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint p_SF = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint p_NF = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint p_NT = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint p_ST = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint p_GRID = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected final Paint s_SF = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint s_NF = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint s_NT = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint s_ST = new Paint(Paint.ANTI_ALIAS_FLAG);


    protected float sf;
    protected float st;
    protected float nf;
    protected float nt;

    protected volatile CPIQuadrant selection;

    protected volatile CPIQuadrant primary;


    public CPIViz(){
        super();

        outside.moveTo(0,0);
        outside.lineTo(0,4);
        outside.lineTo(4,4);
        outside.lineTo(4,0);
        outside.close();
        /*
         * dummy icon data
         * SF 0.307
         * ST 0.519
         * NF 0.634
         * NT 1.0
         */
        inside.reset();
        inside.moveTo(0.962f,0.962f);
        inside.lineTo(2.614f,1.386f);
        inside.lineTo(3.268f,3.268f);
        inside.lineTo(0.000f,4.000f);
        inside.close();

        axes.moveTo(2,0);
        axes.lineTo(2,4);
        axes.moveTo(0,2);
        axes.lineTo(4,2);

        p_BG.setColor(Color.WHITE);
        p_BG.setStyle(Paint.Style.FILL);

        p_SF.setColor(Color.RED);
        p_SF.setStyle(Paint.Style.FILL);

        p_NF.setColor(Color.GREEN);
        p_NF.setStyle(Paint.Style.FILL);

        p_NT.setColor(Color.BLUE);
        p_NT.setStyle(Paint.Style.FILL);

        p_ST.setColor(Color.YELLOW);
        p_ST.setStyle(Paint.Style.FILL);

        p_GRID.setColor(Color.GRAY);
        p_GRID.setStyle(Paint.Style.STROKE);


        s_SF.setColor(Color.RED);
        s_SF.setStyle(Paint.Style.STROKE);
        s_SF.setStrokeWidth(2.0f);

        s_NF.setColor(Color.GREEN);
        s_NF.setStyle(Paint.Style.STROKE);
        s_NF.setStrokeWidth(2.0f);

        s_NT.setColor(Color.BLUE);
        s_NT.setStyle(Paint.Style.STROKE);
        s_NT.setStrokeWidth(2.0f);

        s_ST.setColor(Color.YELLOW);
        s_ST.setStyle(Paint.Style.STROKE);
        s_ST.setStrokeWidth(2.0f);


        clip.margin(2.0f);

        bounds();
    }


    public void select(CPIQuadrant q){

        selection = q;

        if (null == q){

            //info("select <*>");
            p_SF.setStyle(Paint.Style.FILL);
            p_NF.setStyle(Paint.Style.FILL);
            p_NT.setStyle(Paint.Style.FILL);
            p_ST.setStyle(Paint.Style.FILL);
        }
        else {
            switch(q){
            case ST:
                //info("select <ST>");
                p_SF.setStyle(Paint.Style.STROKE);
                p_NF.setStyle(Paint.Style.STROKE);
                p_NT.setStyle(Paint.Style.STROKE);
                p_ST.setStyle(Paint.Style.FILL);
                break;
            case SF:
                //info("select <SF>");
                p_SF.setStyle(Paint.Style.FILL);
                p_NF.setStyle(Paint.Style.STROKE);
                p_NT.setStyle(Paint.Style.STROKE);
                p_ST.setStyle(Paint.Style.STROKE);
                break;
            case NT:
                //info("select <NT>");
                p_SF.setStyle(Paint.Style.STROKE);
                p_NF.setStyle(Paint.Style.STROKE);
                p_NT.setStyle(Paint.Style.FILL);
                p_ST.setStyle(Paint.Style.STROKE);
                break;
            case NF:
                //info("select <NF>");
                p_SF.setStyle(Paint.Style.STROKE);
                p_NF.setStyle(Paint.Style.FILL);
                p_NT.setStyle(Paint.Style.STROKE);
                p_ST.setStyle(Paint.Style.STROKE);
                break;
            default:
                break;
            }
        }
    }
    protected boolean update(){

        inside.reset();

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        if (inventory.hasCPICodeData()){

            //info("update OK");
            /*
             * actual record data
             */
            st = inventory.st;
            sf = inventory.sf;
            nt = inventory.nt;
            nf = inventory.nf;


            final RectF img = bounds();
            final float img_x = img.left;
            final float img_y = img.top;
            final float img_w = (img.right-img_x);
            final float img_h = (img.bottom-img_y);
            final float img_s = Math.max(img_h,img_w);
            final float img_s_d2 = (img_s/2.0f);

            final float s_st = img_s_d2*st;
            final float s_sf = img_s_d2*sf;
            final float s_nt = img_s_d2*nt;
            final float s_nf = img_s_d2*nf;


            final float x0 = (img_s_d2 - s_st)+img_x;
            final float y0 = (img_s_d2 - s_st)+img_y;

            final float x1 = (img_s_d2 + s_sf)+img_x;
            final float y1 = (img_s_d2 - s_sf)+img_y;

            final float x2 = (img_s_d2 + s_nf)+img_x;
            final float y2 = (img_s_d2 + s_nf)+img_y;

            final float x3 = (img_s_d2 - s_nt)+img_x;
            final float y3 = (img_s_d2 + s_nt)+img_y;


            inside.moveTo(x0,y0);
            inside.lineTo(x1,y1);
            inside.lineTo(x2,y2);
            inside.lineTo(x3,y3);
            inside.close();

            if (st > sf){
                if (st > nt){
                    if (st > nf){
                        primary = CPIQuadrant.ST;
                    }
                    else {
                        primary = CPIQuadrant.NF;
                    }
                }
                else if (nt > nf){
                    primary = CPIQuadrant.NT;
                }
                else {
                    primary = CPIQuadrant.NF;
                }
            }
            else if (sf > nt){
                if (sf > nt){
                    if (sf > nf){
                        primary = CPIQuadrant.SF;
                    }
                    else {
                        primary = CPIQuadrant.NF;
                    }
                }
                else if (nt > nf){
                    primary = CPIQuadrant.NT;
                }
                else {
                    primary = CPIQuadrant.NF;
                }
            }
            else if (nt > nf){
                primary = CPIQuadrant.NT;
            }
            else {
                primary = CPIQuadrant.NF;
            }

            return true;
        }
        else {
            //info("update NG");

            return false;
        }
    }
    public final RectF bounds(){

        if (this.bounds.isEmpty()){

            this.outside.computeBounds(this.bounds,true);

            final float x0 = this.bounds.left-1.0f;
            final float y0 = this.bounds.top-1.0f;
            final float x1 = this.bounds.right+1.0f;
            final float y1 = this.bounds.bottom+1.0f;
            final float xm = ((x0+x1)/2.0f)+1.0f;
            final float ym = ((y0+y1)/2.0f)+1.0f;

            clip_ST.left = x0;
            clip_ST.top = y0;
            clip_ST.right = xm;
            clip_ST.bottom = ym;

            clip_NT.left = x0;
            clip_NT.top = ym;
            clip_NT.right = xm;
            clip_NT.bottom = y1;

            clip_NF.left = xm;
            clip_NF.top = ym;
            clip_NF.right = x1;
            clip_NF.bottom = y1;

            clip_SF.left = xm;
            clip_SF.top = y0;
            clip_SF.right = x1;
            clip_SF.bottom = ym;

            this.clip.set(this.bounds);
        }
        return this.bounds;
    }
    public final void transform(Matrix m){

        //info("transform "+m);

        this.clear();

        this.outside.transform(m);
        this.inside.transform(m);
        this.axes.transform(m);

        this.bounds(); // define-clips
    }
    public void group(RectF dim, float pad){

        //info("group "+dim);

        final float square = Math.max((dim.bottom-dim.top), (dim.right-dim.left));
        final float x0 = dim.left, y0 = dim.top, x1 = (x0 + square), y1 = (y0 + square);

        final Matrix m = new Matrix();
        final RectF src = bounds();
        final RectF tgt = new RectF(x0,y0,x1,y1);
        {
            m.setRectToRect(src,tgt,Matrix.ScaleToFit.CENTER);
        }
        this.transform(m);
    }
    public void draw(Canvas c){

        //info("draw "+bounds());

        c.save();
        {
            c.clipPath(clip,Region.Op.REPLACE);

            c.drawPath(outside,p_BG);
        }
        {
            final CPIQuadrant selection = this.selection;

            if (null != selection){
                switch(selection){
                case ST:
                    c.drawRect(clip_ST,s_ST);
                    break;
                case NT:
                    c.drawRect(clip_NT,s_NT);
                    break;
                case NF:
                    c.drawRect(clip_NF,s_NF);
                    break;
                case SF:
                    c.drawRect(clip_SF,s_SF);
                    break;
                }
            }
        }
        {
            c.clipRect(clip_ST,Region.Op.REPLACE);

            c.drawPath(inside,p_ST);
        }
        {
            c.clipRect(clip_NT,Region.Op.REPLACE);

            c.drawPath(inside,p_NT);
        }
        {
            c.clipRect(clip_NF,Region.Op.REPLACE);

            c.drawPath(inside,p_NF);
        }
        {
            c.clipRect(clip_SF,Region.Op.REPLACE);

            c.drawPath(inside,p_SF);
        }
        {
            c.clipPath(clip,Region.Op.REPLACE);

            c.drawPath(axes,p_GRID);
            c.drawPath(outside,p_GRID);
        }
        c.restore();
    }

}
