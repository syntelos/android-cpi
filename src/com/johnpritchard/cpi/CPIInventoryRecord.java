/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 
 * @see CPIDatabase
 */
public final class CPIInventoryRecord
    extends Object
    implements CPICode.Data
{
    /**
     * This singleton is employed as a canonical application register
     * object.
     */
    public final static CPIInventoryRecord Instance = new CPIInventoryRecord();

    private static ArrayList<CPIInventoryRecord> InstanceList;
    /**
     */
    public final static void ListClear(){
        InstanceList = null;
    }
    /**
     * @return Cached copy of database query result
     */
    public final static List<CPIInventoryRecord> List(){
        if (null == InstanceList){
            ArrayList<CPIInventoryRecord> list = new ArrayList();

            SQLiteDatabase db = CPIDatabase.Readable();
            try {
                SQLiteQueryBuilder rQ = CPIDatabase.QueryResultsInternal();
                /*
                 * In "created" order
                 */
                Cursor cursor = rQ.query(db,null,null,null,null,null,CPIDatabaseTables.Results.CREATED+" asc",null);
                try {
                    while (cursor.moveToNext()){

                        CPIInventoryRecord instance = (new CPIInventoryRecord()).results(cursor);

                        if (instance.hasIdentifier()){

                            list.add(instance);
                        }
                    }
                }
                finally {
                    cursor.close();
                }
            }
            finally {
                db.close();
            }
            InstanceList = list;
        }
        return (List<CPIInventoryRecord>)InstanceList.clone();
    }


    private long results = -1;

    private String identifier, title;

    private Float sf, st, nf, nt;
    /**
     * As many as sixty elements
     */
    private List<CPIInventory> session;

    private Date created;

    private Date completed;


    public CPIInventoryRecord(){
        super();
    }


    /**
     * Used by {@link #write()}
     */
    public boolean isOpen(){
        return (null != created && null == completed);
    }
    /**
     * Used by {@link #read()}
     */
    public boolean isClosed(){
        return (null == created);
    }
    /**
     * Reinitialize record to state of source
     */
    public CPIInventoryRecord copy(CPIInventoryRecord source){
        if (null == source)
            return this.clear();
        else {
            this.results = source.results;
            this.identifier = source.identifier;
            this.title = source.title;
            this.sf = source.sf;
            this.st = source.st;
            this.nf = source.nf;
            this.nt = source.nt;
            if (null != source.session){
                this.session = (List<CPIInventory>)((ArrayList<CPIInventory>)source.session).clone();
            }
            this.created = source.created;
            this.completed = source.completed;
            return this;
        }
    }
    /**
     * Reinitialize record to init state
     */
    public CPIInventoryRecord clear(){
        results = -1;
        identifier = null;
        sf = null;
        st = null;
        nf = null;
        nt = null;
        session = null;
        created = null;
        completed = null;
        return this;
    }
    /**
     * Create a new record
     */
    public CPIInventoryRecord create(){
        clear();
        setIdentifier();
        setCreated();
        setSession();
        return this;
    }
    /**
     * Used by {@link CPIPageView}
     */
    protected boolean view(){
        return (hasCompleted() || CPIInventory.Size == read());
    }
    /**
     * Load the referenced record, or the most recently created record.
     * 
     * @return CPIInventory index
     */
    protected int read(){

        if (this.isClosed()){

            SQLiteDatabase db = CPIDatabase.Readable();
            try {
                SQLiteQueryBuilder rQ = null;

                if (0 > this.results){
                    rQ = CPIDatabase.QueryResultsInternal();
                }
                else {
                    rQ = CPIDatabase.QueryResultsInternal(this.results);
                }
                /*
                 * Load the most recently created record
                 */
                Cursor cursor = rQ.query(db,null,null,null,null,null,CPIDatabaseTables.Results.CREATED+" desc","1");
                if (cursor.moveToFirst()){
                    try {
                        this.results(cursor);
                    }
                    finally {
                        cursor.close();
                    }
                }
                /*
                 * Ensure initial state
                 */
                if (hasNotIdentifier()){
                    setIdentifier();
                }
                if (hasNotCreated()){
                    setCreated();
                }
                /*
                 * Load the session table when the referenced results
                 * record is incomplete
                 */
                if (hasNotCompleted()){

                    setSession();

                    SQLiteQueryBuilder sQ = CPIDatabase.QuerySessionInternal();

                    cursor = sQ.query(db,null,null,null,null,null,CPIDatabaseTables.Session.INDEX+" asc",null);
                    try {
                        this.session(cursor);
                    }
                    finally {
                        cursor.close();
                    }
                }
            }
            finally {
                db.close();
            }
        }
        return getSessionIndex();
    }
    /**
     * Used by {@link CPIPageInventory} to write results 
     */
    protected void write(){

        if (this.isOpen()){
            CPIInventoryRecord.InstanceList = null;

            SQLiteDatabase db = CPIDatabase.Writable();
            try {
                CPIInventory.Complete(this);
                /*
                 * Results
                 */
                ContentValues results = this.results();

                if (0L > this.results){

                    this.results = db.insert(CPIDatabase.RESULTS,CPIDatabaseTables.Results.TITLE,results);
                    if (0L > this.results){

                        db.update(CPIDatabase.RESULTS,results,CPIDatabaseTables.Results.IDENTIFIER+" = "+this.identifier,null);
                    }
                }
                else {

                    if (1 > db.update(CPIDatabase.RESULTS,results,CPIDatabaseTables.Results.IDENTIFIER+" = "+this.identifier,null)){

                        this.results = db.insert(CPIDatabase.RESULTS,CPIDatabaseTables.Results.TITLE,results);
                    }
                }
                /*
                 * Session
                 */
                if (hasCompleted()){
                    /*
                     * Clear the session table
                     */
                    db.delete(CPIDatabase.SESSION,null,null);
                }
            }
            finally {
                db.close();
            }
        }
    }
    public boolean hasResults(){
        return (-1L < results);
    }
    public boolean hasNotResults(){
        return (0 > results);
    }
    public long getResults(){
        return results;
    }
    public CPIInventoryRecord setResults(long results){
        this.results = results;
        return this;
    }
    public boolean hasIdentifier(){
        return (null != identifier);
    }
    public boolean hasNotIdentifier(){
        return (null == identifier);
    }
    public String getIdentifier(){
        return identifier;
    }
    public CPIInventoryRecord setIdentifier(){
        identifier = RandomIdentifier();
        return this;
    }
    public CPIInventoryRecord setIdentifier(String identifier){
        this.identifier = identifier;
        return this;
    }
    public boolean hasTitle(){
        return (null != title);
    }
    public boolean hasNotTitle(){
        return (null == title);
    }
    public String getTitle(){
        return title;
    }
    public CPIInventoryRecord setTitle(String title){
        this.title = title;
        return this;
    }
    public boolean hasSf(){
        return (null != sf);
    }
    public boolean hasNotSf(){
        return (null == sf);
    }
    public Float getSf(){
        return sf;
    }
    public CPIInventoryRecord setSf(Float sf){
        this.sf = sf;
        return this;
    }
    public boolean hasSt(){
        return (null != st);
    }
    public boolean hasNotSt(){
        return (null == st);
    }
    public Float getSt(){
        return st;
    }
    public CPIInventoryRecord setSt(Float st){
        this.st = st;
        return this;
    }
    public boolean hasNf(){
        return (null != nf);
    }
    public boolean hasNotNf(){
        return (null == nf);
    }
    public Float getNf(){
        return nf;
    }
    public CPIInventoryRecord setNf(Float nf){
        this.nf = nf;
        return this;
    }
    public boolean hasNt(){
        return (null != nt);
    }
    public boolean hasNotNt(){
        return (null == nt);
    }
    public Float getNt(){
        return nt;
    }
    public CPIInventoryRecord setNt(Float nt){
        this.nt = nt;
        return this;
    }
    public boolean hasSession(){
        return (null != session);
    }
    public boolean hasNotSession(){
        return (null == session);
    }
    public boolean isNotEmptySession(){
        return (null != session && (!session.isEmpty()));
    }
    public List<CPIInventory> getSession(){
        return session;
    }
    /**
     * Used by {@link #updateSession}
     */
    protected List<CPIInventory> getCreateSession(){
        if (null == this.session){
            this.session = new java.util.ArrayList(60);
        }
        return this.session;
    }
    public CPIInventoryRecord setSession(){
        this.session = new java.util.ArrayList(60);
        return this;
    }
    public CPIInventoryRecord setSession(List<CPIInventory> session){
        this.session = session;
        return this;
    }
    /**
     * Used by {@link #open()}.
     */
    protected int getSessionIndex(){

        if (this.hasCompleted()){

            return CPIInventory.Size;
        }
        else if (null == this.session){

            return 0;
        }
        else {

            return this.session.size();
        }
    }
    /**
     * Used by {@link CPIPageInventory} 
     */
    protected boolean updateSession(int index, CPIInventory input){

        if (-1 < index && index < CPIInventory.Size){

            SQLiteDatabase db = CPIDatabase.Writable();
            try {
                ContentValues values = new ContentValues();
                {
                    values.put(CPIDatabaseTables.Session.INDEX,index);
                    values.put(CPIDatabaseTables.Session.CHOICE,input.name());
                }

                long id = db.insert(CPIDatabase.SESSION,null,values);
                if (0L > id){

                    if (1 != db.update(CPIDatabase.SESSION,values,CPIDatabaseTables.Session.INDEX+" = "+index,null))
                    {
                        return false;
                    }
                }

                final List<CPIInventory> session = getCreateSession();

                final int session_size = session.size();

                if (index < session_size){

                    session.set(index,input);

                    return true;
                }
                else if (index == session_size){

                    session.add(input);

                    return true;
                }
                else {
                    throw new IllegalStateException(String.valueOf(index));
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
    public boolean hasCreated(){
        return (null != created);
    }
    public boolean hasNotCreated(){
        return (null == created);
    }
    public Date getCreated(){
        return created;
    }
    public CPIInventoryRecord setCreated(){
        this.created = new Date();
        return this;
    }
    public CPIInventoryRecord setCreated(Date created){
        this.created = created;
        return this;
    }
    public boolean hasCompleted(){
        return (null != completed);
    }
    public boolean hasNotCompleted(){
        return (null == completed);
    }
    public Date getCompleted(){
        return completed;
    }
    public CPIInventoryRecord setCompleted(){
        this.completed = new Date();
        return this;
    }
    public CPIInventoryRecord setCompleted(Date completed){
        this.completed = completed;
        return this;
    }
    public CPICode.Encode getCPICodeEncode(){

        return new CPICode.Encode(this);
    }
    public boolean hasCPICodeData(){
        return this.hasSf();
    }
    public boolean hasNotCPICodeData(){
        return this.hasNotSf();
    }
    public String toStringSf(){
        Float sf = this.getSf();
        if (null == sf)
            return null;
        else
            return CPICode.Format(sf);
    }
    public String toStringSt(){
        Float st = this.getSt();
        if (null == st)
            return null;
        else
            return CPICode.Format(st);
    }
    public String toStringNf(){
        Float nf = this.getNf();
        if (null == nf)
            return null;
        else
            return CPICode.Format(nf);
    }
    public String toStringNt(){
        Float nt = this.getNt();
        if (null == nt)
            return null;
        else
            return CPICode.Format(nt);
    }
    private CPIInventoryRecord results(Cursor cursor){

        this.results = cursor.getLong(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results._ID));
        this.identifier = cursor.getString(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.IDENTIFIER));
        this.title = cursor.getString(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.TITLE));
        this.sf = cursor.getFloat(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.SF));
        this.st = cursor.getFloat(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.ST));
        this.nf = cursor.getFloat(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.NF));
        this.nt = cursor.getFloat(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.NT));

        final long created = cursor.getLong(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.CREATED));
        if (0 < created){
            this.created = new Date(created);
        }
        else {
            this.created = null;
        }

        final long completed = cursor.getLong(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results.COMPLETED));
        if (0 < completed){
            this.completed = new Date(completed);
        }
        else {
            this.completed = null;
        }
        return this;
    }
    private ContentValues results(){
        ContentValues values = new ContentValues();
        if (hasResults()){
            values.put(CPIDatabaseTables.Results._ID,this.results);
        }
        if (hasIdentifier()){
            values.put(CPIDatabaseTables.Results.IDENTIFIER,this.identifier);
        }
        if (hasTitle()){
            values.put(CPIDatabaseTables.Results.TITLE,this.title);
        }
        if (hasSf()){
            values.put(CPIDatabaseTables.Results.SF,this.sf);
        }
        if (hasSt()){
            values.put(CPIDatabaseTables.Results.ST,this.st);
        }
        if (hasNf()){
            values.put(CPIDatabaseTables.Results.NF,this.nf);
        }
        if (hasNt()){
            values.put(CPIDatabaseTables.Results.NT,this.nt);
        }
        if (hasCreated()){
            values.put(CPIDatabaseTables.Results.CREATED,this.created.getTime());
        }
        if (hasCompleted()){
            values.put(CPIDatabaseTables.Results.COMPLETED,this.completed.getTime());
        }
        return values;
    }
    private CPIInventoryRecord session(Cursor cursor){
        while (cursor.moveToNext()){

            int index = cursor.getInt(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Session.INDEX));

            String choice = cursor.getString(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Session.CHOICE));

            this.session.set(index,CPIInventory.valueOf(choice));
        }
        return this;
    }
    private ContentValues session(int index){
        ContentValues values = new ContentValues();
        {
            values.put(CPIDatabaseTables.Session.INDEX,index);
            values.put(CPIDatabaseTables.Session.CHOICE,session.get(index).name());
        }
        return values;
    }
    private final static int RandomIdentifierOctets = 12;

    public final static String RandomIdentifier(){
        byte[] bits = new byte[RandomIdentifierOctets];
        {
            new Random().nextBytes(bits);
        }
        return RandomIdentifierPathclean(B64.encodeBytes(bits));
    }
    private final static String RandomIdentifierPathclean(String r){
        char[] cary = r.toCharArray();
        final int count = cary.length;
        boolean change = false;
        for (int cc = 0; cc < count; cc++){
            switch (cary[cc]){
            case '/':
                change = true;
                cary[cc] = 'A';
                break;
            case '+':
                change = true;
                cary[cc] = 'B';
                break;
            case '\r':
                change = true;
                cary[cc] = 'C';
                break;
            case '\n':
                change = true;
                cary[cc] = 'D';
                break;
            case '=':
                change = true;
                cary[cc] = 'E';
                break;
            default:
                break;
            }
        }
        if (change)
            return new String(cary,0,count);
        else
            return r;
    }

}
