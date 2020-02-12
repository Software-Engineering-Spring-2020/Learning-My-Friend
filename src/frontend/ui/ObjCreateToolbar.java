package frontend.ui;

import frontend.controlP5.*;
import frontend.handler.Handler;
import processing.core.*;
import java.util.ArrayList;
import java.lang.Math;

public class ObjCreateToolbar extends Toolbar{
  Group g;

  public ObjCreateToolbar(ControlP5 cp5, Handler h, PApplet sketch){
    super(cp5, h, sketch);
    g = cp5.addGroup("ObjCreateToolbar")
                .setPosition(0, sketch.height/10)
                .setSize((int)Math.round(sketch.width/20), (int)Math.round(sketch.height*0.9))
                .setBackgroundHeight((int)Math.round(sketch.height*0.8))
                .setBackgroundColor(100)
                .disableCollapse()
                .hideBar()
                ;
    }

  public void update(){

  }



}
