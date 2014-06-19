/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.HashMap;

/**
 * The CPI database has two tables: results and session.  The results
 * table may have one incomplete record, and this incomplete record
 * corresponds with the state of the session table.  When the results
 * table has no incomplete record, the session table should be empty.
 * 
 * @see CPIInventoryRecord
 */
public final class CPIDatabase 
    extends android.database.sqlite.SQLiteOpenHelper
{
    private static CPIDatabase Instance;

    public static void Init(Context cx){

        if (null == Instance){
            Instance = new CPIDatabase(cx);
        }
    }
    public static SQLiteDatabase Readable(){

        if (null != Instance){
            return Instance.getReadableDatabase();
        }
        else {
            throw new IllegalStateException();
        }
    }
    public static SQLiteDatabase Writable(){

        if (null != Instance){
            return Instance.getWritableDatabase();
        }
        else {
            throw new IllegalStateException();
        }
    }
    public static SQLiteQueryBuilder QueryResultsExport(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(RESULTS);
        qb.setProjectionMap(RESULTS_MAP_EXPORT);

        return qb;
    }
    public static SQLiteQueryBuilder QueryResultsExport(String id){

        return QueryResultsExport(Long.parseLong(id));
    }
    public static SQLiteQueryBuilder QueryResultsExport(long id){
        SQLiteQueryBuilder qb = QueryResultsExport();

        qb.appendWhere(CPIDatabaseTables.Results._ID + " = " + id);

        return qb;
    }
    public static SQLiteQueryBuilder QueryResultsInternal(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(RESULTS);
        qb.setProjectionMap(RESULTS_MAP_INTERNAL);

        return qb;
    }
    public static SQLiteQueryBuilder QueryResultsInternal(String id){

        return QueryResultsInternal(Long.parseLong(id));
    }
    public static SQLiteQueryBuilder QueryResultsInternal(long id){
        SQLiteQueryBuilder qb = QueryResultsInternal();

        qb.appendWhere(CPIDatabaseTables.Results._ID + " = " + id);

        return qb;
    }
    public static SQLiteQueryBuilder QuerySessionInternal(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(SESSION);
        qb.setProjectionMap(SESSION_MAP_INTERNAL);

        return qb;
    }

    private static HashMap<String, String> RESULTS_MAP_INTERNAL = CPIDatabaseTables.Results.Internal();

    private static HashMap<String, String> RESULTS_MAP_EXPORT = CPIDatabaseTables.Results.Export();

    private static HashMap<String, String> SESSION_MAP_INTERNAL = CPIDatabaseTables.Session.Internal();


    protected final static String NAME = "cpi.db";
    protected final static int VERSION = 1;
    protected final static String RESULTS = "results";
    protected final static String SESSION = "session";


    private CPIDatabase(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RESULTS + " ( "
                   + CPIDatabaseTables.Results._ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, "
                   + CPIDatabaseTables.Results.IDENTIFIER + " TEXT UNIQUE NOT NULL, "
                   + CPIDatabaseTables.Results.TITLE + " TEXT, "
                   + CPIDatabaseTables.Results.SF + " REAL, "
                   + CPIDatabaseTables.Results.ST + " REAL, "
                   + CPIDatabaseTables.Results.NF + " REAL, "
                   + CPIDatabaseTables.Results.NT + " REAL, "
                   + CPIDatabaseTables.Results.CREATED + " INTEGER, "
                   + CPIDatabaseTables.Results.COMPLETED + " INTEGER"
                   + ");");
        db.execSQL("CREATE TABLE " + SESSION + " ( "
                   + CPIDatabaseTables.Session._ID + " INTEGER PRIMARY KEY ASC AUTOINCREMENT, "
                   + CPIDatabaseTables.Session.INDEX + " INTEGER UNIQUE, "
                   + CPIDatabaseTables.Session.CHOICE + " TEXT NOT NULL"
                   + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }
}
