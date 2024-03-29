/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import path.Operand;

/**
 * 
 */
public class ViewPage2DButtonGroup
    extends ViewPage2DButtonPlain
    implements ViewPageComponentGroup
{


    public ViewPage2DButtonGroup(){
        super();
    }
    public ViewPage2DButtonGroup(String text){
        super(text);
    }
    public ViewPage2DButtonGroup(Operand[] path){
        super(path);
    }
    public ViewPage2DButtonGroup(Operand[] path, Operand[] group){
        super(path,group);
    }
    public ViewPage2DButtonGroup(Operand[] path, Operand[] group, Operand[] clip){
        super(path,group,clip);
    }

}
