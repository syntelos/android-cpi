/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.Context;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Read only access to a limited set of columns from the results
 * table: suitable for the application of CPI data.
 */
public class CPIProvider
    extends android.content.ContentProvider
{

    private final static int QUERY_LIST = 1;
    private final static int QUERY_ID = 2;

    private static final UriMatcher QueryMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        QueryMatcher.addURI(CPIDatabaseTables.AUTHORITY, CPIDatabase.RESULTS, QUERY_LIST);
        QueryMatcher.addURI(CPIDatabaseTables.AUTHORITY, CPIDatabase.RESULTS+"/#", QUERY_ID);
    }



    public CPIProvider(){
        super();
    }


    @Override
    public boolean onCreate() {
        CPIDatabase.Init(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, 
                        String selection, String[] selectionArgs,
                        String sortOrder)
    {
        SQLiteQueryBuilder qb = null;

        switch(QueryMatcher.match(uri)){
        case QUERY_LIST:
            qb = CPIDatabase.QueryResultsExport();
            break;
        case QUERY_ID:
            qb = CPIDatabase.QueryResultsExport(uri.getPathSegments().get(1));
            break;
        default:
            throw new IllegalArgumentException(uri.toString());
        }

        projection = CPIDatabaseTables.Results.Export(projection);

        if (null != sortOrder && 1 > sortOrder.length()){

            sortOrder = null;
        }

        SQLiteDatabase db = CPIDatabase.Readable();
        try {
            Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            {
                c.setNotificationUri(getContext().getContentResolver(), uri);
            }
            return c;
        }
        finally {
            db.close();
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (QueryMatcher.match(uri)) {
        case QUERY_LIST:
            return CPIDatabaseTables.CONTENT_TYPE_LIST;

        case QUERY_ID:
            return CPIDatabaseTables.CONTENT_TYPE_ITEM;

        default:
            throw new IllegalArgumentException(uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        return 0;
    }

}
