
public class T1 {

    private final static float IMG_WH = 4.0f;
    private final static float IMG_WH2 = (IMG_WH/2.0f);

    public static void main(String[] argv){
        float sf = 0.307f;
        float st = 0.519f;
        float nf = 0.634f;
        float nt = 1.0f;

        float s_st = IMG_WH2*st;
        float s_sf = IMG_WH2*sf;
        float s_nt = IMG_WH2*nt;
        float s_nf = IMG_WH2*nf;

        float x0 = (IMG_WH2 - s_st);
        float y0 = (IMG_WH2 - s_st);
        float x1 = (IMG_WH2 + s_sf);
        float y1 = (IMG_WH2 - s_sf);
        float x2 = (IMG_WH2 + s_nf);
        float y2 = (IMG_WH2 + s_nf);
        float x3 = (IMG_WH2 - s_nt);
        float y3 = (IMG_WH2 + s_nt);

        System.out.println("inside.reset();");

        System.out.printf ("inside.moveTo(%2.3ff,%2.3ff);%n",x0,y0);
        System.out.printf ("inside.lineTo(%2.3ff,%2.3ff);%n",x1,y1);
        System.out.printf ("inside.lineTo(%2.3ff,%2.3ff);%n",x2,y2);
        System.out.printf ("inside.lineTo(%2.3ff,%2.3ff);%n",x3,y3);
        System.out.println("inside.close();");

    }
}
