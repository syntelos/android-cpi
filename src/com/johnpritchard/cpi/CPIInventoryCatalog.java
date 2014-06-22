/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.graphics.RectF;

/**
 * Word pairs in left, right order
 */
public final class CPIInventoryCatalog {
    /**
     * @return Word pair in left, right order
     */
    public final static CPITextInventory[] Item(int index){

        if (-1 < index && index < LIST.length){

            return LIST[index];
        }
        else {
            return null;
        }
    }
    private final static CPITextInventory[][] LIST = {
        {new CPITextInventory("Practical"),        new CPITextInventory("Emotional")},
        {new CPITextInventory("Facts"),            new CPITextInventory("Feelings")},
        {new CPITextInventory("Doing"),            new CPITextInventory("Talking")},
        {new CPITextInventory("Concrete"),         new CPITextInventory("Personal")},
        {new CPITextInventory("Read A Book"),      new CPITextInventory("Tell a story")},
        {new CPITextInventory("Get It Done"),      new CPITextInventory("Enjoy doing")},
        {new CPITextInventory("Roles"),            new CPITextInventory("Loyalties")},
        {new CPITextInventory("Sensible"),         new CPITextInventory("Emotional")},
        {new CPITextInventory("Protect yourself"), new CPITextInventory("For the Children")},
        {new CPITextInventory("Practice"),         new CPITextInventory("Discuss")},
        {new CPITextInventory("Trial & error"),    new CPITextInventory("Strategy")},
        {new CPITextInventory("Protect yourself"), new CPITextInventory("Tell the Truth")},
        {new CPITextInventory("Specifics"),        new CPITextInventory("Overview")},
        {new CPITextInventory("Concrete"),         new CPITextInventory("Abstract")},
        {new CPITextInventory("Read A Book"),      new CPITextInventory("Work a Puzzle")},
        {new CPITextInventory("Get It Done"),      new CPITextInventory("Plan it out")},
        {new CPITextInventory("Roles"),            new CPITextInventory("Laws")},
        {new CPITextInventory("Sensible"),         new CPITextInventory("Logical")},
        {new CPITextInventory("Information"),      new CPITextInventory("Concepts")},
        {new CPITextInventory("Practice"),         new CPITextInventory("Understand")},
        {new CPITextInventory("Trial & error"),    new CPITextInventory("Visualize")},
        {new CPITextInventory("Realistic"),        new CPITextInventory("Idealistic")},
        {new CPITextInventory("Details"),          new CPITextInventory("Big Picture")},
        {new CPITextInventory("Touch, hold"),      new CPITextInventory("Mental picture")},
        {new CPITextInventory("Read A Book"),      new CPITextInventory("Daydream")},
        {new CPITextInventory("Get It Done"),      new CPITextInventory("Create")},
        {new CPITextInventory("Roles"),            new CPITextInventory("Principles")},
        {new CPITextInventory("Sensible"),         new CPITextInventory("Logical")},
        {new CPITextInventory("Protect yourself"), new CPITextInventory("Save the Earth")},
        {new CPITextInventory("Practice"),         new CPITextInventory("Think About")},
        {new CPITextInventory("Create"),           new CPITextInventory("Share")},
        {new CPITextInventory("Ideals"),           new CPITextInventory("Relationships")},
        {new CPITextInventory("Imagination"),      new CPITextInventory("People")},
        {new CPITextInventory("Possibilities"),    new CPITextInventory("Loyalties")},
        {new CPITextInventory("Listen to Music"),  new CPITextInventory("Tell a story")},
        {new CPITextInventory("Daydream"),         new CPITextInventory("Group activities")},
        {new CPITextInventory("Principles"),       new CPITextInventory("Loyalties")},
        {new CPITextInventory("Insights"),         new CPITextInventory("Emotions")},
        {new CPITextInventory("Save the Earth"),   new CPITextInventory("For the Children")},
        {new CPITextInventory("Think about"),      new CPITextInventory("Discuss")},
        {new CPITextInventory("Sharing"),          new CPITextInventory("Strategy")},
        {new CPITextInventory("For the Children"), new CPITextInventory("Tell the Truth")},
        {new CPITextInventory("Details"),          new CPITextInventory("Overview")},
        {new CPITextInventory("Concrete"),         new CPITextInventory("Abstract")},
        {new CPITextInventory("Tell a story"),     new CPITextInventory("Work a Puzzle")},
        {new CPITextInventory("Enjoy doing"),      new CPITextInventory("Plan it well")},
        {new CPITextInventory("Loyalty"),          new CPITextInventory("Law")},
        {new CPITextInventory("Emotion"),          new CPITextInventory("Logic")},
        {new CPITextInventory("Join a group"),     new CPITextInventory("Lead a group")},
        {new CPITextInventory("Discuss"),          new CPITextInventory("Understand")},
        {new CPITextInventory("Strategy"),         new CPITextInventory("Visualize")},
        {new CPITextInventory("Theoretical"),      new CPITextInventory("Idealistic")},
        {new CPITextInventory("Experiment"),       new CPITextInventory("Invent")},
        {new CPITextInventory("Think"),            new CPITextInventory("Imagine")},
        {new CPITextInventory("Solve a Puzzle"),   new CPITextInventory("Daydream")},
        {new CPITextInventory("Plan it out"),      new CPITextInventory("Create")},
        {new CPITextInventory("Laws"),             new CPITextInventory("Principles")},
        {new CPITextInventory("Logic"),            new CPITextInventory("Metaphor")},
        {new CPITextInventory("Tell the Truth"),   new CPITextInventory("Save the Earth")},
        {new CPITextInventory("Analyze"),          new CPITextInventory("Discover")}
    };
    protected final static RectF GROUP = new RectF();
    static {
        final int listlen = LIST.length;
        for (int li = 0; li < listlen; li++){
            CPITextInventory a = LIST[li][0];
            CPITextInventory b = LIST[li][1];
            GROUP.union(a.src);
            GROUP.union(b.src);
        }
        for (int li = 0; li < listlen; li++){
            CPITextInventory a = LIST[li][0];
            CPITextInventory b = LIST[li][1];
            a.group(GROUP);
            b.group(GROUP);
        }
    }
    protected static void Init(){
    }

    private CPIInventoryCatalog(){
        super();
    }
}
