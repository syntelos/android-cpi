/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * Application state 
 * 
 * @see CPIDatabase
 * @see CPIInventoryRecord
 * @see CPIPostInventory
 * @see CPIPostPractice
 * @see CPIPostCompleted
 */
public enum CPIProcess {
    Practice,
    Inventory,
    Completed;
}
