/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import path.Op;
import path.Operand;

/**
 * 
 */
public class CPIButtonR3
    extends CPIButtonInventoryAbstract
{


    public CPIButtonR3(){
        super(new Operand[]{
                new Operand(Op.MoveTo,new float[]{2.0f,2.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{2.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{6.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,2.0f}),
                new Operand(Op.Close,new float[]{}),
                new Operand(Op.MoveTo,new float[]{10.0f,2.0f}),
                new Operand(Op.LineTo,new float[]{12.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{10.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{12.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{14.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{12.0f,2.0f}),
                new Operand(Op.Close,new float[]{}),
                new Operand(Op.MoveTo,new float[]{18.0f,2.0f}),
                new Operand(Op.LineTo,new float[]{20.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{18.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{20.0f,10.0f}),
                new Operand(Op.LineTo,new float[]{22.0f,6.0f}),
                new Operand(Op.LineTo,new float[]{20.0f,2.0f}),
                new Operand(Op.Close,new float[]{}),
            }, new Operand[]{
                new Operand(Op.MoveTo,new float[]{0.0f,0.0f}),
                new Operand(Op.LineTo,new float[]{0.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{24.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{24.0f,0.0f}),
                new Operand(Op.Close,new float[]{}),
            });

    }

}
