/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public enum Page {
    intro    (CPIPageIntro.Instance,     true),
    start    (CPIPageStart.Instance,     false),
    view     (CPIPageView.Instance,      true),
    examples (CPIPageExamples.Instance,  false),
    inventory(CPIPageInventory.Instance, false),
    sharing  (CPIPageSharing.Instance,   false),
    about    (CPIPageAbout.Instance,     true);


    public final ViewPage page;

    public final boolean simpleInput;


    private Page(ViewPage page, boolean simpleInput){
        this.page = page;
        this.simpleInput = simpleInput;
    }

}
