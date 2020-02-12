package frontend.ui;

import frontend.controlP5.*;
import frontend.handler.Handler;
import processing.core.*;
import java.util.ArrayList;
import java.lang.Math;

public class ObjCreateToolbar extends Toolbar{
  ControlGroup g;

  public ObjCreateToolbar(ControlP5 cp5, Handler h, PApplet sketch){
    super(cp5, h, sketch);
    g = cp5.addGroup("ObjCreateToolbar").hideBar().setBackgroundColor(100);
    controllers.add(new ScalableObj(sketch, g));
    controllers.get(controllers.size()-1).setPos((float)0, (float)0.05);
    controllers.get(controllers.size()-1).setSize((float)0.05, (float)0.9);

    }





}
