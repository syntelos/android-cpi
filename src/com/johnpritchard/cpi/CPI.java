/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 
 */
public final class CPI
    extends android.app.Application
{
    private final static String TAG = ObjectLog.TAG;

    protected final static int FileModePublic = (Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);


    private static ObjectActivity Activity2D;

    /**
     * Called from {@link View2D#pageTo(com.johnpritchard.cpi.Page)} 
     */
    public final static void StartActivity(Page page){

        Intent intent;

        switch(page){
        case intro:
            intent = new Intent(Activity2D, CPIPageIntroActivity.class);
            break;
        case start:
            intent = new Intent(Activity2D, CPIPageStartActivity.class);
            break;
        case view:
            intent = new Intent(Activity2D, CPIPageViewActivity.class);
            break;
        case practice:
            intent = new Intent(Activity2D, CPIPagePracticeActivity.class);
            break;
        case inventory:
            intent = new Intent(Activity2D, CPIPageInventoryActivity.class);
            break;
        case about:
            intent = new Intent(Activity2D, CPIPageAboutActivity.class);
            break;
        default:
            return;
        }
        Activity2D.startActivity(intent);
    }
    /**
     * Called from activity2D
     */
    protected final static void Activate2D(ObjectActivity instance){

        Activity2D = instance;
    }
    /**
     * Called from activity3D
     */
    protected final static void Activate3D(ObjectActivity na){
        throw new UnsupportedOperationException();// notational
    }
    protected final static void Post2D(Runnable action){

        Activity2D.runOnUiThread(action);
    }
    protected final static void Post3D(Runnable action){
    }
    protected final static void ScreenShot3D(){
    }
    protected final static void Toast2D(String msg){

        Toast toast = Toast.makeText(Activity2D, msg, Toast.LENGTH_SHORT);

        toast.show();
    }
    protected final static void Toast3D(String msg){
    }
    protected final static File ExternalDirectory2D(String type){
        return Activity2D.getExternalFilesDir(type);
    }
    protected final static File ExternalDirectory3D(String type){
        throw new UnsupportedOperationException();// no lvalue
    }
    protected final static FileOutputStream InternalFile2D(String filename)
        throws FileNotFoundException
    {
        return Activity2D.openFileOutput(filename,FileModePublic);
    }
    protected final static FileOutputStream InternalFile3D(String filename)
        throws FileNotFoundException
    {
        throw new UnsupportedOperationException();// no lvalue
    }


    public CPI(){
        super();
    }


    protected void verbose(String m){
        Log.i(TAG,"CPI "+m);
    }
    protected void verbose(String m, Throwable t){
        Log.i(TAG,"CPI "+m,t);
    }
    protected void debug(String m){
        Log.d(TAG,"CPI "+m);
    }
    protected void debug(String m, Throwable t){
        Log.d(TAG,"CPI "+m,t);
    }
    protected void info(String m){
        Log.i(TAG,"CPI "+m);
    }
    protected void info(String m, Throwable t){
        Log.i(TAG,"CPI "+m,t);
    }
    protected void warn(String m){
        Log.w(TAG,"CPI "+m);
    }
    protected void warn(String m, Throwable t){
        Log.w(TAG,"CPI "+m,t);
    }
    protected void error(String m){
        Log.e(TAG,"CPI "+m);
    }
    protected void error(String m, Throwable t){
        Log.e(TAG,"CPI "+m,t);
    }
    protected void wtf(String m){
        Log.wtf(TAG,"CPI "+m);
    }
    protected void wtf(String m, Throwable t){
        Log.wtf(TAG,"CPI "+m,t);
    }
    protected static void Verbose(String m){
        Log.i(TAG,"CPI "+m);
    }
    protected static void Verbose(String m, Throwable t){
        Log.i(TAG,"CPI "+m,t);
    }
    protected static void Debug(String m){
        Log.d(TAG,"CPI "+m);
    }
    protected static void Debug(String m, Throwable t){
        Log.d(TAG,"CPI "+m,t);
    }
    protected static void Info(String m){
        Log.i(TAG,"CPI "+m);
    }
    protected static void Info(String m, Throwable t){
        Log.i(TAG,"CPI "+m,t);
    }
    protected static void Warn(String m){
        Log.w(TAG,"CPI "+m);
    }
    protected static void Warn(String m, Throwable t){
        Log.w(TAG,"CPI "+m,t);
    }
    protected static void Error(String m){
        Log.e(TAG,"CPI "+m);
    }
    protected static void Error(String m, Throwable t){
        Log.e(TAG,"CPI "+m,t);
    }
    protected static void WTF(String m){
        Log.wtf(TAG,"CPI "+m);
    }
    protected static void WTF(String m, Throwable t){
        Log.wtf(TAG,"CPI "+m,t);
    }
}
