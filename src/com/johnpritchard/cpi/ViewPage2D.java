/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * <h3>Layout</h3>
 * 
 * The "layout" method is called with visual display dimensions to
 * transform the abstract geometry into practical visual display
 * geometry.  When this process has completed, the minimal and maximal
 * X and Y coordinates of the set of (page) components are expected to
 * fit within the dimensions of the display (surface).
 * 
 * This phase occurs each time the page is entered (via {@link View}
 * pageTo), and may transform existing geometry for device orientation
 * and display surface dimensions.
 * 
 * @see ViewPage
 * @see ViewPage2DComponent
 */
public abstract class ViewPage2D
    extends ViewPage
{


    /**
     * Relative navigation and visualization for {@link InputScript}
     * events via {@link ViewAnimation}.
     */
    protected final ViewPage2DComponent[] components;

    protected volatile ViewPage2DComponent current;

    protected volatile ViewPageComponentInteractive interactive;


    protected ViewPage2D(ViewPage2DComponent[] components){
        super();
        this.components = components;
        //info("init");
        init();
    }



    /**
     * Bounding box with independence from screen location
     */
    protected RectF measure(){

        return measure(0,components.length);
    }
    protected RectF measure(int offset, int count){
        final ViewPage2DComponent[] components = this.components;

        final RectF g = new RectF();

        // RectF selection_group = null;

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            // if (c.pageMeasureByGroup()){

            //     RectF sel = c.getSelectionGroup().group();

            //     if (sel != selection_group){
            //         selection_group = sel;

            //         g.union(sel);
            //     }
            // }
            // else {
                g.union(c.bounds());
            // }
        }
        return g;
    }
    protected RectF scale(float s){
        return scale(0,components.length,s);
    }
    protected RectF scale(int offset, int count, float s){
        final ViewPage2DComponent[] components = this.components;

        RectF g = new RectF();

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            c.scale(s);

            g.union(c.bounds());
        }
        return g;
    }
    protected void scale(RectF dst){
        scale(0,components.length,dst);
    }
    protected void scale(int offset, int count, RectF dst){

        final Matrix m = new Matrix();
        {
            final RectF src = measure(offset,count);

            m.setRectToRect(src,dst,Matrix.ScaleToFit.CENTER);
        }
        final ViewPage2DComponent[] components = this.components;

        for (int cc = offset, end = (offset+count); cc < end; cc++){

            ViewPage2DComponent c = components[cc];

            c.transform(m);
        }
    }
    /**
     * scale to viewport (layout)
     */
    protected void scale(){
        final ViewPage2DComponent[] components = this.components;
        if (0 < width && 0 < height && null != components){

            final Matrix m = new Matrix();
            {
                final RectF src = measure();

                final RectF dst = new RectF(pad,pad,(width-pad),(height-pad));

                m.setRectToRect(src,dst,Matrix.ScaleToFit.CENTER);
            }

            for (ViewPage2DComponent c : components){

                c.transform(m);
            }
        }
        else {
            throw new IllegalStateException();
        }
    }
    protected void scale(int offset, int count){
        final ViewPage2DComponent[] components = this.components;
        if (0 < width && 0 < height && null != components){

            final Matrix m = new Matrix();
            {
                final RectF src = measure(offset,count);

                final RectF dst = new RectF(pad,pad,(width-pad),(height-pad));

                m.setRectToRect(src,dst,Matrix.ScaleToFit.CENTER);
            }

            for (int cc = offset, cz = (offset+count); cc < cz; cc++){

                ViewPage2DComponent c = components[cc];

                c.transform(m);
            }
        }
        else {
            throw new IllegalStateException();
        }
    }
    protected static float pad(RectF a, RectF b){
        return Math.max(pad(a),pad(b));
    }
    protected static float pad(RectF group){
        return (Math.max((group.right-group.left),(group.bottom-group.top)) * PAD_RATIO);
    }
    /**
     * abstract or self center
     */
    protected RectF center(int offset, int count){
        final ViewPage2DComponent[] components = this.components;

        RectF re = new RectF();

        final RectF g = measure(offset,count);

        final float pad = pad(g);
        /*
         * group delta from origin (pad,pad)
         */
        final float gdx = Z(pad-g.left);
        final float gdy = Z(pad-g.top);
        /*
         * group centroid
         */
        final float gw2 = ((g.right-g.left)/2.0f);
        final float gh2 = ((g.bottom-g.top)/2.0f);

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            final RectF cb = c.bounds();
            /*
             * component centroid
             */
            final float cw2 = (cb.right-cb.left)/2.0f;
            final float ch2 = (cb.bottom-cb.top)/2.0f;
            /*
             * component objective
             */
            final float ox = (gw2-cw2);
            final float oy = (gh2-ch2);
            /*
             * component translation
             */
            final float dx = Z((ox-cb.left)+gdx);
            final float dy = Z((oy-cb.top)+gdy);

            c.translate(dx,dy);

            re.union(c.bounds());
        }
        return re;
    }
    protected void center(RectF g){
        center(0,components.length,g);
    }
    protected void center(int offset, int count, RectF g){
        final ViewPage2DComponent[] components = this.components;

        final float gw2 = ((g.right-g.left)/2.0f);
        final float gh2 = ((g.bottom-g.top)/2.0f);

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            final RectF cb = c.bounds();

            final float cw2 = (cb.right-cb.left)/2.0f;
            final float ch2 = (cb.bottom-cb.top)/2.0f;

            final float cx = (gw2-cw2);
            final float cy = (gh2-ch2);

            final float dx = Z(cx-cb.left);
            final float dy = Z(cy-cb.top);

            c.translate(dx,dy);
        }
    }
    protected RectF group(int offset, int count, RectF g){

        return group(offset,count,g,pad(g));
    }
    protected RectF group(int offset, int count, RectF g, float p){
        final ViewPage2DComponent[] components = this.components;

        RectF re = new RectF();

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            if (c instanceof ViewPageComponentGroup){

                ((ViewPageComponentGroup)c).group(g,p);
            }
            re.union(c.bounds());
        }

        final float margin = Math.max(p,Math.max(re.left,re.top));
        /*
         * Correction for location
         */
        if (0.0f != DE(margin,re.left) || 0.0f != DE(margin,re.top)){

            return location(offset,count,re,margin,margin);
        }
        else {
            return re;
        }
    }
    protected void vertical(){
        final ViewPage2DComponent[] components = this.components;
        if (null != components){

            RectF group = center(0,components.length);

            float pad = pad(group);

            col(0,components.length,pad,pad,(group.right-group.left),pad);
        }
        else {
            throw new IllegalStateException();
        }
    }
    protected void group_vertical(){

        final ViewPage2DComponent[] components = this.components;
        if (null != components){

            RectF group = center(0,components.length);

            float pad = pad(group);

            group = group(0,components.length,group,pad);

            col(0,components.length,pad,pad,(group.right-group.left),pad);
        }
        else {
            throw new IllegalStateException();
        }
    }
    protected void group_vertical(int offset, int count){

        final ViewPage2DComponent[] components = this.components;
        if (null != components){

            RectF group = center(offset,count);

            float pad = pad(group);

            group = group(offset,count,group,pad);

            col(offset,count,pad,pad,(group.right-group.left),pad);
        }
        else {
            throw new IllegalStateException();
        }
    }
    protected RectF location(int offset, int count, RectF g, float x, float y){
        final ViewPage2DComponent[] components = this.components;

        RectF re = new RectF();

        final float dx = (x - g.left);
        final float dy = (y - g.top);

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            c.translate(dx,dy);

            re.union(c.bounds());
        }
        return re;
    }
    protected RectF row(int offset, int count, float row_x, float row_y, float row_h, float pad){

        final float gh2 = (row_h/2.0f);

        RectF re = new RectF();

        float tx = row_x;

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            final RectF cb = c.bounds();

            final float ch2 = (cb.bottom-cb.top)/2.0f;

            final float ty = ((gh2-ch2)+row_y);

            final float ox = (tx-cb.left);
            final float oy = (ty-cb.top);

            c.translate(ox,oy);

            re.union(c.bounds());

            tx += ((cb.right-cb.left) + pad);
        }
        return re;
    }
    protected void row(int offset, int count, float row_x, float row_y, float row_w, float row_h, float pad){

        final float gh2 = (row_h/2.0f);

        final float gwN = (row_w/(((float)count)+1.0f));
        final float gwN_d2 = (gwN/2.0f);

        for (int cc = offset, end = (offset+count); cc < end; cc++){

            ViewPage2DComponent c = components[cc];

            final RectF cb = c.bounds();

            final float cw2 = (cb.right-cb.left)/2.0f;
            final float ch2 = (cb.bottom-cb.top)/2.0f;

            final float tx = ((gwN_d2-cw2)+((gwN*((cc-offset)+1.0f))-gwN_d2));
            final float ty = ((gh2-ch2)+row_y);

            final float ox = (tx-cb.left);
            final float oy = (ty-cb.top);

            c.translate(ox,oy);
        }
    }
    protected RectF col(int offset, int count, float col_x, float col_y, float col_w, float pad){

        final float gw2 = (col_w/2.0f);

        RectF re = new RectF();

        float ty = col_y;

        for (int cc = offset, end = (offset+count); cc < end; cc++){
            ViewPage2DComponent c = components[cc];

            final RectF cb = c.bounds();

            final float cw2 = ((cb.right-cb.left)/2.0f);

            final float tx = ((gw2-cw2)+col_x);

            final float ox = (tx-cb.left);
            final float oy = (ty-cb.top);

            c.translate(ox,oy);

            re.union(c.bounds());

            ty += ((cb.bottom-cb.top) + pad);
        }
        return re;
    }

    @Override
    protected void init(){
    }
    @Override
    protected void layout(){
        scale();
    }
    @Override
    public void down(SharedPreferences.Editor preferences){

        this.down();

        preferences.putInt(name()+".focus",this.current());
    }

    /**
     * @see #first()
     */
    protected boolean navigationInclude(int index, ViewPage2DComponent c){
        return true;
    }
    /**
     * Initialize navigation
     */
    @Override
    protected final void navigation(){
        for (int cc = 0, count = components.length; cc < count; cc++){
            ViewPage2DComponent c = components[cc];

            c.clearCardinals();

            if (navigationInclude(cc,c)){

                final float cx = c.getX();
                final float cy = c.getY();

                for (int bb = 0; bb < count; bb++){
                    if (bb != cc){
                        ViewPage2DComponent b = components[bb];

                        if (navigationInclude(bb,b)){

                            final Input dir = c.direction(b);

                            if (Input.Enter != dir){

                                c.setCardinal(dir,b);
                            }
                        }
                    }
                    else {
                        c.setCardinal(Input.Enter,c);
                    }
                }
            }
        }
    }
    /**
     * Initialize focus
     */
    @Override
    protected void focus(){
        final int count = components.length;

        if (0 != count){
            final SharedPreferences preferences = preferences();
            final int first = first();
            final int focus = preferences.getInt(name()+".focus",first);

            if (-1 < focus && focus < count){

                current(components[focus]);
            }
            else if (-1 < first && first < count){

                current(components[first]);
            }

            final ViewPage2DComponent current = this.current;

            for (ViewPage2DComponent c : components){

                if (c == current){
                    c.setCurrent();
                }
                else {
                    c.clearCurrent();
                }
            }
        }
    }
    @Override
    protected void input_emphasis(){

        if (current instanceof ViewPageComponentEmphasis){

            ((ViewPageComponentEmphasis)current).emphasis(true);
        }
    }
    @Override
    protected void input_deemphasis(){

        if (current instanceof ViewPageComponentEmphasis){

            ((ViewPageComponentEmphasis)current).emphasis(false);
        }
    }
    /**
     * Convert navigation activity to navigational focus status.
     * 
     * The back button or input script should always have the same
     * effect as on devices where the back button operates directly on
     * the activity stack (without passing through the View key event
     * process).
     */
    @Override
    public void input(InputScript event){

        Input in = event.type();

        if (in.geometric){

            final ViewPageComponentInteractive interactive = this.interactive;

            if (null != interactive && 
                (Input.Enter == in || interactive.interactive()))
            {
                interactive.input(event);
            }
            else {

                ViewPage2DComponent current = this.current;

                if (null != current){

                    current(current.getCardinal(in));
                }
                else {
                    current = this.current(in);

                    if (null != current){

                        current(current);
                    }
                }
            }
        }
        else {
            super.input(event);
        }
    }
    /**
     * Called by {@link View} after filling the background.
     */
    @Override
    public void draw(Canvas g){

        for (ViewPage2DComponent c : components){

            c.draw(g);
        }
    }
    /**
     * This may return negative one when a subclass is filtering
     * (excluding) enter events.
     */
    protected int enter(){

        return this.current();
    }
    /**
     * According to the typical implementation of {@link #focus()},
     * this will not return negative one.
     */
    protected int current(){
        ViewPage2DComponent[] components = this.components;

        final int count = components.length;
        for (int cc = 0; cc < count; cc++){

            if (current == components[cc]){

                return cc;
            }
        }
        return -1;
    }
    protected ViewPage2DComponent current(Input in){

        final ViewPage2DComponent current = this.current;

        if (null != current){

            return current;
        }
        else {
            final int count = this.components.length;
            if (0 < count){
                if (Input.Down == in){

                    for (int cc = 0; cc < count; cc++){
                        final ViewPage2DComponent c = this.components[cc];
                        if (navigationInclude(cc,c)){
                            return c;
                        }
                    }
                }
                else {

                    for (int cc = (count-1); -1 < cc; cc--){
                        final ViewPage2DComponent c = this.components[cc];
                        if (navigationInclude(cc,c)){
                            return c;
                        }
                    }
                }
            }

            return null;
        }
    }
    protected void current(ViewPage2DComponent next){

        final ViewPage2DComponent prev = this.current;

        if (null != next && next != prev){

            if (null != prev){
                prev.clearCurrent();
            }

            this.current = next;

            next.setCurrent();

            if (next instanceof ViewPageComponentInteractive){

                this.interactive = (ViewPageComponentInteractive)next;
            }
            else {
                this.interactive = null;
            }
        }
    }
    /**
     * Initialize focus with a central component
     * 
     * @see #navigationInclude
     */
    protected int first(){
        int first = 0;

        if (1 < components.length){

            int first_score = -1;

            final int count = (int)Math.ceil((float)components.length/2.0f);

            for (int cc = 0; cc < count; cc++){

                ViewPage2DComponent c = components[cc];

                int c_score = c.countCardinals();

                if (c_score >= first_score){

                    first = cc;
                    first_score = c_score;
                }
            }
        }
        return first;
    }
    protected ViewPage2DPath.FillType getFillType(){
        for (ViewPage2DComponent c : components){
            if (c instanceof ViewPage2DComponentPath){
                return ((ViewPage2DComponentPath)c).getFillType();
            }
        }
        return ViewPage2DPath.FillType.WINDING;
    }
    protected void setFillType(ViewPage2DPath.FillType ft){
        for (ViewPage2DComponent c : components){
            if (c instanceof ViewPage2DComponentPath){
                ViewPage2DComponentPath p = (ViewPage2DComponentPath)c;
                p.setFillType(ft);
            }
        }
    }
    protected void rotateFillType(){
        for (ViewPage2DComponent c : components){
            if (c instanceof ViewPage2DComponentPath){
                ViewPage2DComponentPath p = (ViewPage2DComponentPath)c;
                p.rotateFillType();
            }
        }
    }
    protected void logFillType(){
        for (ViewPage2DComponent c : components){
            if (c instanceof ViewPage2DComponentPath){
                ViewPage2DComponentPath p = (ViewPage2DComponentPath)c;
                p.logFillType();
            }
        }
    }
}
