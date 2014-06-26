/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/** 
 * This app employs a game style architecture, similar to Android
 * Drawables, so that the current user "state of activity" is
 * preserved on startup.  
 */
public final class CPIPageIntroActivity
    extends ObjectActivity
{

    private View2D view;


    @Override
    public void onCreate(Bundle state)
    {
        CPI.Activate2D(this);

        super.onCreate(state);
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        CPIDatabase.Init(this);

        this.preferences = this.getSharedPreferences("cpi.properties",MODE_PRIVATE);

        this.view = new View2D(this);

        this.setContentView(this.view);

        this.view.onCreate(this.preferences);
    }
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences.Editor preferences = this.preferences.edit();

        this.view.onPause(preferences);

        preferences.commit();
    }
    @Override
    protected void onResume(){
        super.onResume();

        this.view.onResume();
    }
}
