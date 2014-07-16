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
    extends ObjectLog
    implements CPICode.Data
{
    /**
     * This singleton is employed as a canonical application register
     * object.
     */
    public final static CPIInventoryRecord Instance = new CPIInventoryRecord();


    protected volatile CPIProcess process;

    protected volatile long cursor = -1L;

    protected volatile String identifier, title;

    protected volatile float sf, st, nf, nt;
    /**
     * As many as sixty elements
     */
    protected volatile List<CPIInventory> session;

    protected volatile Date created;

    protected volatile Date completed;


    public CPIInventoryRecord(){
        super();
    }


    public boolean inProcess(){

        return (null != this.process);
    }
    public CPIProcess whichProcess(){

        return this.process;
    }
    public void practice(){
        clear();
        {
            this.process = CPIProcess.Practice;
        }
    }
    public void inventory(){
        clear();
        {
            this.process = CPIProcess.Inventory;
        }
    }
    public void completed(){
        clear();
        {
            this.process = CPIProcess.Completed;
        }
    }
    /**
     * Has cursor and created but not completed
     */
    public boolean isOpen(){
        return (-1L < cursor && null != created && null == completed);
    }
    /**
     * Has cursor and created and completed
     */
    public boolean isClosed(){
        return (-1L < cursor && null != created && null != completed);
    }
    /**
     * Reinitialize record to init state
     */
    private CPIInventoryRecord clear(){
        process = null;
        cursor = -1;
        identifier = null;
        sf = 0.0f;
        st = 0.0f;
        nf = 0.0f;
        nt = 0.0f;
        session = null;
        created = null;
        completed = null;
        return this;
    }
    public boolean hasCursor(){
        return (-1L < cursor);
    }
    public boolean hasNotCursor(){
        return (0L > cursor);
    }
    public long getCursor(){
        return cursor;
    }
    public CPIInventoryRecord setCursor(long cursor){
        this.cursor = cursor;
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
    public CPIInventoryRecord setTitle(){
        this.title = this.created.toString();
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
    public void setSession(int index, CPIInventory input){

        final List<CPIInventory> session = this.getCreateSession();

        final int session_size = session.size();

        if (index < session_size){

            session.set(index,input);
        }
        else {

            session.add(input);
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
    public CPIInventoryRecord setCompleted(CPIInventory.Product product){
        this.nf = product.normalized_nf;
        this.sf = product.normalized_sf;
        this.nt = product.normalized_nt;
        this.st = product.normalized_st;

        return this.setCompleted();
    }
    public CPIInventoryRecord setCompleted(Date completed){
        this.completed = completed;
        return this;
    }
    public CPICode.Encode getCPICodeEncode(){

        return new CPICode.Encode(this);
    }
    public boolean hasCPICodeData(){

        return (0.0f != sf || 0.0f != st || 0.0f != nf || 0.0f != nt);
    }
    public boolean hasNotCPICodeData(){

        return (0.0f == sf && 0.0f == st && 0.0f == nf && 0.0f == nt);
    }
    public String toStringSf(){

        return CPICode.Format(sf);
    }
    public String toStringSt(){

        return CPICode.Format(st);
    }
    public String toStringNf(){

        return CPICode.Format(nf);
    }
    public String toStringNt(){

        return CPICode.Format(nt);
    }
    public CPIInventoryRecord readResults(Cursor cursor){

        this.cursor = cursor.getLong(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Results._ID));

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
        if (0L < completed){
            this.completed = new Date(completed);

            this.process = CPIProcess.Completed;
        }
        else {
            this.completed = null;

            this.process = CPIProcess.Inventory;
        }
        return this;
    }
    public ContentValues writeResults(){
        ContentValues values = new ContentValues();
        if (hasCursor()){

            values.put(CPIDatabaseTables.Results._ID,this.cursor);
        }

        if (hasIdentifier()){
            values.put(CPIDatabaseTables.Results.IDENTIFIER,this.identifier);
        }

        if (hasTitle()){
            values.put(CPIDatabaseTables.Results.TITLE,this.title);
        }

        values.put(CPIDatabaseTables.Results.SF,this.sf);

        values.put(CPIDatabaseTables.Results.ST,this.st);

        values.put(CPIDatabaseTables.Results.NF,this.nf);

        values.put(CPIDatabaseTables.Results.NT,this.nt);

        if (hasCreated()){
            values.put(CPIDatabaseTables.Results.CREATED,this.created.getTime());
        }

        if (hasCompleted()){
            values.put(CPIDatabaseTables.Results.COMPLETED,this.completed.getTime());
        }

        return values;
    }
    public CPIInventoryRecord readSession(Cursor cursor){

        final int index = cursor.getInt(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Session.INDEX));

        final String choice = cursor.getString(cursor.getColumnIndexOrThrow(CPIDatabaseTables.Session.CHOICE));

        setSession(index,CPIInventory.valueOf(choice));

        return this;
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
