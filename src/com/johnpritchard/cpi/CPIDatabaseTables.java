/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.HashMap;

/**
 * 
 */
public final class CPIDatabaseTables {

    public final static String AUTHORITY = "com.johnpritchard.cpi.provider.CPI";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/cpi");

    public static final String CONTENT_TYPE_LIST = "vnd.android.cursor.dir/vnd.johnpritchard.cpi";

    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd.johnpritchard.cpi";

    /**
     * Columns in table "results"
     */
    public final static class Results
        extends Object
        implements android.provider.BaseColumns
    {

        protected final static String IDENTIFIER = "identifier";

        protected final static String TITLE = "title";

        public final static String SF = "sf";

        public final static String ST = "st";

        public final static String NF = "nf";

        public final static String NT = "nt";

        public final static String CREATED = "created";

        public final static String COMPLETED = "completed";


        private final static String[] InternalList = {
            _ID,
            IDENTIFIER,
            TITLE,
            SF,
            ST,
            NF,
            NT,
            CREATED,
            COMPLETED
        };
        protected final static String[] ProjectionInternal(){

            return InternalList.clone();
        }
        public final static HashMap<String,String> Internal(){
            HashMap<String, String> internal = new HashMap();

            for (String column : InternalList){
                internal.put(column,column);
            }
            return internal;
        }
        private final static String[] ExportList = {
            _ID,
            SF,
            ST,
            NF,
            NT,
            CREATED,
            COMPLETED
        };
        protected final static String[] ProjectionExport(){

            return ExportList.clone();
        }
        public final static HashMap<String,String> Export(){
            HashMap<String, String> export = new HashMap();

            for (String column : ExportList){
                export.put(column,column);
            }
            return export;
        }
        public final static String[] Export(String[] projection){
            if (null == projection){
                return ExportList.clone();
            }
            else {
                boolean excluded = false;

                for (String p : projection){

                    if (IDENTIFIER.equalsIgnoreCase(p)){
                        excluded = true;
                        break;
                    }
                    else if (TITLE.equalsIgnoreCase(p)){
                        excluded = true;
                        break;
                    }
                }

                if (excluded){
                    return ExportList.clone();
                }
                else {
                    return projection;
                }
            }
        }
        private Results(){
            super();
        }
    }
    /**
     * Columns in table "session"
     */
    public final static class Session
        extends Object
        implements android.provider.BaseColumns
    {

        protected final static String INDEX = "lidx";

        protected final static String CHOICE = "choice";


        private final static String[] InternalList = {
            _ID,
            INDEX,
            CHOICE
        };
        protected final static String[] ProjectionInternal(){

            return InternalList.clone();
        }
        public final static HashMap<String,String> Internal(){
            HashMap<String, String> internal = new HashMap();

            for (String column : InternalList){
                internal.put(column,column);
            }
            return internal;
        }
        private Session(){
            super();
        }
    }


    private CPIDatabaseTables(){
        super();
    }
}
