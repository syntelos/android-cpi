/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * Buttons can't use "simple input" (gestures).  Non button pages use
 * simple input.
 */
public enum Page {
    /*
     * Changes to the membership of this list need to be reflected in
     * CPI StartActivity(Page)
     */
    intro    (CPIPageIntro.Instance,     true),
    start    (CPIPageStart.Instance,     false),
    view     (CPIPageView.Instance,      true),
    practice (CPIPagePractice.Instance,  false),
    inventory(CPIPageInventory.Instance, false),
    about    (CPIPageAbout.Instance,     true);


    public final ViewPage page;

    public final boolean simpleInput;


    private Page(ViewPage page, boolean simpleInput){
        this.page = page;
        this.simpleInput = simpleInput;
    }

}
