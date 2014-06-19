/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

public enum CPIQuadrant
{
    ST, SF, NT, NF;

    
    public static final CPIQuadrant For(String value) {
        if (null == value)
            return null;
        else if (2 < value.length())
            value = value.trim();
        else if (2 == value.length()) {
            switch (value.charAt(0)) {
            case 'S':
            case 's':
                switch (value.charAt(1)) {
                case 'F':
                case 'f':
                    return CPIQuadrant.SF;
                case 'T':
                case 't':
                    return CPIQuadrant.ST;
                default:
                    break;
                }
                break;
            case 'N':
            case 'n':
                switch (value.charAt(1)) {
                case 'F':
                case 'f':
                    return CPIQuadrant.NF;
                case 'T':
                case 't':
                    return CPIQuadrant.NT;
                default:
                    break;
                }
                break;
            }
        }
        throw new IllegalArgumentException(value);
    }
    public static final CPIQuadrant For(boolean left, int id) {
        switch (id) {
        case 0:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 1:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 2:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 3:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 4:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 5:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 6:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 7:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 8:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 9:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.SF;
        case 10:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 11:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 12:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 13:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 14:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 15:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 16:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 17:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 18:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 19:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NT;
        case 20:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 21:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 22:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 23:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 24:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 25:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 26:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 27:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 28:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 29:
            if (left)
                return CPIQuadrant.ST;
            else
                return CPIQuadrant.NF;
        case 30:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 31:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 32:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 33:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 34:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 35:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 36:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 37:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 38:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 39:
            if (left)
                return CPIQuadrant.NF;
            else
                return CPIQuadrant.SF;
        case 40:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 41:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 42:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 43:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 44:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 45:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 46:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 47:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 48:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 49:
            if (left)
                return CPIQuadrant.SF;
            else
                return CPIQuadrant.NT;
        case 50:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 51:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 52:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 53:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 54:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 55:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 56:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 57:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 58:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        case 59:
            if (left)
                return CPIQuadrant.NT;
            else
                return CPIQuadrant.NF;
        default:
            throw new IllegalArgumentException(String.valueOf(id));
        }
    }
}
