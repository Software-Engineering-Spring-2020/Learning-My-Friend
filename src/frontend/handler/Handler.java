package frontend.handler;

/**
  * <h1>Handler/h1>
  *
  * Handler is responsable handling incomming ControlEvents from ControlP5
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
 */

import backend.*;
import frontend.controlP5.*;
import java.util.ArrayList;
import processing.core.*;
import processing.event.*;


public class Handler{
  Window win;
  ArrayList<Integer> controllerIDs;
  PApplet sketch;



  char tool;
  int fillColor[] = {0, 0, 0};
  int boarderColor[] = {0, 0, 0};


  public Handler(Window win, PApplet sketch){
    this.win = win;
    controllerIDs = new ArrayList<Integer>();
    this.sketch = sketch;
  }

  public void handleEvent(ControlEvent event){
    if(event.isController()){


    }
  }


  public void clickedCanvas(){
    if(tool != null)
      win.createShape(sketch.mouseX, sketch.mouseY, tool);
  }

  /**
   * [regController adds the controller to the handler's list, it also assignes that contoller an ID]
   * @param c [the controller to be registered]
   */
  public void regController(ControllerInterface c){
    c.setId(controllerIDs.size());
  }


  public void createShape(){


  }


}
