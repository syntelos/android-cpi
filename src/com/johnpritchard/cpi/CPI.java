/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.Context;
import android.content.Intent;
import static android.content.Intent.*;
import android.net.Uri;
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

    private final static int IntentFlags = (FLAG_ACTIVITY_CLEAR_TOP|FLAG_DEBUG_LOG_RESOLUTION);
    //private final static int IntentFlags = (FLAG_DEBUG_LOG_RESOLUTION);


    private static volatile ObjectActivity Activity2D;

    /**
     * Called from {@link View2D#pageTo(com.johnpritchard.cpi.Page)} 
     */
    public final static void StartActivity(Page page){
        /*
         * 
         */
        switch(Activity2D.currentPage()){
        case intro:
            if (Page.intro == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                Activity2D.finish();
            }
            break;
        case start:
            if (Page.start == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                //Activity2D.finish();
            }
            break;
        case view:
            if (Page.view == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                Activity2D.finish();
            }
            break;
        case practice:
            if (Page.practice == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                Activity2D.finish();
            }
            break;
        case inventory:
            if (Page.inventory == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                Activity2D.finish();
            }
            break;
        case about:
            if (Page.about == page){
                throw new ViewAnimation.Shutdown();
            }
            else {
                Activity2D.finish();
            }
            break;
        default:
            return;
        }
        /*
         * 
         */
        switch(page){
        case intro:
            {
                final Intent intent = new Intent(Activity2D, CPIPageIntroActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        case start:
            {
                final Intent intent = new Intent(Activity2D, CPIPageStartActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        case view:
            {
                final Intent intent = new Intent(Activity2D, CPIPageViewActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        case practice:
            {
                final Intent intent = new Intent(Activity2D, CPIPagePracticeActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        case inventory:
            {
                final Intent intent = new Intent(Activity2D, CPIPageInventoryActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        case about:
            {
                final Intent intent = new Intent(Activity2D, CPIPageAboutActivity.class);
                {
                    intent.setFlags(IntentFlags);
                }
                Activity2D.startActivity(intent);
            }
            break;
        default:
            return;
        }
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
    /**
     * Requires call from looper thread
     */
    protected final static void ToastShort2D(String msg){

        Toast toast = Toast.makeText(Activity2D, msg, Toast.LENGTH_SHORT);

        toast.show();
    }
    /**
     * Requires call from looper thread
     */
    protected final static void ToastLong2D(String msg){

        Toast toast = Toast.makeText(Activity2D, msg, Toast.LENGTH_LONG);

        toast.show();
    }
    /**
     */
    protected final static void ActionView2D(Uri uri){

        Intent intent = new Intent(ACTION_VIEW);
        {
            intent.setData(uri);
        }
        Activity2D.startActivity(intent);
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
