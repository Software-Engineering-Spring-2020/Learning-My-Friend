package frontend.ui;

import frontend.controlP5.*;
import frontend.handler.Handler;
import processing.core.*;
import java.util.ArrayList;
import java.lang.Math;

public class ObjCreateToolbar extends Toolbar{
ScalableObj g;

  public ObjCreateToolbar(ControlP5 cp5, Handler h, PApplet sketch){
    super(cp5, h, sketch);


    //g = ScalableObjFactory('g', "ObjCreateToolbar", 0, (float).05, (float).05, (float).9);
    //controllers.add(g);


    g = ScalableObjFactory('b', "Rect", 0, (float).10, (float).04, (float).1);
    g = ScalableObjFactory('b', "Ecl", 0, (float).20, (float).04, (float).1);

    }





}
