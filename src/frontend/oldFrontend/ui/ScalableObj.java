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
  public float sizeX, sizeY, posX, posY;
  // relitive size nad position
  float sizeRX, sizeRY, posRX, posRY;

  //Some obj are Controller and some are Contoller groups, unfortuently both have `postion()` and `size()`
  public Controller con = null;
  public ControllerGroup cg = null;
  //this is added to have the ability to change the group background color
  public ControlGroup cgb = null;
  public Group parrentGroup;
  public ScalableObj parrent = null;

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


  public ScalableObj getParrent(){
    return parrent;
  }

  public void setParrent(ScalableObj parrentGroup){
    this.parrent = parrent;
  }

  public Group getParrentGroup(){
    return parrentGroup;
  }

  public void setParrentGroup(Group parrent){
    this.parrentGroup = parrentGroup;
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
    if(parrent == null || parrentGroup == null){
      System.out.println("Object: " + getName() + " Width: " + sizeRX + " Height: " + sizeRY);
      if(cg != null ){
        cg.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
        cg.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
      }
      else if(con!= null){
        con.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
        con.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
      }
      else if(cgb!= null){
        System.out.println("Object: " + getName() + " Width: " + (int)Math.round(sketch.width*sizeRX) +
        " Height: " + (int)Math.round(sketch.height*sizeRY));
        sizeX = (int)Math.round(sketch.width*sizeRX);
        sizeY = (int)Math.round(sketch.height*sizeRY);
        posX = (int)Math.round(sketch.width*posRX);
        posY = (int)Math.round(sketch.height*posRY);



        cgb.setSize((int)Math.round(sketch.width*sizeRX), (int)Math.round(sketch.height*sizeRY));
        cgb.setPosition((int)Math.round(sketch.width*posRX), (int)Math.round(sketch.height*posRY));
        cgb.setBackgroundHeight((int)Math.round(sketch.height*sizeRY));
        System.out.println("Object: " + getName() + " Registered -    Width: " + cgb.getWidth() +
        " Height: " + cgb.getHeight());

      }
    }
  else{
    //System.out.println(parrent.sizeX + " " + parrent.sizeY);
    if(cg != null){
      cg.setSize((int)Math.round(parrent.sizeX*sizeRX), (int)Math.round(parrent.sizeY*sizeRY));
      cg.setPosition((int)Math.round(parrent.sizeX*posRX), (int)Math.round(parrent.sizeY*posRY));
    }
    if(con!= null){
      con.setSize((int)Math.round(parrent.sizeX*sizeRX), (int)Math.round(parrent.sizeY*sizeRY));
      con.setPosition((int)Math.round(parrent.sizeX*posRX), (int)Math.round(parrent.sizeY*posRY));
    }
    if(cgb!= null){
      //System.out.println("PosRX: " + posRX);
      cgb.setSize((int)Math.round(parrent.sizeX*sizeRX), (int)Math.round(parrent.sizeY*sizeRY));
      cgb.setPosition((int)Math.round(parrent.sizeX*posRX), (int)Math.round(parrent.sizeY*posRY));
      cgb.setBackgroundHeight((int)Math.round(parrent.sizeX*sizeRY));
    }


  }



  }

}
