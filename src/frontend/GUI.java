package frontend;
/**
  * <h1>GUI/h1>
  *
  * GUI is responsable for the creation and update of the toolbars and surounding GUI.
  * GUI also holds the toolbars, UIgroups, and UIControllers. It holds state infromation
  * like what tool is selected.
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
  *
  * TODO
  * Comment eveything
  *
  *
  * TODO Make toolbars
 */

import backend.Window;
import frontend.controlP5.*;
import frontend.fcontrollers.*;
import java.util.LinkedList;
import processing.core.*;

 public class GUI{
   PApplet sketch;
   Window win;
   //ControlP5 is a GUI lib
   ControlP5 cp5;
   //list of toolbars
   LinkedList<FToolbar> tbList;

   /**
    * Start of STATE Deleration
    * The state is a structure of varibales that represent the state of the GUI.
    * For example the selected tool and color are stored in the state.
    */
   //selected tool
   char tool;
   int fillColor[] = {0, 0, 0};
   int boarderColor[] = {0, 0, 0};

  /**
   * End of STATE Deleration
   */



/**
 * [GUI description]
 * @param sketch [description]
 * @param win    [description]
 */
   public GUI(PApplet sketch, Window win){
     this.sketch = sketch;
     this.win = win;
     cp5 = new ControlP5(sketch);
   }


   public void display(){
     //TODO
     //Check for window resize and if so update all toolbars
   }


}
