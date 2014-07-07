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

    public final static void Init(){
    }
    public final static void Update(){

        Instance.update();
    }


    protected final RectF clipY = new RectF();
    protected final RectF clipB = new RectF();
    protected final RectF clipG = new RectF();
    protected final RectF clipR = new RectF();

    protected final ViewPage2DPath outside = new ViewPage2DPath();

    protected final ViewPage2DPath inside = new ViewPage2DPath();

    protected final ViewPage2DPath axes = new ViewPage2DPath();

    protected final Paint pW = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint pR = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint pG = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint pB = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint pY = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected final Paint pA = new Paint(Paint.ANTI_ALIAS_FLAG);

    protected float sf;
    protected float st;
    protected float nf;
    protected float nt;

    private volatile boolean debug;


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

        pW.setColor(Color.WHITE);
        pW.setStyle(Paint.Style.FILL);

        pR.setColor(Color.RED);
        pR.setStyle(Paint.Style.FILL);

        pG.setColor(Color.GREEN);
        pG.setStyle(Paint.Style.FILL);

        pB.setColor(Color.BLUE);
        pB.setStyle(Paint.Style.FILL);

        pY.setColor(Color.YELLOW);
        pY.setStyle(Paint.Style.FILL);

        pA.setColor(Color.GRAY);
        pA.setStyle(Paint.Style.STROKE);

        clip.margin(2.0f);

        bounds();
    }


    protected void update(){

        inside.reset();

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        if (inventory.hasCPICodeData()){

            info("update OK");
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
        }
        else {
            info("update NG");
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

            clipY.left = x0;
            clipY.top = y0;
            clipY.right = xm;
            clipY.bottom = ym;

            clipB.left = x0;
            clipB.top = ym;
            clipB.right = xm;
            clipB.bottom = y1;

            clipG.left = xm;
            clipG.top = ym;
            clipG.right = x1;
            clipG.bottom = y1;

            clipR.left = xm;
            clipR.top = y0;
            clipR.right = x1;
            clipR.bottom = ym;

            this.clip.set(this.bounds);

            //info("bounds left: "+bounds.left+", right: "+bounds.right+", top: "+bounds.top+", bottom: "+bounds.bottom);
            //info("clipY left: "+clipY.left+", right: "+clipY.right+", top: "+clipY.top+", bottom: "+clipY.bottom);
            //info("clipB left: "+clipB.left+", right: "+clipB.right+", top: "+clipB.top+", bottom: "+clipB.bottom);
            //info("clipG left: "+clipG.left+", right: "+clipG.right+", top: "+clipG.top+", bottom: "+clipG.bottom);
            //info("clipR left: "+clipR.left+", right: "+clipR.right+", top: "+clipR.top+", bottom: "+clipR.bottom);
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
    protected void toggleDebug(){
        debug = (!debug);

        //info("debug "+debug);
    }
    public void draw(Canvas c){

        //info("draw "+bounds());

        c.save();

        {
            c.clipPath(clip,Region.Op.REPLACE);

            c.drawPath(outside,pW);
        }
        {
            c.clipRect(clipY,Region.Op.REPLACE);

            c.drawPath(inside,pY);
        }
        {
            c.clipRect(clipB,Region.Op.REPLACE);

            c.drawPath(inside,pB);
        }
        {
            c.clipRect(clipG,Region.Op.REPLACE);

            c.drawPath(inside,pG);
        }
        {
            c.clipRect(clipR,Region.Op.REPLACE);

            c.drawPath(inside,pR);
        }
        {
            c.clipPath(clip,Region.Op.REPLACE);

            c.drawPath(axes,pA);
            c.drawPath(outside,pA);
        }
        c.restore();
    }

}
