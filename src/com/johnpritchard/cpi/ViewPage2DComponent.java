/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Page components are always visible and focusable and do not overlap
 * in X-Y space.  This component is comparable to the Android
 * Drawable, and differs primarily in its emphasis on a simplified
 * model of interaction.
 * 
 * @see ViewPage2DComponentPath
 */
public interface ViewPage2DComponent
    extends ViewPageComponent
{
    /**
     * Geometric clipping area should default to the shape boundary
     * @see ViewPage2DClip#shape
     */
    public ViewPage2DClip clip();
    /**
     * Initialization of the navigational operator: accept multiple
     * components in any one direction, keeping the one which is least
     * distant from this one.
     * 
     * The "Enter" input may be called with the "this" reference.
     * 
     * @param direction An input having geometric application
     * @param component A component member of the same page
     */
    public void setCardinal(Input direction, ViewPage2DComponent component);
    /**
     * Navigational operator
     */
    public ViewPage2DComponent getCardinal(Input direction);
    /**
     * Visual output operator
     */
    public void draw(Canvas c);

}
