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


  FSlider rSlider, gSlider, bSlider;

  String currentString;

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



  public void group(){
    win.group();
  }


  public void ungroup(){
    win.unGroup();
  }


  public void setRotate(float i){
    win.rotate(i);
  }

  public void setSize(float i){
    win.resizeSelected(i);
  }


  public void toggleComments(){
    win.toggleComments();
  }

  public String getCurrentString(){
    return currentString;
  }

  public void setCurrentString(String currentString){
    this.currentString = currentString;
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
    //System.out.println(tool);
    this.tool = tool;
  }


  public void trash(){
    win.deleteSelected();
  }

  /**
   * [save opens file exploreor and passes save func]
   */
  public void save() {
    File f = new File("drawing.polly");
    sketch.selectOutput("Select a file to save to save as:", "saveFileSelected", f, this);
  }

  public void saveFileSelected(File selection) {
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      try {
        win.save(selection.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
 }

  /**
   * [export opens file exploreor and passes export func]
   */
  public void export() {
    File f = new File("drawing.png");
    sketch.selectOutput("Select a file to save to export to", "saveFileSelected", f, this);
  }

  public void exportFileSelected(File selection) {
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      win.exportAs(selection.getAbsolutePath(), ".png");
    }
 }

   public void open() {
    File f = new File("drawing.polly");
    sketch.selectInput("Select a file to open:", "openFileSelected", f, this);
  }


  public void fileImport() {
    File f = new File("drawing.polly");
    sketch.selectInput("Select a file to open:", "openFileSelected", f, this);

  }

  public void openFileSelected(File selection) {
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      try {
        win.open(selection.getAbsolutePath());
      } catch (IOException | ClassNotFoundException e) {
        System.out.println(e);
        e.printStackTrace();
      }
    }
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
     ft.addFController(new SelecButton(cp5, ft, this));
     ft.addFController(new RectButton(cp5, ft, this));
     ft.addFController(new ElipButton(cp5, ft, this));
     ft.addFController(new PenButton(cp5, ft, this));
     ft.addFController(new TextButton(cp5, ft, this));
     ft.addFController(new CommentButton(cp5, ft, this));

   }


   private void setupObjectSettingsToolbar(){
    FToolbar ft = toolbarFactory("Obj Set", (float).2, (float).8, (float).8, (float).1);
    rSlider = new RFillSlider(cp5, ft, this);
    bSlider = new GFillSlider(cp5, ft, this);
    gSlider = new BFillSlider(cp5, ft, this);

    rSlider.slider.setColorBackground(sketch.color(100,0,0));
    rSlider.slider.setColorActive(sketch.color(255,0,0));
    rSlider.slider.setColorForeground(sketch.color(255,0,0));

    gSlider.slider.setColorBackground(sketch.color(0,100,0));
    gSlider.slider.setColorActive(sketch.color(0,255,0));
    gSlider.slider.setColorForeground(sketch.color(0,255,0));

    bSlider.slider.setColorBackground(sketch.color(0,0,100));
    bSlider.slider.setColorActive(sketch.color(0,0,255));
    bSlider.slider.setColorForeground(sketch.color(0,0,255));


    ft.addFController(rSlider);
    ft.addFController(gSlider);
    ft.addFController(bSlider);

    ft.addFController(new Textbox(cp5, ft, this));

    ft.addFController(new GroupButton(cp5, ft, this));
    ft.addFController(new UngroupButton(cp5, ft, this));
    
    ft.addFController(new SizeSlider(cp5, ft, this));
    ft.addFController(new RotateSlider(cp5, ft, this));

    ft.addFController(new TrashButton(cp5, ft, this));

    //ft.addFController(new FColorPicker(cp5, ft, this));
   }

   private void setUpWorkspaceToolbar(){
    FToolbar ft = toolbarFactory("Workspace", (float).8, (float).05, (float).1, (float).0);
    ft.addFController(new SaveButton(cp5, ft, this));
    ft.addFController(new OpenButton(cp5, ft, this));
    ft.addFController(new ExportButton(cp5, ft, this));
    ft.addFController(new ImportButton(cp5, ft, this));
    ft.addFController(new TogComButton(cp5, ft, this));
    ft.addFController(new GridButton(cp5, ft, this));

   }


  private FToolbar toolbarFactory(String name, float sizeX, float sizeY, float posX, float posY){
    FToolbar ret = new FToolbar(sketch, cp5, name);
    tbList.add(ret);
    ret.setSize(sizeX, sizeY);
    ret.setPos(posX, posY);
    return ret;
  }



  public void updateRGB(int r, int g, int b){
    rSlider.updateState(r);
    gSlider.updateState(g);
    bSlider.updateState(b);

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
