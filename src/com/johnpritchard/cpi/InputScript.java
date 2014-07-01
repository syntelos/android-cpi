/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public interface InputScript {

    boolean isEnum();

    boolean isFiltered();

    boolean isSkipping();

    int ordinal();

    String name();

    Input type();

    String toString();

    /**
     * 
     */
    public final class Key
        extends Object
        implements InputScript
    {

        public final Input type = Input.Key;

        public final char key;


        public Key(char key){
            super();
            this.key = key;
        }


        public boolean isEnum(){
            return false;
        }
        public boolean isFiltered(){
            return true;
        }
        public boolean isSkipping(){
            return false;
        }
        public int ordinal(){
            return type.ordinal();
        }
        public String name(){
            return type.name();
        }
        public Input type(){
            return type;
        }
        public String toString(){
            return type.name()+"[0x"+Integer.toHexString(key)+",'"+key+"']";
        }
    }
    /**
     * 
     */
    public final class Database
        extends Object
        implements InputScript
    {
        /**
         * 
         */
        public enum Op {
            Init,
            Practice,
            Inventory,
            Input,
            Completion,
            Completed,
            CompletedPrev,
            CompletedNext;
        }


        public final Input type = Input.Database;

        public final Database.Op op;

        public final int index;

        public final CPIInventory input;

        public final ObjectActivity context;


        public Database(Op op){
            this(op,null,-1,null);
        }
        public Database(Op op, int index, CPIInventory input){
            this(op,null,index,input);
        }
        public Database(Op op, ObjectActivity context){
            this(op,context,-1,null);
        }
        private Database(Op op, ObjectActivity context, int index, CPIInventory input){
            super();
            if (null != op){
                this.op = op;

                if (Op.Input == op || Op.Completion == op){

                    if (-1 < index && CPIInventory.Size > index && null != input){
                        this.context = null;
                        this.index = index;
                        this.input = input;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
                else if (Op.Init == op){

                    if (null != context){
                        this.context = context;
                        this.index = -1;
                        this.input = null;
                    }
                    else {
                        throw new IllegalArgumentException();
                    }
                }
                else {
                    this.context = null;
                    this.index = -1;
                    this.input = null;
                }
            }
            else {
                throw new IllegalArgumentException();
            }
        }


        public boolean isEnum(){
            return false;
        }
        public boolean isFiltered(){
            return true;
        }
        public boolean isSkipping(){
            return (Op.Init == op);
        }
        public int ordinal(){
            return type.ordinal();
        }
        public String name(){
            return type.name();
        }
        public Input type(){
            return type;
        }
        public String toString(){
            return type.name()+' '+op.name();
        }
        public void eval(){
            switch(this.op){
            case Init:
                {
                    CPIDatabase.Init(context);
                }
                break;
            case Practice:
                {
                    CPIDatabase.Practice();
                }
                break;
            case Inventory:
                {
                    CPIDatabase.Inventory();
                }
                break;
            case Input:
                {
                    CPIDatabase.Input(index,input);
                }
                break;
            case Completion:
                {
                    CPIDatabase.Completion(index,input);
                }
                break;
            case Completed:
                {
                    CPIDatabase.Completed();
                }
                break;
            case CompletedPrev:
                {
                    CPIDatabase.CompletedPrev();
                }
                break;
            case CompletedNext:
                {
                    CPIDatabase.CompletedNext();
                }
                break;
            default:
                throw new IllegalStateException(this.op.name());
            }
        }
    }
}
