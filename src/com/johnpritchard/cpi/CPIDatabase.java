/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

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
    private final static Object StaticMonitor = new Object();

    private volatile static CPIDatabase Instance;

    public static boolean IsUp(){

        return (null != Instance);
    }
    public static boolean IsDown(){

        return (null == Instance);
    }
    public static void Init(Context cx){
        synchronized(StaticMonitor){
            if (null == Instance){
                Instance = new CPIDatabase(cx);
            }
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
    /**
     * Prepare for practice
     */
    public static void Practice(){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        {
            inventory.practice();
        }
    }
    /**
     * Open a new or existing session
     */
    public static void Inventory(){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        {
            /*
             * Initialize a new session
             */
            inventory.inventory();

            inventory.setIdentifier();
            inventory.setCreated();
            inventory.setSession();
            inventory.setTitle();

            SQLiteDatabase db = CPIDatabase.Readable();
            try {
                SQLiteQueryBuilder rQ = CPIDatabase.QueryResultsInternal();
                /*
                 * Look at the most recent session
                 */
                final String[] projection = CPIDatabaseTables.Results.ProjectionInternal();

                final String where = null;

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = CPIDatabaseTables.Results.CREATED+" desc";

                final String limit = "1";

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){

                        inventory.readResults(cursor);
                    }
                }
                finally {
                    cursor.close();
                }
                /*
                 * Look at the session table: delete when the most
                 * recent session has completed (redundant to
                 * completion operation)
                 */
                if (null == inventory.completed){

                    SQLiteQueryBuilder sQ = CPIDatabase.QuerySessionInternal();

                    cursor = sQ.query(db,null,null,null,null,null,CPIDatabaseTables.Session.INDEX+" asc",null);
                    try {
                        while (cursor.moveToNext()){

                            inventory.readSession(cursor);
                        }
                    }
                    finally {
                        cursor.close();
                    }
                    /*
                     * The most recent session doesn't exist
                     */
                    final int six = inventory.getSessionIndex();

                    if (0 >= six || CPIInventory.Size <= six){
                        /*
                         * Create a new session
                         */
                        inventory.inventory();

                        inventory.setIdentifier();
                        inventory.setCreated();
                        inventory.setSession();
                        inventory.setTitle();
                        /*
                         * Ensure that the session table is clear
                         */
                        db.delete(CPIDatabase.SESSION,null,null);
                    }
                }
                else {
                    /*
                     * The most recent session has completed,
                     * therefore create a new session
                     */
                    inventory.inventory();

                    inventory.setIdentifier();
                    inventory.setCreated();
                    inventory.setSession();
                    inventory.setTitle();
                    /*
                     * Ensure that the session table is clear
                     */
                    db.delete(CPIDatabase.SESSION,null,null);
                }
            }
            finally {
                db.close();
            }
        }

        CPIPageInventory.View();
    }
    private static void Session(SQLiteDatabase db, int index, CPIInventory input){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;

        ContentValues values = new ContentValues();
        {
            values.put(CPIDatabaseTables.Session.INDEX,index);
            values.put(CPIDatabaseTables.Session.CHOICE,input.name());
        }

        try {
            final String where = CPIDatabaseTables.Session.INDEX+" = "+index;

            final String[] whereArgs = null;

            db.update(CPIDatabase.SESSION,values,where,whereArgs);
        }
        catch (SQLiteException exc){

            db.insert(CPIDatabase.SESSION,null,values);
        }

        inventory.setSession(index,input);
    }
    public static void Input(int index, CPIInventory input){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        {
            if (CPIProcess.Practice == inventory.process){

                CPI.StartActivity(Page.start);
            }
            else if (-1 < index && index < CPIInventory.Term){

                SQLiteDatabase db = CPIDatabase.Writable();
                try {
                    Session(db,index,input);

                    CPIPageInventory.Input();
                }
                finally {
                    db.close();
                }
            }
            else {
                Completion(index,input);
            }
        }
    }
    /**
     * Close an open session
     */
    private static void Completion(int index, CPIInventory input){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        {
            SQLiteDatabase db = Writable();
            try {
                Session(db,index,input);

                if (CPIInventory.Complete(inventory)){
                    try {
                        /*
                         * State
                         */
                        ContentValues state = inventory.writeResults();

                        if (-1L < inventory.cursor){

                            final String where = CPIDatabaseTables.Results._ID+" = "+inventory.cursor;

                            final String[] whereArgs = null;

                            db.update(CPIDatabase.RESULTS,state,where,whereArgs);
                        }
                        else {

                            final String where = CPIDatabaseTables.Results.IDENTIFIER+" = "+inventory.identifier;

                            final String[] whereArgs = null;

                            db.update(CPIDatabase.RESULTS,state,where,whereArgs);
                        }
                    }
                    finally {
                        /*
                         * Session
                         */
                        if (inventory.hasCompleted()){
                            /*
                             * Clear the session table
                             */
                            db.delete(CPIDatabase.SESSION,null,null);
                        }
                    }

                    CPI.StartActivity(Page.view);
                }
                else {
                    throw new IllegalStateException();
                }
            }
            finally {
                db.close();
            }
        }
    }
    /**
     * View the most recently closed session
     */
    public static void Completed(){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        {
            inventory.completed();

            SQLiteDatabase db = Readable();
            try {
                SQLiteQueryBuilder rQ = QueryResultsInternal();

                final String[] projection = CPIDatabaseTables.Results.ProjectionInternal();

                final String where = CPIDatabaseTables.Results.COMPLETED+" != NULL";

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = CPIDatabaseTables.Results.CREATED+" desc";

                final String limit = "1";

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){
                        /*
                         * Have history
                         */
                        inventory.readResults(cursor);
                    }
                    else {
                        /*
                         * No history
                         */
                    }
                }
                finally {
                    cursor.close();
                }
            }
            finally {
                db.close();
            }
        }

        CPIPageView.View();
    }
    /**
     * Called from poster to setup the {@link CPIInventoryRecord}
     */
    public static boolean CompletedPrev(){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        final long id = inventory.cursor;

        if (0L < id){

            SQLiteDatabase db = Readable();
            try {
                SQLiteQueryBuilder rQ = QueryResultsInternal(id-1);

                final String[] projection = CPIDatabaseTables.Results.ProjectionInternal();

                final String where = CPIDatabaseTables.Results.COMPLETED+" != NULL";

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = null;

                final String limit = null;

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){


                        inventory.readResults(cursor);

                        //Info("history prev: ok");

                        return true;
                    }
                    else {
                        //Info("history prev: <end>");

                        return false;
                    }
                }
                finally {
                    cursor.close();
                }
            }
            finally {
                db.close();
            }
        }
        else if (0L > id){
            //Info("history prev: <missing cursor>");

            return false;
        }
        else {
            //Info("history prev: <end>");

            return false;
        }
    }
    /**
     * Called from poster to setup the {@link CPIInventoryRecord}
     */
    public static boolean CompletedNext(){

        final CPIInventoryRecord inventory = CPIInventoryRecord.Instance;
        final long id = inventory.cursor;

        if (-1 < id){

            SQLiteDatabase db = Readable();
            try {
                SQLiteQueryBuilder rQ = QueryResultsInternal(id+1);

                final String[] projection = CPIDatabaseTables.Results.ProjectionInternal();

                final String where = CPIDatabaseTables.Results.COMPLETED+" != NULL";

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = null;

                final String limit = null;

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){
                        CPIInventoryRecord.Instance.readResults(cursor);

                        //Info("history next: ok");

                        return true;
                    }
                    else {

                        //Info("history next: <end>");

                        return false;
                    }
                }
                finally {
                    cursor.close();
                }
            }
            finally {
                db.close();
            }
        }
        else {
            //Info("history next: <missing cursor>");

            return false;
        }
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
