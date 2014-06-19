

import path.Op;
import path.Operand;

import com.johnpritchard.cpi.Inventory;

import java.io.*;

/**
 * 
 */
public final class chevron {

    private final static int X = 0;
    private final static int Y = 1;

    private final static float M = 2.0f;

    private final static float W = 4.0f;
    private final static float H = 6.0f;
    private final static float O = 0.0f;

    private final static float[][] OL = {
        new float[]{O,O},
        new float[]{O,H},
        new float[]{W,H},
        new float[]{W,O}
    };
    private final static float[][] CL = {
        new float[]{2.0f,1.0f},
        new float[]{1.0f,3.0f},
        new float[]{2.0f,5.0f},
        new float[]{3.0f,5.0f},
        new float[]{2.0f,3.0f},
        new float[]{3.0f,1.0f}
    };
    private final static float[][] CR = {
        new float[]{1.0f,1.0f},
        new float[]{2.0f,3.0f},
        new float[]{1.0f,5.0f},
        new float[]{2.0f,5.0f},
        new float[]{3.0f,3.0f},
        new float[]{2.0f,1.0f}
    };


    public final float[][] path, group;


    public chevron(Inventory inventory){
        super();
        int num_chevrons;
        switch(inventory){
        case L4:
        case R4:
            num_chevrons = 4;
            break;
        case L3:
        case R3:
            num_chevrons = 3;
            break;
        case L2:
        case R2:
            num_chevrons = 2;
            break;
        case L1:
        case R1:
            num_chevrons = 1;
            break;
        default:
            throw new IllegalArgumentException(inventory.name());
        }
        float[][] CV;
        switch(inventory){
        case L4:
        case L3:
        case L2:
        case L1:
            CV = CL;
            break;
        case R3:
        case R4:
        case R2:
        case R1:
            CV = CR;
            break;
        default:
            throw new IllegalArgumentException(inventory.name());
        }


        final float N = num_chevrons;
        /*
         * Exterior border outline
         */
        this.group = new float[][]{
            { (OL[0][X]*M),   (OL[0][Y]*M) },
            { (OL[1][X]*M),   (OL[1][Y]*M) },
            { (OL[2][X]*N*M), (OL[2][Y]*M) },
            { (OL[3][X]*N*M), (OL[3][Y]*M) }
        };

        /*
         * Interior L/R arrow shape
         */
        final int path_len = (num_chevrons*6);
        float[][] path = new float[path_len][2];

        for (int cc = 0, chevron = 0; chevron < num_chevrons; cc += 6, chevron += 1){

            float O = (chevron*W*M);

            path[cc+0][X] = O+CV[0][X]*M; path[cc+0][Y] = CV[0][Y]*M;
            path[cc+1][X] = O+CV[1][X]*M; path[cc+1][Y] = CV[1][Y]*M;
            path[cc+2][X] = O+CV[2][X]*M; path[cc+2][Y] = CV[2][Y]*M;
            path[cc+3][X] = O+CV[3][X]*M; path[cc+3][Y] = CV[3][Y]*M;
            path[cc+4][X] = O+CV[4][X]*M; path[cc+4][Y] = CV[4][Y]*M;
            path[cc+5][X] = O+CV[5][X]*M; path[cc+5][Y] = CV[5][Y]*M;
        }

        this.path = path;
    }

    public static void generate(Inventory inventory, File file)
        throws IOException
    {
        final chevron c = new chevron(inventory);

        final PrintStream out = new PrintStream(new FileOutputStream(file));
        try {
            out.println("/*");
            out.println(" * Copyright (C) 2014 John Pritchard.  All rights reserved.");
            out.println(" */");
            out.println("package com.johnpritchard.cpi;");
            out.println();
            out.println("import path.Op;");
            out.println("import path.Operand;");
            out.println();
            out.println("/**");
            out.println(" * ");
            out.println(" */");
            out.printf ("public class Button%s%n",inventory);
            out.println("    extends ButtonPlain");
            out.println("{");
            out.println();
            out.println();
            out.printf ("    public Button%s(){%n",inventory);
            out.println("        super(new Operand[]{");
            for (int cc = 0; cc < c.path.length; ){
                out.printf ("                new Operand(Op.MoveTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.path[cc][X],c.path[cc][Y]); cc++;
                out.println("                new Operand(Op.Close,new float[]{}),");
            }
            out.println("            }, new Operand[]{");

            out.printf ("                new Operand(Op.MoveTo,new float[]{%2.1ff,%2.1ff}),%n",c.group[0][X],c.group[0][Y]);
            out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.group[1][X],c.group[1][Y]);
            out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.group[2][X],c.group[2][Y]);
            out.printf ("                new Operand(Op.LineTo,new float[]{%2.1ff,%2.1ff}),%n",c.group[3][X],c.group[3][Y]);
            out.println("                new Operand(Op.Close,new float[]{}),");
            out.println("            });");
            out.println();
            out.println("    }");
            out.println();
            out.println("}");
        }
        finally {
            out.close();
        }
    }

    public static void main(String[] argv){
        try {
            for (Inventory inventory: Inventory.values()){

                File out = new File("src/com/johnpritchard/cpi/Button"+inventory+".java");

                generate(inventory,out);

                System.out.println(out.getPath());
            }
        }
        catch (Throwable t){
            t.printStackTrace();
            System.exit(1);
        }
    }
}
