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

public class Handler{
  Window win;
  ArrayList<Integer> controllerIDs;

  public Handler(Window win){
    this.win = win;
    controllerIDs = new ArrayList<Integer>();
  }

  public void handle(ControlEvent event){
    if(event.isController()){


    }
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
