/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * Word pairs in left, right order
 */
public class CPIInventoryCatalog {
    /**
     * @return Word pair in left, right order
     */
    public final static String[] Item(int index){

        if (-1 < index && index < LIST.length){

            return LIST[index];
        }
        else {
            return null;
        }
    }
    private final static String[][] LIST = {
        {"Practical","Emotional"},
        {"Facts","Feelings"},
        {"Doing","Talking"},
        {"Concrete","Personal"},
        {"Read A Book","Tell a story"},
        {"Get It Done","Enjoy doing"},
        {"Roles","Loyalties"},
        {"Sensible","Emotional"},
        {"Protect yourself","For the Children"},
        {"Practice","Discuss"},
        {"Trial & error","Strategy"},
        {"Protect yourself","Tell the Truth"},
        {"Specifics","Overview"},
        {"Concrete","Abstract"},
        {"Read A Book","Work a Puzzle"},
        {"Get It Done","Plan it out"},
        {"Roles","Laws"},
        {"Sensible","Logical"},
        {"Information","Concepts"},
        {"Practice","Understand"},
        {"Trial & error","Visualize"},
        {"Realistic","Idealistic"},
        {"Details","Big Picture"},
        {"Touch, hold","Mental picture"},
        {"Read A Book","Daydream"},
        {"Get It Done","Create"},
        {"Roles","Principles"},
        {"Sensible","Logical"},
        {"Protect yourself","Save the Earth"},
        {"Practice","Think About"},
        {"Create","Share"},
        {"Ideals","Relationships"},
        {"Imagination","People"},
        {"Possibilities","Loyalties"},
        {"Listen to Music","Tell a story"},
        {"Daydream","Group activities"},
        {"Principles","Loyalties"},
        {"Insights","Emotions"},
        {"Save the Earth","For the Children"},
        {"Think about","Discuss"},
        {"Sharing","Strategy"},
        {"For the Children","Tell the Truth"},
        {"Details","Overview"},
        {"Concrete","Abstract"},
        {"Tell a story","Work a Puzzle"},
        {"Enjoy doing","Plan it well"},
        {"Loyalty","Law"},
        {"Emotion","Logic"},
        {"Join a group","Lead a group"},
        {"Discuss","Understand"},
        {"Strategy","Visualize"},
        {"Theoretical","Idealistic"},
        {"Experiment","Invent"},
        {"Think","Imagine"},
        {"Solve a Puzzle","Daydream"},
        {"Plan it out","Create"},
        {"Laws","Principles"},
        {"Logic","Metaphor"},
        {"Tell the Truth","Save the Earth"},
        {"Analyze","Discover"}
    };


    private CPIInventoryCatalog(){
        super();
    }
}
