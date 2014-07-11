/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * 
 */
public enum CPIInventory
{
    L1, L2, L3, L4, R1, R2, R3, R4;
    
    public boolean isLeft(){
        switch (this){
        case L1:
            return true;
        case L2:
            return true;
        case L3:
            return true;
        case L4:
            return true;
        case R1:
            return false;
        case R2:
            return false;
        case R3:
            return false;
        case R4:
            return false;
        default:
            throw new IllegalArgumentException();
        }
    }

    public final static boolean IsComplete(CPIInventoryRecord record){
        if (null != record){

            return (Size == record.getSessionIndex());
        }
        else {

            return false;
        }
    }
    public final static boolean Complete(CPIInventoryRecord record){
        if (null != record){

            if (record.hasSession()){

                final List<CPIInventory> session = record.getSession();

                final int session_size = session.size();

                if (Size == session_size){

                    //Log.i(ObjectLog.TAG,"CPIInventory Complete Product");

                    record.setCompleted(new CPIInventory.Product(session));

                    return true;
                }
                else {
                    Log.e(ObjectLog.TAG,"CPIInventory Complete <session size "+session_size+">");
                }
            }
            else {
                Log.e(ObjectLog.TAG,"CPIInventory Complete <missing session>");
            }
        }
        else {
            throw new IllegalArgumentException();
        }
        return false;
    }
    public final static CPICode.Encode Encode(CPIInventoryRecord record){

        return new CPICode.Encode(record.nt,record.nf,record.st,record.sf);
    }
    public final static int ValueOf(CPIInventory ri){
        switch (ri){
        case L1:
            return 1;
        case L2:
            return 2;
        case L3:
            return 3;
        case L4:
            return 4;
        case R1:
            return 1;
        case R2:
            return 2;
        case R3:
            return 3;
        case R4:
            return 4;
        default:
            throw new IllegalArgumentException();
        }
    }

    public final static int Size = 60;

    public final static int Term = (Size-1);
    /**
     * 
     */
    public static class Product {

        public final float normalized_sf;
        public final float normalized_st;
        public final float normalized_nf;
        public final float normalized_nt;

        public Product (List<CPIInventory> session)
        {
            super();
            if (CPIInventory.Size == session.size()){
                float raw_sf = 0.0F;
                float raw_st = 0.0F;
                float raw_nf = 0.0F;
                float raw_nt = 0.0F;
                float raw_max = 0.0F;

                for (int cc = 0; cc < CPIInventory.Size; cc++) {
                    CPIInventory usr_ri = session.get(cc);
                    CPIQuadrant usr_grp = CPIQuadrant.For(usr_ri.isLeft(), cc);
                    float usr_val = CPIInventory.ValueOf(usr_ri);

                    switch(usr_grp){
                    case SF:
                        raw_sf += usr_val;
                        if (raw_sf > raw_max)
                            raw_max = raw_sf;
                        break;
                    case ST:
                        raw_st += usr_val;
                        if (raw_st > raw_max)
                            raw_max = raw_st;
                        break;
                    case NF:
                        raw_nf += usr_val;
                        if (raw_nf > raw_max)
                            raw_max = raw_nf;
                        break;
                    case NT:
                        raw_nt += usr_val;
                        if (raw_nt > raw_max)
                            raw_max = raw_nt;
                        break;
                    default:
                        throw new IllegalStateException("bug");
                    }
                }
                this.normalized_sf = raw_sf / raw_max;
                this.normalized_st = raw_st / raw_max;
                this.normalized_nt = raw_nt / raw_max;
                this.normalized_nf = raw_nf / raw_max;
            }
            else
                throw new IllegalArgumentException("Incomplete session");
        }
    }
}
