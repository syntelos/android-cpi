/*
 * Copyright (C) 2014 John Pritchard.  All rights reserved.
 */
package com.johnpritchard.cpi;

/**
 * 
 */
public class CPIPageSharing
    extends ViewPage2D
{

    public final static CPIPageSharing Instance = new CPIPageSharing();

    private final static int GMAIL      = 0;
    private final static int TWITTER    = 1;
    private final static int FACEBOOK   = 2;
    private final static int GOOGLEPLUS = 3;


    public CPIPageSharing(){
        super(new ViewPage2DComponent[]{
                new CPIButtonShareGM(),
                new CPIButtonShareTW(),
                new CPIButtonShareFB(),
                new CPIButtonShareGP()
            });
    }

    @Override
    protected void init(){

        group_vertical();
    }
    @Override
    protected int first(){

        return GMAIL;
    }
    @Override
    public String name(){
        return Page.sharing.name();
    }
    @Override
    public Page value(){
        return Page.sharing;
    }
    @Override
    public void input(InputScript in){

        switch(in.type()){

        case Enter:

            switch(enter()){

            case GMAIL:{

                view.script(Page.start);
                break;
            }
            case TWITTER:{

                view.script(Page.start);
                break;
            }
            case FACEBOOK:{

                view.script(Page.start);
                break;
            }
            case GOOGLEPLUS:{

                view.script(Page.start);
                break;
            }
            default:
                break;
            }
            return;

        case Back:
        case Up:
        case Down:
            view.script(Page.start);
            return;

        default:
            return;
        }
    }
}
