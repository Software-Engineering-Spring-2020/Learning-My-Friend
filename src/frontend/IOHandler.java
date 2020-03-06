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
import java.awt.event.KeyEvent;



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
  public boolean onCanvas(){
    return win.withinCanvas(sketch.mouseX, sketch.mouseY);
  }

  public void mouseClicked(){

      if(gui.getTool() == 's')
        win.select(sketch.mouseX, sketch.mouseY);

  }

  public void mousePressed(){
    if(gui.getTool() == 'h')
      win.addAnimation(Window.AnimationOption.TRANSLATE, sketch.mouseX, sketch.mouseY);
    action();
  }

  public void mouseReleased(){
    action();
    //reequip the select tool only if on canvas
    if(onCanvas())
      gui.setTool('s');
  }

  public void mouseDragged() {
    //if pan tool is selected
    if(gui.getTool() == 'p' && onCanvas())
      win.freeDraw(sketch.mouseX, sketch.mouseY);
    else //if(onCanvas())
      win.pan(sketch.mouseX, sketch.mouseY, sketch.pmouseX, sketch.pmouseY);
  }


  public void mouseWheel(MouseEvent event) {
    //had to change scroll to on canvas only to protect gui elements
    //if(onCanvas())
    //  win.zoom(event.getCount()/mouseSense);
  }




  private void action(){
    if(onCanvas()){
        //System.out.println("this is being called with tool " + gui.getTool());
      if(gui.getTool() == 'p')
        win.createFreeForm();

      if(gui.getTool() == 'r')
        win.createRect(sketch.mouseX, sketch.mouseY);

      if(gui.getTool() == 'e')
        win.createEllipse(sketch.mouseX, sketch.mouseY);

      if(gui.getTool() == 't')
        win.createInteractiveTextBox(sketch.mouseX, sketch.mouseY, gui.getFont(), 20, gui.getTextMode());

      if(gui.getTool() == 'l')
        win.createLine(sketch.mouseX, sketch.mouseY);


   }
   else
    win.selectSlide(sketch.mouseX, sketch.mouseY);
  }

}
