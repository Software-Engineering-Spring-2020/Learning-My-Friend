package frontend;


import backend.Window;
import frontend.controlP5.*;
import frontend.fcontrollers.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;
import processing.core.*;


/**
  * GUI is responsable for the creation and update of the toolbars and surounding GUI.
  * GUI also holds the toolbars, UIgroups, and UIControllers. It holds state infromation
  * like what tool is selected. It also acts as an adapter between CP5/fcontrollers buttons
  * and Window. All Buttons are passed GUI.
  *
  * @author Hunter Chasens
  * @version 1.0
  * @since 02.29.2019
  *
  */

public class GUI {
  PApplet sketch;
  Window win;
  // ControlP5 is a GUI lib
  ControlP5 cp5;
  // list of toolbars
  LinkedList<FToolbar> tbList;

  //Active Toolbar List
  LinkedList<FToolbar> activeTbList;


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
   * slider objects for rgb control
   */
  FSlider rSlider, gSlider, bSlider;


/**
 * current string entered
 */
  String currentString = "";

  // count to pass to IOhandler
  int polyCount = 3;

  boolean toggleFill = true;

  //The top buttonBar responsable for changing topChoice. This will allow the user to select menues
  FButtonBar buttonBar;

  //scrollbar for thumbnails and slide selection
  Scrollbar sb;


  //ScrollMenu to display thumbnails and slide selection
  ScrollMenu sm;

  //PresentMode Toolbar (must have visability set to false during normal use, then set to true during presentations)
  FToolbar presentModeToolbar;

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
  activeTbList = new LinkedList<FToolbar>();

  lastAppletWidth = sketch.width;
  lastAppletHeight = sketch.height;

  sm = new ScrollMenu(sketch, 200, 400, 200, 400, new ArrayList<PImage>());


  setup();
}



/**
 * [present enters present mode and hides all toolbars untill presentation is finished]
 */
  public void present(){
    win.present();
    setTool('s');
    for(FToolbar ft : tbList)
      ft.setVisable(false);
    presentModeToolbar.setVisable(true);
  }

/**
 * [nextSlide changes the slide in present mode, it also check to see if the presentation is done, and if so, exits present mode returning the gui]
 */
  public void nextSlide(){
    //TODO uncomment this if statemnet after API change
    //if(nextSlide())
      exitPresentMode();
  }


/**
 * [exitPresentMode exits presentation mode]
 */
  private void exitPresentMode(){
    for(FToolbar ft : tbList)
      ft.setVisable(true);
    presentModeToolbar.setVisable(false);
    setActiveToolbar(0);
  }


  /**
   * [toggleFill changes wheather or not the setFill() function sets the boarder color]
   */
  public void toggleFill(){
    toggleFill = !toggleFill;
  }

/**
 * [setPolyCount sets the set intiger of polyCount, or the sides the will be allowed during the creation of a polygon]
 * @param i [polygon side creation count]
 */
  public void setPolyCount(int i){
    polyCount = i;
  }

/**
 * [getPolyCount gets the set intiger of polyCount, or the sides the will be allowed during the creation of a polygon]
 * @return [polygon side creation count]
 */
  public int getPolyCount(){
    return polyCount;
  }


  /**
   * [copy copys the selected object]
   */
  public void copy(){
    win.copy();
  }

/**
 * [paste pastes the selected object]
 */
  public void paste(){
    win.paste();
  }


/**
 * [group groups objects]
 */
  public void group(){
    win.group();
  }


/**
 * [ungroup ungroups objects]
 */
  public void ungroup(){
    win.unGroup();
  }

/**
 * [setRotate rotates selected object]
 * @param i [degreses]
 */
  public void setRotate(float i){
    win.rotate(i);
  }

/**
 * [setSize sets the size of an object]
 * @param i [size multiplyer]
 */
  public void setSize(float i){
    win.resizeSelected(i);
  }

/**
 * [toggleComments toggles comments on or off]
 */
  public void toggleComments(){
    win.toggleComments();
  }


/**
 * [getCurrentString retreves the current string for the TextBox]
 * @return [the current string in the text box]
 */
  public String getCurrentString(){
    return currentString;
  }

/**
 * [setCurrentString sets the string in the textbox]
 * @param currentString [the string in the textbox]
 */
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
    win.newToolSelection();
    this.tool = tool;
  }


  public void trash(){
    win.delete();
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
    sketch.selectOutput("Select a file to save to export to", "exportFileSelected", f, this);
  }

  public void exportFileSelected(File selection) {
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      String fname = selection.getAbsolutePath();
      // trim the file extension first.
      if (fname.contains(".")) {
        fname = fname.substring(0, fname.lastIndexOf('.'));
      }
      win.exportAs(fname, ".png");
    }
 }

   public void fileImport() {
    File f = new File("drawing.png");
    sketch.selectInput("Select a file to open:", "importFileSelected", f, this);

  }

  public void importFileSelected(File selection) {
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      String fname = selection.getAbsolutePath();
      String fext = ".png";
      // trim the file extension first.
      if (fname.contains(".")) {
        fext = fname.substring(fname.lastIndexOf('.'), fname.length());
        fname = fname.substring(0, fname.lastIndexOf('.'));
      }
      win.importImage(fname, fext);
    }
 }

   public void open() {
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
   * [newSlide creates a new slide]
   */
  public void newSlide(){
    win.createSlideAt();
  }


  /**
   * [dekSlide deletes the selected slide]
   */
  public void delSlide(){
    win.deleteSlide();
  }


  /**
   * [upSlide moves the selected slide up in the array]
   */
  public void upSlide(){
    win.moveSlideUp();
  }


  /**
   * [downSlide moves the selected slide down in the array]
   */
  public void downSlide(){
    win.moveSlideDown();
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
    if(toggleFill)
      win.setFillColor(fillColor[0], fillColor[1], fillColor[2], 255);
    else
      setBoarder(i, c);
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

   /**
    * [toggleGrid turns the canvas grid on or off]
    */
   public void toggleGrid(){
    win.toggleGrid();
   }



/**
 * Start of Toolbar Section
 */



/**
 * [setup is a init for all toolbars]
 */

   private void setup(){
     //toolbar menu, very top toolbar
     setupMenuSelectToolbar();

     //object setings rbg and ect..
     setupObjectSettingsToolbar();

     //file, save, save as, export
     setUpFileToolbar();

     //text menu
     setupTextToolbar();

     //draw stuff
     setupDrawToolbar();

     //animate stuff
     setupAnimateToolbar();

     //present toolbar
     setupPresentToolbar();

     //slide toolbar
     setupSlideToolbar();

     //scrollbar toolbar
     setupScrollbar();

     //sets up presentModeToolbar
     setupPresentModeToolbar();

     setActiveToolbar(0);
     resizeAll();
   }


   /**
    * Start of toolbar setup functions (1 function per toolbar)
    */

/**
 * [setupDrawToolbar initialises the toolbar for Draw]
 */
   private void setupDrawToolbar(){
     FToolbar ft = topToolbarFactory("Draw");
     ft.addFController(new PenButton(cp5, ft, this));
     ft.addFController(new LineButton(cp5, ft, this));
     ft.addFController(new CurveButton(cp5, ft, this));
     ft.addFController(new RectButton(cp5, ft, this));
     ft.addFController(new ElipButton(cp5, ft, this));
   }

/**
 * [setupTextToolbar initialises the toolbar for Text]
 */
   private void setupTextToolbar(){
     FToolbar ft = topToolbarFactory("TextOpt");
     ft.addFController(new TextButton(cp5, ft, this));

   }

/**
 * [setupAnimateToolbar initialises the toolbar for Animate]
 */
   private void setupAnimateToolbar(){
     FToolbar ft = topToolbarFactory("Animate");
     ft.addFController(new FadeInButton(cp5, ft, this));
     ft.addFController(new FadeOutButton(cp5, ft, this));
     ft.addFController(new TranslateButton(cp5, ft, this));

   }

/**
 * [setupPresentToolbar initialises the toolbar for Present]
 */
   private void setupPresentToolbar(){
     FToolbar ft = topToolbarFactory("Present");
     ft.addFController(new PresentButton(cp5, ft, this));
   }


/**
 * [setupObjectSettingsToolbar initialises the toolbar for object settings]
 */
   private void setupObjectSettingsToolbar(){
    FToolbar ft = toolbarFactory("Obj Set", (float).2, (float).8, (float).8, (float).1);
    ft.addFController(new SelecButton(cp5, ft, this));

    rSlider = new RFillSlider(cp5, ft, this);
    bSlider = new GFillSlider(cp5, ft, this);
    gSlider = new BFillSlider(cp5, ft, this);

    rSlider.slider.setColorBackground(sketch.color(100,0,0));
    rSlider.slider.setColorActive(sketch.color(255,0,0));
    rSlider.slider.setColorForeground(sketch.color(255,0,0));

    bSlider.slider.setColorBackground(sketch.color(0,100,0));
    bSlider.slider.setColorActive(sketch.color(0,255,0));
    bSlider.slider.setColorForeground(sketch.color(0,255,0));

    gSlider.slider.setColorBackground(sketch.color(0,0,100));
    gSlider.slider.setColorActive(sketch.color(0,0,255));
    gSlider.slider.setColorForeground(sketch.color(0,0,255));


    ft.addFController(rSlider);
    ft.addFController(gSlider);
    ft.addFController(bSlider);
    ft.addFController(new TogFillButton(cp5, ft, this));
    ft.addFController(new SizeSlider(cp5, ft, this));
    ft.addFController(new RotateSlider(cp5, ft, this));
    ft.addFController(new TrashButton(cp5, ft, this));
  }

  /**
   * [setUpFileToolbar initialises the toolbar for File]
   */
    private void setUpFileToolbar(){
      FToolbar ft = topToolbarFactory("File");

      ft.addFController(new CopyButton(cp5, ft, this));
      ft.addFController(new PasteButton(cp5, ft, this));

      ft.addFController(new SaveButton(cp5, ft, this));
      ft.addFController(new OpenButton(cp5, ft, this));
      ft.addFController(new ExportButton(cp5, ft, this));
      ft.addFController(new ImportButton(cp5, ft, this));
     }

/**
 * [setupMenuSelectToolbar initialises the toolbar for menu selection (big toolbar on top to pic sub toolbar)]
 */
  private void setupMenuSelectToolbar(){
    FToolbar ft = toolbarFactory("MenuSelectTB", (float).8, (float).06, (float).1, (float).0);
    ft.setBoarder((float).05,(float).05);
    ft.addFController(new MenuSelect(cp5, ft, this));
    //ft.setVisable(false);

  }

/**
 * [setupSlideToolbar initialises the toolbar for slides]
 */
  private void setupSlideToolbar(){
    FToolbar ft = toolbarFactory("Slides", (float).1, (float).05, (float).06, (float).2);
    //Disables the users ability to clapse the toolbar
    ft.getGroup().disableCollapse();
    //reduces boarder
    ft.setBoarder((float).005,(float).005);

    ft.addFController(new NewSlideButton(cp5, ft, this));
    ft.addFController(new DelSlideButton(cp5, ft, this));
    ft.addFController(new UpSlideButton(cp5, ft, this));
    ft.addFController(new DownSlideButton(cp5, ft, this));


  }

  /**
   * [setupScrollbar initialises the toolbar for scrolling thumbnails and slides]
   */
  private void setupScrollbar(){
    FToolbar ft = toolbarFactory("Scrollbar", (float).01, (float).8, (float).16, (float).2);
    ft.getGroup().hideBar();
    ft.getGroup().disableCollapse();
    sb = new Scrollbar(cp5, ft, this);
    ft.addFController(sb);
  }


/**
 * [setupPresentModeToolbar description]
 */
  private void setupPresentModeToolbar(){
    presentModeToolbar = toolbarFactory("PresentMode", (float).1, (float).05, (float).9, (float).9);
    presentModeToolbar.setVisable(false);
  }

/**
 * Start of toolbar administartion functions
 */

   /**
    * [setActiveToolbar sets the active toolbar]
    * @param i [index of the toolbar to activate in order of top toolbar added]
    */
   public void setActiveToolbar(int i){
     for(FToolbar ftb : activeTbList)
        ftb.setVisable(false);
     activeTbList.get(i).setVisable(true);
   }


/**
 * [topToolbarFactory is a factory for creating the top toolbars that are chosen from the buttonbar. This facotry automaticly adds the tb to the internal list of top tooblars and sets its size.]
 * @param  name [the name to pass to cp5, needs to be unique]
 * @return      [FToolbar]
 */
   private FToolbar topToolbarFactory(String name){
     FToolbar ft = toolbarFactory(name, (float).8, (float).05, (float).1, (float).06);
     activeTbList.add(ft);
     ft.getGroup().hideBar();
     ft.getGroup().disableCollapse();
     return ft;
   }


/**
 * [toolbarFactory is a factory method for creating toolbars]
 * @param  name  [the given name of the toolbar, needed for cp5, needs to be unique]
 * @param  sizeX [size of toolbar in percetage of screen]
 * @param  sizeY [size of toolbar in percetage of screen]
 * @param  posX  [position of toolbar in percetage of screen]
 * @param  posY  [position of toolbar in percetage of screen]
 * @return       [Ftoolbar with requested arguemnts]
 */
  private FToolbar toolbarFactory(String name, float sizeX, float sizeY, float posX, float posY){
    FToolbar ret = new FToolbar(sketch, cp5, name);
    tbList.add(ret);
    ret.setSize(sizeX, sizeY);
    ret.setPos(posX, posY);
    return ret;
  }


/**
 * [updateRGB updates the rgb sliders in the GUI]
 * @param r [red value between 0 and 255]
 * @param g [green value between 0 and 255]
 * @param b [blue value between 0 and 255]
 */
  public void updateRGB(int r, int g, int b){
    rSlider.updateState(r);
    gSlider.updateState(g);
    bSlider.updateState(b);

  }




   /**
    * [display checks to see if the applet size has changed. If it has it triggers a resize.]
    */
   public void display(){
     if(lastAppletWidth != sketch.width || lastAppletHeight != sketch.height){
      resizeAll();
      lastAppletWidth = sketch.width;
      lastAppletHeight = sketch.height;
    }
     //Check for window resize and if so update all toolbars

     //displays ScrollMenu
     sm.display();
   }



   /**
    * [resizeAll resizes all the gui elements]
    */
   public void resizeAll(){
     for(FToolbar tb : tbList)
      tb.update();

   }

}
