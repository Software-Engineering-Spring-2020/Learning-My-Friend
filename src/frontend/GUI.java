package frontend;


/**
  * <h1>GUI/h1>
  *
  * GUI is responsable for the creation and update of the toolbars and surounding GUI.
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.09.2019
 */
import processing.core.PApplet;
import frontend.controlP5.*;
import frontend.ui.*;
import javax.management.*;

public class GUI{
  int guiHeight;
  int guiWidth;
  ControlP5 cp5;
  PApplet sketch;


  /**
   * [GUI constructor]
   * @param sketch [PApplet from sketch]
   */
  public GUI(PApplet sketch){
    this.sketch = sketch;
    cp5 = new ControlP5(sketch);
  }

  /**
   * [setup is called by setup on the sketch. It is responsable for defining the init GUI]
   */
  public void setup(){

  }

  /**
   * [update is called during during display when the window changes size. It is responsable for communicating scaling.]
   */
  public void update(){

  }

  /**
   * [display is called by draw() in the sketch and is responsable for updating th gui eveyframe]
   */
  public void display(){
    if (guiHeight != sketch.height || guiWidth!= sketch.width)
      update();
    guiHeight = sketch.height;
    guiWidth = sketch.width;
    //testing cp5 right now, not final

  }





  public Toolbar toolbarFactory(String type){
    if(type.equals("ObjSet"))
      return new ObjSetToolbar(cp5);
    if(type.equals("ObjCreate"))
      return new ObjCreateToolbar(cp5);
    if(type.equals("Workspace"))
      return new WorkspaceToolbar(cp5);
    //throw BadStringOperationException
    return null;
  }

}
