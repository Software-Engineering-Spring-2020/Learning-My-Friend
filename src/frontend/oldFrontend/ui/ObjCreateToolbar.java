package frontend.ui;

import frontend.controlP5.*;
import frontend.handler.Handler;
import processing.core.*;
import java.util.ArrayList;
import java.lang.Math;

public class ObjCreateToolbar extends Toolbar{


  public ObjCreateToolbar(ControlP5 cp5, Handler h, PApplet sketch){
    super(cp5, h, sketch);


    parrent = ScalableGroupFactory("Object Create", (float)0, (float).1, (float).05, (float).8);
    g = (Group)cp5.getGroup("Object Create");
    //controllers.add(g);

    ScalableObjFactory('b', "Sel", 0, (float)0, (float)1, (float).8);
    ScalableObjFactory('b', "Pen", 0, (float).1, (float).9, (float).8);
    ScalableObjFactory('b', "Rect", 0, (float).2, (float).9, (float).8);
    ScalableObjFactory('b', "Tri", 0, (float).3, (float).9, (float).8);
    ScalableObjFactory('b', "Ecl", 0, (float).4, (float).9, (float).8);
    ScalableObjFactory('b', "Star", 0, (float).5, (float).9, (float).8);
    ScalableObjFactory('b', "Curve", 0, (float).6, (float).9, (float).8);
    ScalableObjFactory('b', "Line", 0, (float).7, (float).9, (float).8);
    ScalableObjFactory('b', "Text", 0, (float).8, (float).9, (float).8);




    }





}
