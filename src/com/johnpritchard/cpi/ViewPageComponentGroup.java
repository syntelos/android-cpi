/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.RectF;

/**
 * 
 */
public interface ViewPageComponentGroup
    extends ViewPageComponent
{
    /**
     * Group dimension and derived margin
     */
    public void group(RectF dim, float pad);
}
