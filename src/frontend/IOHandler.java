package frontend;
/**
 * <h1>IOHandler</h1>
 * IOHandler takes in all IO actions. They are passed from PApplet PollyPaint and are parsed here.
 * For example when the mouse is on a GUI eliment you dont want to pan the canvas by accident.
 *
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 *
 * TODO Finish
 *
 * TODO Comment
 */



import processing.core.*;
import processing.event.*;
import backend.Window;
import processing.core.*;



public class IOHandler{
  PApplet sketch;
  Window win;
  //gui holds our state. The state is a structure of varibales that represent the state of the GUI.
  //For example the selected tool and color are stored in the state.
  GUI gui;





  //zoom sensitivity
  float mouseSense = 5;
  /**
   * [IOHandler constructor]
   * @param sketch [PollyPaint, needed for easy access to IO variables like the mouse's location]
   */
  public IOHandler(PApplet sketch,  Window win, GUI gui){
    this.sketch = sketch;
    this.gui = gui;
    this.win = win;
  }

/**
 * [offGUI returns a boolean representing if the mouse is on the GUI or not]
 * @return [returns true of the mouse is not on GUI elements]
 */
  public boolean offGUI(){
    //TODO
    return true;
  }



  public void clickedCanvas(){

  }

  public void mouseDragged() {
    if(offGUI())
      win.canvasPan((sketch.mouseX - sketch.pmouseX), (sketch.mouseY - sketch.pmouseY));
  }


  public void mouseWheel(MouseEvent event) {
    win.zoom(event.getCount()/mouseSense);
  }


}
