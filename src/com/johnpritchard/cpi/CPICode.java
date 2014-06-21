/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * CPI statistics basic privacy code.  This scheme permits CPI
 * statistics to be encoded into a URL for the user's benefit, without
 * any other storage, and maintaining the basic "eyeballs" privacy.
 * The encoded data requires a copy of the URL to be decoded.
 * 
 * Numeric accuracy is preserved within a margin of error of 1e-4 over
 * 16 bit fixed point values.
 * 
 * @author John Pritchard
 */
public abstract class CPICode {

    public interface Data {

        public boolean hasCPICodeData();
    }

    /**
     * Fixed point bits.  A floating point value is multiplied by this
     * value to convert into a 16 bit fixed point integer
     * representation.  A 16 bit fixed point value is divided by this
     * value to convert into a floating point representation.
     */
    protected final static float FX = 0xffff;

    /**
     * A simple pad shared by all users.
     */
    private final static byte[] Pad = {
        'L', 'o', 'r', 'e', 'm', ' ', 'i', 'p', 's', 'u', 'm', ' ', 'd', 'o', 'l', 
        'o', 'r', ' ', 's', 'i', 't', ' ', 'a', 'm', 'e', 't', ',', ' ', 'c', 'o', 
        'n', 's', 'e', 'c', 't', 'e', 't', 'u', 'e', 'r', ' ', 'a', 'd', 'i', 'p', 
        'i', 's', 'c', 'i', 'n', 'g', ' ', 'e', 'l', 'i', 't', ',', ' ', 's', 'e', 
        'd', ' ', 'd', 'i', 'a', 'm', ' ', 'n', 'o', 'n', 'u', 'm', 'm', 'y', ' ', 
        'n', 'i', 'b', 'h', ' ', 'e', 'u', 'i', 's', 'm', 'o', 'd', ' ', 't', 'i', 
        'n', 'c', 'i', 'd', 'u', 'n', 't', ' ', 'u', 't', ' ', 'l', 'a', 'o', 'r', 
        'e', 'e', 't', ' ', 'd', 'o', 'l', 'o', 'r', 'e', ' ', 'm', 'a', 'g', 'n', 
        'a', ' ', 'a', 'l', 'i', 'q', 'u', 'a', 'm', ' ', 'e', 'r', 'a', 't', ' ', 
        'v', 'o', 'l', 'u', 't', 'p', 'a', 't', '.', ' ', 'U', 't', ' ', 'w', 'i', 
        's', 'i', ' ', 'e', 'n', 'i', 'm', ' ', 'a', 'd', ' ', 'm', 'i', 'n', 'i', 
        'm', ' ', 'v', 'e', 'n', 'i', 'a', 'm', ',', ' ', 'q', 'u', 'i', 's', ' ', 
        'n', 'o', 's', 't', 'r', 'u', 'd', ' ', 'e', 'x', 'e', 'r', 'c', 'i', ' ', 
        't', 'a', 't', 'i', 'o', 'n', ' ', 'u', 'l', 'l', 'a', 'm', 'c', 'o', 'r', 
        'p', 'e', 'r', ' ', 's', 'u', 's', 'c', 'i', 'p', 'i', 't', ' ', 'l', 'o', 
        'b', 'o', 'r', 't', 'i', 's', ' ', 'n', 'i', 's', 'l', ' ', 'u', 't', ' ', 
        'a', 'l', 'i', 'q', 'u', 'i', 'p', ' ', 'e', 'x', ' ', 'e', 'a', ' ', 'c', 
        'o', 'm', 'm', 'o', 'd', 'o', ' ', 'c', 'o', 'n', 's', 'e', 'q', 'u', 'a', 
        't', '.', ' ', 'D', 'u', 'i', 's', ' ', 'a', 'u', 't', 'e', 'm', ' ', 'v', 
        'e', 'l', ' ', 'e', 'u', 'm', ' ', 'i', 'r', 'i', 'u', 'r', 'e', ' ', 'd', 
        'o', 'l', 'o', 'r', ' ', 'i', 'n', ' ', 'h', 'e', 'n', 'd', 'r', 'e', 'r', 
        'i', 't', ' ', 'i', 'n', ' ', 'v', 'u', 'l', 'p', 'u', 't', 'a', 't', 'e', 
        ' ', 'v', 'e', 'l', 'i', 't', ' ', 'e', 's', 's', 'e', ' ', 'm', 'o', 'l', 
        'e', 's', 't', 'i', 'e', ' ', 'c', 'o', 'n', 's', 'e', 'q', 'u', 'a', 't', 
        ',', ' ', 'v', 'e', 'l', ' ', 'i', 'l', 'l', 'u', 'm', ' ', 'd', 'o', 'l', 
        'o', 'r', 'e', ' ', 'e', 'u', ' ', 'f', 'e', 'u', 'g', 'i', 'a', 't', ' ', 
        'n', 'u', 'l', 'l', 'a', ' ', 'f', 'a', 'c', 'i', 'l', 'i', 's', 'i', 's', 
        ' ', 'a', 't', ' ', 'v', 'e', 'r', 'o', ' ', 'e', 'r', 'o', 's', ' ', 'e', 
        't', ' ', 'a', 'c', 'c', 'u', 'm', 's', 'a', 'n', ' ', 'e', 't', ' ', 'i', 
        'u', 's', 't', 'o', ' ', 'o', 'd', 'i', 'o', ' ', 'd', 'i', 'g', 'n', 'i', 
        's', 's', 'i', 'm', ' ', 'q', 'u', 'i', ' ', 'b', 'l', 'a', 'n', 'd', 'i', 
        't', ' ', 'p', 'r', 'a', 'e', 's', 'e', 'n', 't', ' ', 'l', 'u', 'p', 't', 
        'a', 't', 'u', 'm', ' ', 'z', 'z', 'r', 'i', 'l', ' ', 'd', 'e', 'l', 'e', 
        'n', 'i', 't', ' ', 'a', 'u', 'g', 'u', 'e', ' ', 'd', 'u', 'i', 's', ' ', 
        'd', 'o', 'l', 'o', 'r', 'e', ' ', 't', 'e', ' ', 'f', 'e', 'u', 'g', 'a', 
        'i', 't', ' ', 'n', 'u', 'l', 'l', 'a', ' ', 'f', 'a', 'c', 'i', 'l', 'i', 
        's', 'i', '.', ' ', 'N', 'a', 'm', ' ', 'l', 'i', 'b', 'e', 'r', ' ', 't', 
        'e', 'm', 'p', 'o', 'r', ' ', 'c', 'u', 'm', ' ', 's', 'o', 'l', 'u', 't', 
        'a', ' ', 'n', 'o', 'b', 'i', 's', ' ', 'e', 'l', 'e', 'i', 'f', 'e', 'n', 
        'd', ' ', 'o', 'p', 't', 'i', 'o', 'n', ' ', 'c', 'o', 'n', 'g', 'u', 'e', 
        ' ', 'n', 'i', 'h', 'i', 'l', ' ', 'i', 'm', 'p', 'e', 'r', 'd', 'i', 'e', 
        't', ' ', 'd', 'o', 'm', 'i', 'n', 'g', ' ', 'i', 'd', ' ', 'q', 'u', 'o', 
        'd', ' ', 'm', 'a', 'z', 'i', 'm', ' ', 'p', 'l', 'a', 'c', 'e', 'r', 'a', 
        't', ' ', 'f', 'a', 'c', 'e', 'r', ' ', 'p', 'o', 's', 's', 'i', 'm', ' ', 
        'a', 's', 's', 'u', 'm', '.', ' ', 'T', 'y', 'p', 'i', ' ', 'n', 'o', 'n', 
        ' ', 'h', 'a', 'b', 'e', 'n', 't', ' ', 'c', 'l', 'a', 'r', 'i', 't', 'a', 
        't', 'e', 'm', ' ', 'i', 'n', 's', 'i', 't', 'a', 'm', ';', ' ', 'e', 's', 
        't', ' ', 'u', 's', 'u', 's', ' ', 'l', 'e', 'g', 'e', 'n', 't', 'i', 's', 
        ' ', 'i', 'n', ' ', 'i', 'i', 's', ' ', 'q', 'u', 'i', ' ', 'f', 'a', 'c', 
        'i', 't', ' ', 'e', 'o', 'r', 'u', 'm', ' ', 'c', 'l', 'a', 'r', 'i', 't', 
        'a', 't', 'e', 'm', '.', ' ', 'I', 'n', 'v', 'e', 's', 't', 'i', 'g', 'a', 
        't', 'i', 'o', 'n', 'e', 's', ' ', 'd', 'e', 'm', 'o', 'n', 's', 't', 'r', 
        'a', 'v', 'e', 'r', 'u', 'n', 't', ' ', 'l', 'e', 'c', 't', 'o', 'r', 'e', 
        's', ' ', 'l', 'e', 'g', 'e', 'r', 'e', ' ', 'm', 'e', ' ', 'l', 'i', 'u', 
        's', ' ', 'q', 'u', 'o', 'd', ' ', 'i', 'i', ' ', 'l', 'e', 'g', 'u', 'n', 
        't', ' ', 's', 'a', 'e', 'p', 'i', 'u', 's', '.', ' ', 'C', 'l', 'a', 'r', 
        'i', 't', 'a', 's', ' ', 'e', 's', 't', ' ', 'e', 't', 'i', 'a', 'm', ' ', 
        'p', 'r', 'o', 'c', 'e', 's', 's', 'u', 's', ' ', 'd', 'y', 'n', 'a', 'm', 
        'i', 'c', 'u', 's', ',', ' ', 'q', 'u', 'i', ' ', 's', 'e', 'q', 'u', 'i', 
        't', 'u', 'r', ' ', 'm', 'u', 't', 'a', 't', 'i', 'o', 'n', 'e', 'm', ' ', 
        'c', 'o', 'n', 's', 'u', 'e', 't', 'u', 'd', 'i', 'u', 'm', ' ', 'l', 'e', 
        'c', 't', 'o', 'r', 'u', 'm', '.', ' ', 'M', 'i', 'r', 'u', 'm', ' ', 'e', 
        's', 't', ' ', 'n', 'o', 't', 'a', 'r', 'e', ' ', 'q', 'u', 'a', 'm', ' ', 
        'l', 'i', 't', 't', 'e', 'r', 'a', ' ', 'g', 'o', 't', 'h', 'i', 'c', 'a', 
        ',', ' ', 'q', 'u', 'a', 'm', ' ', 'n', 'u', 'n', 'c', ' ', 'p', 'u', 't', 
        'a', 'm', 'u', 's', ' ', 'p', 'a', 'r', 'u', 'm', ' ', 'c', 'l', 'a', 'r', 
        'a', 'm', ',', ' ', 'a', 'n', 't', 'e', 'p', 'o', 's', 'u', 'e', 'r', 'i', 
        't', ' ', 'l', 'i', 't', 't', 'e', 'r', 'a', 'r', 'u', 'm', ' ', 'f', 'o', 
        'r', 'm', 'a', 's', ' ', 'h', 'u', 'm', 'a', 'n', 'i', 't', 'a', 't', 'i', 
        's', ' ', 'p', 'e', 'r', ' ', 's', 'e', 'a', 'c', 'u', 'l', 'a', ' ', 'q', 
        'u', 'a', 'r', 't', 'a', ' ', 'd', 'e', 'c', 'i', 'm', 'a', ' ', 'e', 't', 
        ' ', 'q', 'u', 'i', 'n', 't', 'a', ' ', 'd', 'e', 'c', 'i', 'm', 'a', '.', 
        ' ', 'E', 'o', 'd', 'e', 'm', ' ', 'm', 'o', 'd', 'o', ' ', 't', 'y', 'p', 
        'i', ',', ' ', 'q', 'u', 'i', ' ', 'n', 'u', 'n', 'c', ' ', 'n', 'o', 'b', 
        'i', 's', ' ', 'v', 'i', 'd', 'e', 'n', 't', 'u', 'r', ' ', 'p', 'a', 'r', 
        'u', 'm', ' ', 'c', 'l', 'a', 'r', 'i', ',', ' ', 'f', 'i', 'a', 'n', 't', 
        ' ', 's', 'o', 'l', 'l', 'e', 'm', 'n', 'e', 's', ' ', 'i', 'n', ' ', 'f', 
        'u', 't', 'u', 'r', 'u', 'm', '.'
    };

    final static byte[] Pad(byte[] text){
        if (null == text)
            return null;
        else {
            int len = text.length;
            if (0 == len)
                return null;
            else {
                byte[] re = new byte[len];
                for (int cc = 0; cc < len; cc++){
                    re[cc] = (byte)(text[cc] ^ Pad[cc]);
                }
                return re;
            }
        }
    }


    public final static class Encode 
        extends CPICode
    {

        final static byte[] Bits(float nt, float nf, float st, float sf){

            int b_nt = (int)(nt*FX);
            int b_nf = (int)(nf*FX);
            int b_st = (int)(st*FX);
            int b_sf = (int)(sf*FX);
            byte[] re = new byte[8];
            re[0] = (byte)((b_nt>>8) & 0xff);
            re[1] = (byte)(b_nt & 0xff);
            re[2] = (byte)((b_nf>>8) & 0xff);
            re[3] = (byte)(b_nf & 0xff);
            re[4] = (byte)((b_st>>8) & 0xff);
            re[5] = (byte)(b_st & 0xff);
            re[6] = (byte)((b_sf>>8) & 0xff);
            re[7] = (byte)(b_sf & 0xff);
            return re;
        }
        final static String Encode(float nt, float nf, float st, float sf){

            byte[] bits = Pad(Bits(nt,nf,st,sf));
            if (null == bits)
                throw new IllegalArgumentException("Bits null");
            else if (8 != bits.length)
                throw new IllegalArgumentException("Bits "+String.valueOf(bits.length));
            else 
                return Hex.encode(bits);
        }

        public final float nt, nf, st, sf;
        public final String code;

        public Encode(CPIInventoryRecord data){
            super();
            if (null != data && data.hasCPICodeData()){

                this.nt = data.nt;
                this.nf = data.nf;
                this.st = data.st;
                this.sf = data.sf;

                this.code = Encode(this.nt,this.nf,this.st,this.sf);
            }
            else
                throw new IllegalArgumentException();
        }
        public Encode(float nt, float nf, float st, float sf){
            super();
            this.nt = nt;
            this.nf = nf;
            this.st = st;
            this.sf = sf;

            this.code = Encode(nt,nf,st,sf);
        }


        public String getCPICode(){
            return this.code;
        }
        public String toStringNt(){
            return Format(this.nt);
        }
        public String toStringNf(){
            return Format(this.nf);
        }
        public String toStringSt(){
            return Format(this.st);
        }
        public String toStringSf(){
            return Format(this.sf);
        }
        public String toString(){
            return String.format("%9g %9g %9g %9g",this.nt,this.nf,this.st,this.sf);
        }
    }

    public final static class Decode
        extends CPICode
    {
        final static float[] Bits(byte[] bits){
            if (8 == bits.length){

                float b_nt = (((bits[0] & 0xff)<<8)|(bits[1] & 0xff));
                float b_nf = (((bits[2] & 0xff)<<8)|(bits[3] & 0xff));
                float b_st = (((bits[4] & 0xff)<<8)|(bits[5] & 0xff));
                float b_sf = (((bits[6] & 0xff)<<8)|(bits[7] & 0xff));

                return new float[]{
                    (b_nt/FX),
                    (b_nf/FX),
                    (b_st/FX),
                    (b_sf/FX)
                };
            }
            else
                throw new IllegalArgumentException();
        }
        final static float[] Decode(String string){
            if (null == string)
                throw new IllegalArgumentException("String null");
            else if (16 != string.length())
                throw new IllegalArgumentException("String "+String.valueOf(string.length()));
            else {
                byte[] bits = Hex.decode(string);
                if (null == bits)
                    throw new IllegalArgumentException("Bits null");
                else if (8 != bits.length)
                    throw new IllegalArgumentException("Bits "+String.valueOf(bits.length));
                else 
                    return Bits(Pad(bits));
            }
        }

        public final float nt, nf, st, sf;
        public final String code;

        public Decode(String code){
            super();
            this.code = Clean(code);
            {
                final float[] value = Decode(code);

                this.nt = value[0];
                this.nf = value[1];
                this.st = value[2];
                this.sf = value[3];
            }
        }


        public String getCPICode(){
            return this.code;
        }
        public String toStringNt(){
            return Format(this.nt);
        }
        public String toStringNf(){
            return Format(this.nf);
        }
        public String toStringSt(){
            return Format(this.st);
        }
        public String toStringSf(){
            return Format(this.sf);
        }
        public String toString(){
            return String.format("%9g %9g %9g %9g",this.nt,this.nf,this.st,this.sf);
        }
    }


    public CPICode(){
        super();
    }


    public abstract String getCPICode();

    public abstract String toStringNt();

    public abstract String toStringNf();

    public abstract String toStringSt();

    public abstract String toStringSf();

    public abstract String toString();


    /**
     * Test
     */
    public static void main(String[] argv){
        int N = 99;

        switch (argv.length){
        case 1:
            try {
                N = Integer.parseInt(argv[0]);
            }
            catch (Exception help){
                System.err.println("Usage ");
                System.err.println("    CPICode               -- Random test number "+N);
                System.err.println("    CPICode  N            -- Random test number N");
                System.err.println("    CPICode  -d code      -- Decode");
                System.err.println("    CPICode  NT NF ST SF  -- Encode ");
                System.exit(1);
            }

        case 0:

            Random prng = new Random();

            for (int T = 0; T < N; T++){

                Encode enc = new Encode(prng.nextFloat(),prng.nextFloat(),prng.nextFloat(),prng.nextFloat());

                Decode dec = new Decode(enc.code);
            
                if (ENE(dec.nt,enc.nt) ||
                    ENE(dec.nf,enc.nf) ||
                    ENE(dec.st,enc.st) ||
                    ENE(dec.sf,enc.sf))
                {
                    System.err.println();
                    System.err.println("Error");
                    System.out.printf("%s %s %n",enc.code,enc);
                    System.out.printf("%s %s %n",enc.code,dec);
                    System.exit(1);
                }
                else { 
                    System.out.printf("%s %s %n",enc.code,dec);
                }
            }
            System.exit(0);
            break;
        case 2:
            Decode dec = new Decode(argv[1]);
            System.out.printf("%s %s %n",dec.code,dec);
            System.exit(0);
            break;
        case 4:
            Encode enc = new Encode(Float.parseFloat(argv[0]),Float.parseFloat(argv[1]),Float.parseFloat(argv[2]),Float.parseFloat(argv[3]));
            System.out.printf("%s %s %n",enc.code,enc);
            System.exit(0);
            break;
        }
    }


    public final static String Format(float f) {
        String string = String.valueOf(f);
        if (5 < string.length())
            return string.substring(0, 5);
        return string;
    }
    final static String Clean(String string){
        if (null == string || 1 > string.length())
            return null;
        else {

            while ('/' == string.charAt(0)){

                string = string.substring(1);

                if (1 > string.length())
                    return null;
            }
            while ('/' == string.charAt(string.length()-1)){

                string = string.substring(0,(string.length()-1));

                if (1 > string.length())
                    return null;
            }
            return string;
        }
    }
    public final static float EPS = 1e-4f;
    public final static boolean EEQ(float a, float b){

        return (Math.abs(a-b) < EPS);
    }
    public final static boolean ENE(float a, float b){

        return (Math.abs(a-b) > EPS);
    }
}
