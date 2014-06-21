/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import path.Op;
import path.Operand;

/**
 * 
 */
public class CPIButtonR1
    extends CPIButtonInventoryAbstract
{


    public CPIButtonR1(){
        super(new Operand[]{
                new Operand(Op.MoveTo,new float[]{2.0f,4.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{2.0f,20.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,20.0f}),
                new Operand(Op.LineTo,new float[]{6.0f,12.0f}),
                new Operand(Op.LineTo,new float[]{4.0f,4.0f}),
                new Operand(Op.Close,new float[]{}),
            }, new Operand[]{
                new Operand(Op.MoveTo,new float[]{-4.0f,0.0f}),
                new Operand(Op.LineTo,new float[]{-4.0f,24.0f}),
                new Operand(Op.LineTo,new float[]{+12.0f,24.0f}),
                new Operand(Op.LineTo,new float[]{+12.0f,0.0f}),
                new Operand(Op.Close,new float[]{}),
            });

    }

}
