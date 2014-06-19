/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import path.Op;
import path.Operand;

import android.graphics.Canvas;

/**
 * Visual and navigation placeholder for {@link CPIPageInventory}
 */
public class CPIButtonFirst
    extends ViewPage2DButtonPlain
{

    public CPIButtonFirst(){
        super(new Operand[]{
                new Operand(Op.MoveTo,new float[]{0.0f,0.0f}),
                new Operand(Op.LineTo,new float[]{0.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,0.0f}),
                new Operand(Op.Close,new float[]{}),
            });
    }

    public void draw(Canvas c){
    }
}
