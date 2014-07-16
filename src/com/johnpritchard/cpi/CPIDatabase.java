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
    private final static String TAG = ObjectLog.TAG;

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

            if (null == Instance || cx != Instance.context){

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

            SQLiteDatabase db = CPIDatabase.Writable();
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
                 */
                if (inventory.isOpen()){

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
                    /*
                     * New record
                     */
                    ContentValues values = inventory.writeResults();

                    inventory.cursor = db.insert(CPIDatabase.RESULTS,CPIDatabaseTables.Results.TITLE,values);
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
            long cursor = db.insert(CPIDatabase.SESSION,null,values);
        }
        catch (SQLiteException exc){

            final String where = CPIDatabaseTables.Session.INDEX+" = "+index;

            final String[] whereArgs = null;

            int rows = db.update(CPIDatabase.SESSION,values,where,whereArgs);
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
                }
                finally {
                    db.close();
                }

                CPIPageInventory.View();
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

                CPIInventory.Complete(inventory);
                try {
                    /*
                     * State
                     */
                    ContentValues state = inventory.writeResults();

                    if (-1L < inventory.cursor){

                        final String where = CPIDatabaseTables.Results._ID+" = "+inventory.cursor;

                        final String[] whereArgs = null;

                        int rows = db.update(CPIDatabase.RESULTS,state,where,whereArgs);
                    }
                    else {

                        final String where = CPIDatabaseTables.Results.IDENTIFIER+" = "+inventory.identifier;

                        final String[] whereArgs = null;

                        int rows = db.update(CPIDatabase.RESULTS,state,where,whereArgs);
                    }
                }
                finally {
                    /*
                     * Clear the session table
                     */
                    final String where = "1";

                    final String[] whereArgs = null;

                    int rows = db.delete(CPIDatabase.SESSION,where,whereArgs);
                }
            }
            finally {
                db.close();
            }

            CPI.StartActivity(Page.view);
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

                final String where = CPIDatabaseTables.Results.COMPLETED+" != 0";

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

                    CPIPageView.View();
                }
                finally {
                    cursor.close();
                }
            }
            finally {
                db.close();
            }
        }
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

                final String where = CPIDatabaseTables.Results.COMPLETED+" != 0";

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = null;

                final String limit = null;

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){


                        inventory.readResults(cursor);

                        CPIPageView.View();

                        return true;
                    }
                    else {

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

            return false;
        }
        else {

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

                final String where = CPIDatabaseTables.Results.COMPLETED+" != 0";

                final String[] whereargs = null;

                final String groupby = null;

                final String having = null;

                final String orderby = null;

                final String limit = null;

                Cursor cursor = rQ.query(db,projection,where,whereargs,groupby,having,orderby,limit);
                try {
                    if (cursor.moveToFirst()){

                        CPIInventoryRecord.Instance.readResults(cursor);

                        CPIPageView.View();

                        return true;
                    }
                    else {

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


    protected final Context context;


    private CPIDatabase(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
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

    protected static void Verbose(String m){
        Log.i(TAG,"CPIDatabase "+m);
    }
    protected static void Verbose(String m, Throwable t){
        Log.i(TAG,"CPIDatabase "+m,t);
    }
    protected static void Debug(String m){
        Log.d(TAG,"CPIDatabase "+m);
    }
    protected static void Debug(String m, Throwable t){
        Log.d(TAG,"CPIDatabase "+m,t);
    }
    protected static void Info(String m){
        Log.i(TAG,"CPIDatabase "+m);
    }
    protected static void Info(String m, Throwable t){
        Log.i(TAG,"CPIDatabase "+m,t);
    }
    protected static void Warn(String m){
        Log.w(TAG,"CPIDatabase "+m);
    }
    protected static void Warn(String m, Throwable t){
        Log.w(TAG,"CPIDatabase "+m,t);
    }
    protected static void Error(String m){
        Log.e(TAG,"CPIDatabase "+m);
    }
    protected static void Error(String m, Throwable t){
        Log.e(TAG,"CPIDatabase "+m,t);
    }
    protected static void WTF(String m){
        Log.wtf(TAG,"CPIDatabase "+m);
    }
    protected static void WTF(String m, Throwable t){
        Log.wtf(TAG,"CPIDatabase "+m,t);
    }
}
