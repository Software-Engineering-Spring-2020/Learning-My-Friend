package frontend.ui;

/**
  * <h1>ScalableObj/h1>
  *
  * ScalableObj holds ControlP5 objects and allows them to update their postion.
  * Rather then telling where you want an object in pixles it places an object based on percentages.
  * For example setting the size of x to .5 would take up .5 of the screens space or 1/20th
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
 */

import processing.core.PApplet;
import frontend.controlP5.*;
import java.lang.Math;

class ScalableObj{
  PApplet sketch;
  // relitive size nad position
  float sizeRX, sizeRY, posRX, posRY;

  //Some obj are Controller and some are Contoller groups, unfortuently both have `postion()` and `size()`
  public Controller con = null;
  public ControllerGroup cg = null;
  //this is added to have the ability to change the group background color
  public ControlGroup cgb = null;

  public ScalableObj(PApplet sketch, Controller con){
    this.sketch = sketch;
    this.con = con;
  }
  public ScalableObj(PApplet sketch, ControllerGroup cg){
    this.sketch = sketch;
    this.cg = cg;
  }
  public ScalableObj(PApplet sketch, ControlGroup cgb){
    this.sketch = sketch;
    this.cgb = cgb;
  }

  public String getName(){
    if(cg != null){
      return cg.getName();
    }
    if(con!= null){
      return con.getName();
    }
    if(cgb!= null){
      return cgb.getName();
    }
    else return null;
  }

  public void setPos(float x, float y){
    posRX = x;
    posRY = y;
    update();
  }

  public void setSize(float x, float y){
    sizeRX = x;
    sizeRY = y;
    update();
  }

  public void update(){
    if(cg != null){
      cg.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
      cg.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
    }
    if(con!= null){
      con.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
      con.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
    }
    if(cgb!= null){
      //System.out.println("PosRX: " + posRX);
      cgb.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
      cgb.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
      cgb.setBackgroundHeight((int)Math.round(sketch.height*sizeRY));
    }
  }

}
