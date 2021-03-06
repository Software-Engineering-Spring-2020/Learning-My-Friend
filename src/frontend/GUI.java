package frontend;


import backend.Window;
import frontend.controlP5.*;
import frontend.fcontrollers.*;
import java.util.*;
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

  public int color[] = { 0, 0, 0, 0 };

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


  //PresentMode Toolbar (must have visability set to false during normal use, then set to true during presentations)
  FToolbar presentModeToolbar;

  //TextMode affects what type of textbox is made, plain text, bullets, numbered list
  Window.TextMode textMode;

  //font, the selected font to pass to IOhandler
  String font;

  String[] fontList = {"AmarBangla.ttf", "Amar-Desh.ttf", "BenSenHandwriting.ttf", "Ekushey-Bangla.ttf", "Lal-Sabuj.ttf", "NikoshGrameen.ttf", "SiyamRupali.ttf"};

  // buttons that can be highlighted when tools are selected
  FButton textButton;
  FButton numberedListButton;
  FButton bulletListButton;
  FButton penButton;
  FButton lineButton;
  FButton curveButton;
  FButton rectangleButton;
  FButton ovalButton;
  FButton fadeInButton;
  FButton fadeOutButton;
  FButton translateButton;

  FButton lastSelected;
  FButton currentSelected;

  boolean objectToolSelected;


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
  Label.setUpperCaseDefault(false);
  cp5 = new ControlP5(sketch);
  cp5.setFont(sketch.createFont("Lal-Sabuj.ttf", 15));
  tbList = new LinkedList<FToolbar>();
  activeTbList = new LinkedList<FToolbar>();

  lastAppletWidth = sketch.width;
  lastAppletHeight = sketch.height;


  setup();
  textMode = Window.TextMode.PLAIN;
  font = "Lal-Sabuj.ttf";

  win.initScrollMenu(getCurrentMenuPosX(), getCurrentMenuPosY(), getCurrentMenuSizeX(), getCurrentMenuSizeY());
}

/**
 * [animate will animate selected object or equip the animate tool]
 * @param i [the type of animation, 0 = FadeOut, 1 = FadeIn, 2 = Transilate]
 */
  public void animate(int i){
    if(i == 0)
      win.addAnimation(Window.AnimationOption.FADE_OUT, sketch.mouseX, sketch.mouseY);
      setTool('s');
    if(i == 1)
      win.addAnimation(Window.AnimationOption.FADE_IN, sketch.mouseX, sketch.mouseY);
      setTool('s');
    if(i == 2)
      setTool('h');
  }



/**
 * [getCurrentMenuPosX getst the thumbnail menu position X]
 * @return [thumbnail menu position in precentage of screen, X]
 */
  private int getCurrentMenuPosX(){
    return (int)(sketch.width*(.15/2));
  }
  /**
   * [getCurrentMenuPosY  getst the thumbnail menu position Y]
   * @return [thumbnail menu position in precentage of screen, Y]
   */
  private int getCurrentMenuPosY(){
    return (int)(sketch.height*.56);
  }
  /**
   * [getCurrentMenuSizeX  getst the thumbnail menu size X]
   * @return [thumbnail menu size in precentage of screen, X]
   */
  private int getCurrentMenuSizeX(){
    return (int)(sketch.width*.15);
  }
  /**
   * [getCurrentMenuSizeY  getst the thumbnail menu size Y]
   * @return [thumbnail menu size in precentage of screen, Y]
   */
  private int getCurrentMenuSizeY(){
    return (int)(sketch.height*.8);
  }



/**
 * [import allows buttons to select multiple import types to be triggered]
 * @param i [the int code of the input to trigger]
 */
  public void importMethod(int i){
    if(i == 0)
      fileImport();
    else if(i == 1)
      soundImport();
    else if(i ==2)
      win.createYouTubeTextBox(sketch.width/2, sketch.height/2);
    else{
      fileImport();
    }
  }





  /**
   * [getFont returns the string of the curently selected font]
   * @return [the curent font]
   */
  public String getFont(){
    return font;
  }

/**
 * [setFont sets the font as listed in PFont.list()]
 * @param i [the number in PFont.list() of the selected font]
 */
  public void setFont(int i){
    font = fontList[i];
  }

/**
 * [setTextMode sets the `state` to be called by IOHandler, this method changes the TextMode]
 * @param e [Window.TextMode enum, : BULLETED, NUMBERED, PLAIN]
 */
public void setTextMode(Window.TextMode e){
  textMode = e;
}

/**
 * [getTextMode returns textMode, to be called by IOHandler]
 * @return [returns the state of textMode]
 */
public Window.TextMode getTextMode(){
  return textMode;
}

/**
 * [present enters present mode and hides all toolbars untill presentation is finished]
 */
  public void present(boolean fromStart){
    if(fromStart)
      win.selectSlide(0);
    present();
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
    //TODO does this return true or false if done?
    if(!win.nextSlide())
      exitPresentMode();
    System.out.println("nextslide");
  }

/**
 * [previousSlide changes the slide in present mode]
 */
  public void previousSlide(){
    win.previousSlide();
  }


/**
 * [exitPresentMode exits presentation mode]
 */
  public void exitPresentMode(){
    win.endPresent();
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

    // Highlight button based on tool selected

    objectToolSelected = false;

    if((tool == 'p') && (penButton != null)){
      setSelectedButton(penButton);
    }
    if((tool == 'l') && (lineButton != null)){
      setSelectedButton(lineButton);
    }
    if ((tool == 'u') && (curveButton != null)){
      setSelectedButton(curveButton);
    }
    if ((tool == 'r') && (rectangleButton != null)){
      setSelectedButton(rectangleButton);
    }
    if ((tool == 'e') && (ovalButton != null)){
      setSelectedButton(ovalButton);
    }
    if ((tool == 't')){
      if((getTextMode() == Window.TextMode.PLAIN) && (textButton != null)){
        setSelectedButton(textButton);
      }
      if((getTextMode() == Window.TextMode.BULLETED) && (bulletListButton != null)){
        setSelectedButton(bulletListButton);
      }
      if((getTextMode() == Window.TextMode.NUMBERED) && (numberedListButton != null)){
        setSelectedButton(numberedListButton);
      }
    }
    if ((tool == 'f' ) && (fadeInButton != null)){ // fade in
      setSelectedButton(fadeInButton);
    }
    if ((tool == 'g' ) && (fadeOutButton != null)){ // fade in
      setSelectedButton(fadeOutButton);
    }
    if ((tool == 'h' ) && (translateButton != null)){ // fade in
      setSelectedButton(translateButton);
    }
    if (lastSelected != null){
      if ((!objectToolSelected) || (lastSelected != currentSelected)){
        // If the new tool selected isn't an object-manipulation tool
        // Or if the current tool selected isn't the past selected tool
        // De-highlight the current button
        if(lastSelected==fadeInButton){
          lastSelected.button.setColorBackground(sketch.color(150,0,0));
        }
        else if(lastSelected==fadeOutButton){
          lastSelected.button.setColorBackground(sketch.color(0,150,0));
        }
        else if(lastSelected==translateButton){
          lastSelected.button.setColorBackground(sketch.color(0,0,100));
        }
        else{
          lastSelected.button.setColorBackground(sketch.color(0, 45, 90));;
        }
      }
    }
    lastSelected = currentSelected;
  }

  private void setSelectedButton(FButton b){
    if(b==fadeInButton){
      b.button.setColorBackground(sketch.color(200,0,0));
    }
    else if(b==fadeOutButton){
      b.button.setColorBackground(sketch.color(0,200,0));
    }
    else if(b==translateButton){
      b.button.setColorBackground(sketch.color(0,0,200));
    }
    else{
      b.button.setColorBackground(sketch.color(0, 170, 255));
    }
    currentSelected = b;
    objectToolSelected = true;
  }


  public void trash(){
    //System.out.println("test");
    win.delete();
  }

  public void restore(){
    win.restoreLast();
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
      //System.out.println("Window was closed or the user hit cancel.");
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
    File f = new File("drawing");
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

  public void soundImport(){
    File f = new File("sound.mp3");
    System.out.println("asking soundImport()");
    sketch.selectInput("Select a file to open:", "importSoundCallback", f, this);
  }

  /**
   * [importSoundCallback callback called by PApplet]
   * @param selection [file in callback arugemnt]
   */
  public void importSoundCallback(File selection){
    if (selection == null) {
      System.out.println("Window was closed or the user hit cancel.");
    } else {
      String fname = selection.getAbsolutePath();
      String fext = ".mp3";
      // trim the file extension first.
      if (fname.contains(".")) {
        fext = fname.substring(fname.lastIndexOf('.'), fname.length());
        fname = fname.substring(0, fname.lastIndexOf('.'));
      }
      System.out.println("asking winow to add sound");
      win.addAnimation(Window.AnimationOption.PLAY_SOUND, fname, fext);
    }
  }

  /**
   * [fileImport is called to import images]
   */
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
    updateScrollbar();
  }


  /**
   * [dekSlide deletes the selected slide]
   */
  public void delSlide(){
    win.deleteSlide();
    updateScrollbar();
  }


  /**
   * [upSlide moves the selected slide up in the array]
   */
  public void upSlide(){
    //System.out.println("upslide");
    win.moveSlideUp();
  }

/**
 * [scrollTo sets the scrollMenu to the slide listed]
 * @param i [the slide to be the new start of displayed slides on the scrollMenu]
 */
  public void scrollTo(int i){
    	win.scroll(-1*i);
  }

  /**
   * [downSlide moves the selected slide down in the array]
   */
  public void downSlide(){
    win.moveSlideDown();
  }

  /**
   * [setFill sets the color]
   * @param i [color from 1 to 255]
   * @param c [color r, b, g]
   * currently unused as client did not ask for boarder settings and it takes up space in the gui, is controlled by setFill
   */
  public void setColor(int i, char c){
    if(c == 'r')
      color[0] = i;
    if(c == 'g')
      color[1] = i;
    if(c == 'b')
      color[2] = i;
    if(c == 'a')
      color[3] = i;
    win.setBoarderColor(color[0], color[1], color[2]);
    win.setFillColor(color[0], color[1], color[2], 255);
  }

  public int returnColor(int r, int g, int b){
    return sketch.color(r, g, b);
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
 * [updateScrollbar updatest the number of slides one can scroll to]
 */
   public void updateScrollbar(){
    /// sb.setMin(getSlideCount())
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

     penButton = new PenButton(cp5, ft, this);
     ft.addFController(penButton);
     lineButton = new LineButton(cp5, ft, this);
     //ft.addFController(lineButton);
     curveButton = new CurveButton(cp5, ft, this);
     //ft.addFController(curveButton);
     rectangleButton = new RectButton(cp5, ft, this);
     ft.addFController(rectangleButton);
     ovalButton = new ElipButton(cp5, ft, this);
     ft.addFController(ovalButton);

   }

/**
 * [setupTextToolbar initialises the toolbar for Text]
 */
   private void setupTextToolbar(){
     FToolbar ft = topToolbarFactory("TextOpt");
     textButton = new TextButton(cp5, ft, this);
     ft.addFController(textButton);
     numberedListButton = new NumListButton(cp5, ft, this);
     ft.addFController(numberedListButton);
     bulletListButton = new BulletListButton(cp5, ft, this);
     ft.addFController(bulletListButton);
     FDropdown fd = new FontDropdown(cp5, ft, this);

     //String[] fonts
     String[] fonts = {"AmarBangla", "Amar-Desh", "BenSenHandwriting", "Ekushey-Bangla", "Lal-Sabuj", "NikoshGrameen", "SiyamRupali"};

     fd.setItems(fonts);
     ft.addFController(fd);

   }

/**
 * [setupAnimateToolbar initialises the toolbar for Animate]
 */
   private void setupAnimateToolbar(){
     FToolbar ft = topToolbarFactory("Animate");

     fadeInButton = new FadeInButton(cp5, ft, this);
     fadeInButton.button.setColorBackground(sketch.color(150,0,0));
     fadeInButton.button.setColorActive(sketch.color(200,0,0));
     fadeInButton.button.setColorForeground(sketch.color(200,0,0));

     fadeOutButton = new FadeOutButton(cp5, ft, this);
     fadeOutButton.button.setColorBackground(sketch.color(0,150,0));
     fadeOutButton.button.setColorActive(sketch.color(0,200,0));
     fadeOutButton.button.setColorForeground(sketch.color(0,200,0));

     translateButton = new TranslateButton(cp5, ft, this);
     translateButton.button.setColorBackground(sketch.color(0,0,100));
     translateButton.button.setColorActive(sketch.color(0,0,200));
     translateButton.button.setColorForeground(sketch.color(0,0,200));

     ft.addFController(fadeInButton);
     ft.addFController(fadeOutButton);
     ft.addFController(translateButton);

   }

/**
 * [setupPresentToolbar initialises the toolbar for Present]
 */
   private void setupPresentToolbar(){
     FToolbar ft = topToolbarFactory("Present");
     //ft.addFController(new PresentButton(cp5, ft, this));
     ft.addFController(new PresentStartButton(cp5, ft, this));
     ft.addFController(new PresentCurrentButton(cp5, ft, this));
   }


/**
 * [setupObjectSettingsToolbar initialises the toolbar for object settings]
 */
   private void setupObjectSettingsToolbar(){
    FToolbar ft = toolbarFactory("Obj Set", (float).2, (float).8, (float).78, (float).15);
    rSlider = new RFillSlider(cp5, ft, this);
    bSlider = new GFillSlider(cp5, ft, this);
    gSlider = new BFillSlider(cp5, ft, this);

    rSlider.slider.setColorBackground(sketch.color(100,0,0));
    rSlider.slider.setColorActive(sketch.color(255,0,0));
    rSlider.slider.setColorForeground(sketch.color(255,0,0));
    rSlider.setLabelColor(20);
    //rSlider.slider.setNumberOfTickMarks(6);
    //rSlider.slider.snapToTickMarks(false);
    //rSlider.slider.showTickMarks(true);


    bSlider.slider.setColorBackground(sketch.color(0,100,0));
    bSlider.slider.setColorActive(sketch.color(0,255,0));
    bSlider.slider.setColorForeground(sketch.color(0,255,0));
    bSlider.setLabelColor(20);


    gSlider.slider.setColorBackground(sketch.color(0,0,100));
    gSlider.slider.setColorActive(sketch.color(0,0,255));
    gSlider.slider.setColorForeground(sketch.color(0,0,255));
    gSlider.setLabelColor(20);



    ft.addFController(rSlider);
    ft.addFController(gSlider);
    ft.addFController(bSlider);
    //ft.addFController(new TogFillButton(cp5, ft, this));
    FSlider ss = new SizeSlider(cp5, ft, this);
    ss.setLabelColor(20);
    ft.addFController(ss);

    FSlider rs = new RotateSlider(cp5, ft, this);
    rs.setLabelColor(20);
    ft.addFController(rs);

    ft.addFController(new RestoreTrashButton(cp5, ft, this));
    ft.addFController(new TrashButton(cp5, ft, this));
  }

  /**
   * [setUpFileToolbar initialises the toolbar for File]
   */
    private void setUpFileToolbar(){
      FToolbar ft = topToolbarFactory("File");

      //ft.addFController(new CopyButton(cp5, ft, this));
      //ft.addFController(new PasteButton(cp5, ft, this));

      ft.addFController(new SaveButton(cp5, ft, this));
      ft.addFController(new OpenButton(cp5, ft, this));
      ft.addFController(new ExportButton(cp5, ft, this));

      String[] importMethods = {"Picture", "Sound", "Youtube"};
      FDropdown fd = new ImportDropdown(cp5, ft, this);
      fd.setItems(importMethods);
      ft.addFController(fd);
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
    FToolbar ft = toolbarFactory("Slides", (float).15, (float).05, (float).0, (float).1);
    //Disables the users ability to clapse the toolbar
    ft.getGroup().disableCollapse();
    //reduces boarder
    ft.setBoarder((float).005,(float).005);

    ft.addFController(new NewSlideButton(cp5, ft, this));
    ft.addFController(new UpSlideButton(cp5, ft, this));
    ft.addFController(new DownSlideButton(cp5, ft, this));
    ft.addFController(new DelSlideButton(cp5, ft, this));
  }

  /**
   * [setupScrollbar initialises the toolbar for scrolling thumbnails and slides]
   */
  private void setupScrollbar(){
    FToolbar ft = toolbarFactory("Scrollbar", (float).02, (float).7, (float).16, (float).2);
    ft.getGroup().hideBar();
    ft.getGroup().disableCollapse();
    sb = new Scrollbar(cp5, ft, this);
    ft.addFController(sb);

    sb.slider.setColorBackground(sketch.color(100,100,100));
    sb.slider.setColorActive(sketch.color(100,100,100));
    sb.slider.setColorForeground(sketch.color(100,100,100));
    sb.slider.setHandleSize(50);
  }


/**
 * [setupPresentModeToolbar description]
 */
  private void setupPresentModeToolbar(){
    presentModeToolbar = toolbarFactory("PresentMode", (float).13, (float).08, (float).85, (float).9);
    presentModeToolbar.setVisable(false);
    presentModeToolbar.addFController(new PrevSlideButton(cp5, presentModeToolbar, this));
    presentModeToolbar.addFController(new NextSlideButton(cp5, presentModeToolbar, this));
    presentModeToolbar.addFController(new ExitSlideButton(cp5, presentModeToolbar, this));
  }


       /**
        * [setUpFileToolbar initialises the toolbar for File]
        */
         private void setUpImportToolbar(){
           FToolbar ft = topToolbarFactory("Import");

           ft.addFController(new ExportButton(cp5, ft, this));
           ft.addFController(new ImportButton(cp5, ft, this));
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
    * [setActiveToolbar sets the active toolbar]
    * @param i [index of the toolbar to activate in order of top toolbar added]
    */
   public void setActiveSubToolbar(int i){
     for(FToolbar ftb : tbList)
        ftb.setVisable(false);
     tbList.get(i).setVisable(true);
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


   }



   /**
    * [resizeAll resizes all the gui elements]
    */
   public void resizeAll(){
     cp5.setGraphics(sketch,0,0);
     for(FToolbar tb : tbList)
      tb.update();
    //System.out.println("% of width " + (sketch.width*.15) + "   --   % of height" + (sketch.height*.20));
     win.setMenuPosition(getCurrentMenuPosX(), getCurrentMenuPosY());
     win.setMenuSize(getCurrentMenuSizeX(), getCurrentMenuSizeY());
     win.reCenter();
     win.setWidth(sketch.width/2);
   }

}
