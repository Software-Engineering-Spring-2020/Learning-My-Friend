package frontend.ui;
import frontend.controlP5.*;
import frontend.handler.Handler;
import processing.core.*;
import java.util.ArrayList;

/**
  * <h1>Toolbar/h1>
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
 */

import frontend.controlP5.*;


public abstract class Toolbar{
  ControlP5 cp5;
  //Handler is present so the Toolbar can register it's controllers
  Handler h;
  PApplet sketch;
  ArrayList<ScalableObj> controllers;

  public Toolbar(ControlP5 cp5, Handler h, PApplet sketch){
    this.cp5 = cp5;
    this.h = h;
    this.sketch = sketch;
    controllers = new ArrayList<ScalableObj>();
  }


  public void update(){
    for(ScalableObj c : controllers)
      c.update();
  }



    public ScalableObj ScalableObjFactory(char c, String name, float posX, float posY, float sizeX, float sizeY){
      ScalableObj so;
      //add group
      if(c == 'g'){
        ControlGroup g = cp5.addGroup(name)
        .hideBar()
        .setBackgroundColor(100);
        so = new ScalableObj(sketch, g);
        so.setPos(posX, posY);
        so.setSize(sizeX, sizeY);
      }
      //add buttonbar
      else if(c == 'b'){
        Controller g = cp5.addButton(name);
        so = new ScalableObj(sketch, g);
        so.setPos(posX, posY);
        so.setSize(sizeX, sizeY);
      }
      else {
        so = new ScalableObj(sketch, cp5.addGroup(name).hideBar().setBackgroundColor(100));
        so.setPos(posX, posY);
        so.setSize(sizeX, posY);
      }
      controllers.add(so);
      return so;
    }






    public ScalableObj ButtonBarFactory(String name, String[] option, float posX, float posY, float sizeX, float sizeY){
      ScalableObj so;
      ButtonBar g = cp5.addButtonBar(name);
      g.addItems(option);
      so = new ScalableObj(sketch, g);
      so.setPos(posX, posY);
      so.setSize(sizeX, sizeY);
      controllers.add(so);
      return so;
    }
}
