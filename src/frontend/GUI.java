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

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import processing.core.*;

public class GUI {
  PApplet sketch;
  Window win;
  // ControlP5 is a GUI lib
  ControlP5 cp5;
  // list of toolbars
  LinkedList<FToolbar> tbList;

  // contains the last size for the sketch. If these are diffrent then the curent
  // sketch size the gui will call the toolbars to resize.
  int lastAppletWidth, lastAppletHeight;
  /**
   * Start of STATE Deleration The state is a structure of varibales that
   * represent the state of the GUI. For example the selected tool and color are
   * stored in the state.
   */
  // selected tool
  public char tool;
  public int fillColor[] = { 0, 0, 0, 0 };
  public int boarderColor[] = { 0, 0, 0 };

  /**
   * End of STATE Deleration
   */

  /**
   * [GUI constructor]
   * 
   * @param sketch [PApplet sketch]
   * @param win    [Windows from selected backend]
   */
  public GUI(PApplet sketch, Window win) {
    this.sketch = sketch;
    this.win = win;
    cp5 = new ControlP5(sketch);
    tbList = new LinkedList<FToolbar>();

    lastAppletWidth = sketch.width;
    lastAppletHeight = sketch.height;

    setup();
  }

  /**
   * [getTool gets the selected tool]
   * 
   * @return [the selected tool]
   */
  public char getTool() {
    return tool;
  }

  /**
   * [setTool sets the selected tool]
   * 
   * @param tool [the selected tool]
   */
  public void setTool(char tool) {
    this.tool = tool;
  }

  /**
   * [save opens file exploreor and passes save func]
   */
  public void save() {
    File f = new File("drawing.polly");
    sketch.selectOutput("Select a file to save to:", "saveFileSelected", f, this);
  }

  public void saveFileSelected(File selection) {
    try {
      win.save(selection.getAbsolutePath());
    } catch (IOException e) {
      System.out.println(e);
      e.printStackTrace();
    }
   }

   public void open() {
     File f = new File("drawing.polly");
    sketch.selectInput("Select a file to open:", "openFileSelected", f, this);
  }

  public void openFileSelected(File selection) {
    try {
      win.open(selection.getAbsolutePath());
    } catch (IOException | ClassNotFoundException e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }

   public void exportImage() {

   }

   public void importImage() {

   }

   /**
    * [setFill sets the fill color]
    * @param i [color from 1 to 255]
    * @param c [color r, b, g]
    * also changes boarder as client did not ask for this feture and it takes up space in the gui
    */
   public void setFill(int i, char c){
    if(c == 'r')
      fillColor[0] = i;
    if(c == 'g')
      fillColor[1] = i;
    if(c == 'b')
      fillColor[2] = i;
    if(c == 'a')
      fillColor[3] = i;
    //win.setFillColor(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
    win.setFillColor(fillColor[0], fillColor[1], fillColor[2], 255);

   }


   /**
    * [setFill sets the boarder color]
    * @param i [color from 1 to 255]
    * @param c [color r, b, g]
    * currently unused as client did not ask for boarder settings and it takes up space in the gui, is controlled by setFill
    */
   public void setBoarder(int i, char c){
    if(c == 'r')
      boarderColor[0] = i;
    if(c == 'g')
      boarderColor[1] = i;
    if(c == 'b')
      boarderColor[2] = i;
    win.setBoarderColor(boarderColor[0], boarderColor[1], boarderColor[2]);
   }

   public void toggleGrid(){
    win.toggleGrid();
    System.out.println("Grid Toggled");
   }


   private void setup(){
     setupObjectCreationToolbar();
     setupObjectSettingsToolbar();
     setUpWorkspaceToolbar();
     resizeAll();
   }

   private void setupObjectCreationToolbar(){
     FToolbar ft = toolbarFactory("Obj Create", (float).05, (float).8, (float).0, (float).1);
     ft.addFController(new RectButton(cp5, ft, this));
     ft.addFController(new ElipButton(cp5, ft, this));
     ft.addFController(new PenButton(cp5, ft, this));
   }


   private void setupObjectSettingsToolbar(){
    FToolbar ft = toolbarFactory("Obj Set", (float).2, (float).8, (float).8, (float).1);
    ft.addFController(new RFillSlider(cp5, ft, this));
    ft.addFController(new FColorPicker(cp5, ft, this));
   }

   private void setUpWorkspaceToolbar(){
    FToolbar ft = toolbarFactory("Workspace", (float).8, (float).05, (float).1, (float).0);
    ft.addFController(new SaveButton(cp5, ft, this));
    ft.addFController(new GridButton(cp5, ft, this));

   }


  private FToolbar toolbarFactory(String name, float sizeX, float sizeY, float posX, float posY){
    FToolbar ret = new FToolbar(sketch, cp5, name);
    tbList.add(ret);
    ret.setSize(sizeX, sizeY);
    ret.setPos(posX, posY);
    return ret;
  }



   /**
    * [display checks to see if the applet size has changed. If it has it triggers a resize.]
    */
   public void display(){
     //TODO
     if(lastAppletWidth != sketch.width || lastAppletHeight != sketch.height){
      resizeAll();
      lastAppletWidth = sketch.width;
      lastAppletHeight = sketch.height;
    }
     //Check for window resize and if so update all toolbars
   }

   /**
    * [resizeAll resizes all the gui elements]
    */
   public void resizeAll(){
     for(FToolbar tb : tbList)
      tb.update();

   }

}
