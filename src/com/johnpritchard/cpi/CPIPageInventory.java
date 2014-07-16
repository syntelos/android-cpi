/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * 
 */
public class CPIPageInventory
    extends ViewPage2D
{

    private final static int NONE = -1;
    /*
     * First row
     */
    private final static int LABEL_LEFT = 0;
    private final static int LABEL_RIGHT = 1;
    /*
     * Second row
     */
    private final static int BUTTON_L4 = 2;
    private final static int BUTTON_L3 = 3;
    private final static int BUTTON_L2 = 4;
    private final static int BUTTON_L1 = 5;
    private final static int FIRST     = 6;
    private final static int BUTTON_R1 = 7;
    private final static int BUTTON_R2 = 8;
    private final static int BUTTON_R3 = 9;
    private final static int BUTTON_R4 = 10;
    /*
     * First row offset and count
     */
    private final static int WO = LABEL_LEFT;
    private final static int WC = (LABEL_RIGHT-NONE);
    /*
     * Second row offset and count
     */
    private final static int BO = BUTTON_L4;
    private final static int BC = (BUTTON_R4-LABEL_RIGHT);

    public final static CPIPageInventory Instance = new CPIPageInventory();

    public static void View(){

        Instance.input();

        ViewAnimation.Script();
    }


    private volatile int inventoryIndex;

    private volatile boolean back;


    protected CPIPageInventory(){
        super(new ViewPage2DComponent[]{
                new CPITextInventory("Left"),
                new CPITextInventory("Right"),
                new CPIButtonL4(),
                new CPIButtonL3(),
                new CPIButtonL2(),
                new CPIButtonL1(),
                new CPIButtonFirst(),
                new CPIButtonR1(),
                new CPIButtonR2(),
                new CPIButtonR3(),
                new CPIButtonR4()
            });
    }


    @Override
    protected void init(){
        /*
         * Abstract words and buttons geometry
         */
        RectF words = center(WO,WC);

        RectF buttons = center(BO,BC);

        buttons = group(BO,BC,buttons);

        float p2 = (2.0f*pad(buttons));

        float p4 = (2.0f*p2);

        float words_h = (words.bottom-words.top)*4.0f;

        row(BO,BC,p2,(words_h+p4),(buttons.bottom-buttons.top),p2);

    }
    @Override
    protected void layout(){

        words();

        scale();
    }
    protected void input(){

        words();

        refocus();
    }
    protected CPITextInventory[] catalog(){

        this.inventoryIndex = CPIInventoryRecord.Instance.getSessionIndex();

        return CPIInventoryCatalog.Item(this.inventoryIndex);
    }
    protected void words(){

        CPITextInventory[] catalog = catalog();

        if (null != catalog){

            final CPITextInventory word_left = (CPITextInventory)(components[LABEL_LEFT] = catalog[0]);

            final CPITextInventory word_right = (CPITextInventory)(components[LABEL_RIGHT] = catalog[1]);
            /*
             * Define objective bounding boxes for each word
             */
            RectF dstL = new RectF(), dstR = new RectF();
            {
                dstL.union(components[BUTTON_L1].bounds());
                dstL.union(components[BUTTON_L4].bounds());

                dstR.union(components[BUTTON_R1].bounds());
                dstR.union(components[BUTTON_R4].bounds());

                final float dst_h = (dstL.bottom - dstL.top);
                final float pv = (dst_h * 0.5f);
                final float dst_top = (dstL.top - dst_h - pv);
                final float dst_bottom = (dst_top + dst_h);

                dstL.top = dst_top;
                dstL.bottom = dst_bottom;

                dstR.top = dst_top;
                dstR.bottom = dst_bottom;
            }
            /*
             * Center vertex sets within their respective objectives
             */
            {
                word_left.center(dstL);

                word_right.center(dstR);
            }
        }
        else {
            throw new IllegalStateException();
        }
    }
    /**
     * Used by {@link #input(com.johnpritchard.cpi.Input)} and {@link #focus()}
     */
    protected void refocus(){
        final int first = first();
        current = components[first];
        for (ViewPage2DComponent c : components){

            if (c == current){
                c.setCurrent();
            }
            else {
                c.clearCurrent();
            }
        }
    }
    @Override
    protected void focus(){

        refocus();
    }
    @Override
    protected int first(){
        return FIRST;
    }
    @Override
    protected boolean navigationInclude(int index, ViewPage2DComponent c){
        switch(index){
        case BUTTON_L4:
        case BUTTON_L3:
        case BUTTON_L2:
        case BUTTON_L1:
        case FIRST:
        case BUTTON_R1:
        case BUTTON_R2:
        case BUTTON_R3:
        case BUTTON_R4:
            return true;
        default:
            return false;
        }
    }
    @Override
    public String name(){
        return Page.inventory.name();
    }
    @Override
    public Page value(){
        return Page.inventory;
    }
    @Override
    public void input(InputScript in){

        final Input type = in.type();
        switch (type){

        case Enter:
            this.back = false;
            switch(enter()){
            case BUTTON_L4:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.L4));
                return;
            case BUTTON_L3:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.L3));
                return;
            case BUTTON_L2:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.L2));
                return;
            case BUTTON_L1:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.L1));
                return;
            case BUTTON_R1:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.R1));
                return;
            case BUTTON_R2:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.R2));
                return;
            case BUTTON_R3:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.R3));
                return;
            case BUTTON_R4:
                view.script(new InputScript.Database(InputScript.Database.Op.Input,this.inventoryIndex,CPIInventory.R4));
                return;
            default:
                return;
            }

        case Up:
        case Down:
            if (this.back){
                this.back = false;
                CPI.StartActivity(Page.start);
            }
            else {
                this.back = true;
                refocus();
            }
            return;
        default:
            if (type.geometric){
                this.back = false;
            }
            super.input(in);
            return;
        }
    }
}
