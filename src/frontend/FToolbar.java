package frontend;
/**
 * <h1>FToolbar</h1>
 * FToolbar is a ControlP5 group object adapter that holds and distributes `FController`s.
 * A FToolbar holds a list of FControllers
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.09.2019
 */


//Do i need this?
import frontend.fcontrollers.*;
import frontend.controlP5.*;
import processing.core.*;

import java.util.LinkedList;

public class FToolbar{
  //PApplet of the sketch
  PApplet sketch;

  ControlP5 cp5;
  //ControlP5 group that holds all the children Controllers
  Group g;

  // the size and position of the group and toolbar
  float sizeX, sizeY, posX, posY;

  //real size and pos in pixel count
  int sizeRX, sizeRY, posRX, posRY;

  //name of the toolbar
  String name;

  //List of fcontrollers
  public LinkedList<FController> conList;

  //the groups boarders/margins
  float boarderX, boarderY, boarderController;

  public FToolbar(PApplet sketch, ControlP5 cp5, String name){
    sizeX = (float).05;
    sizeY = (float).9;
    posX = (float).5;
    posY = (float).0;

    conList = new LinkedList<FController>();

    boarderX = (float).1;
    boarderY = (float).05;

    this.cp5 = cp5;
    this.sketch = sketch;
    g = groupFactory(name);
    update();
    //hides top bar and lables
    g.hideBar();
    g.disableCollapse();
  }

/**
 * [groupFactory returns a ControlP5 Group object]
 * @return [a ControlP5 Group object]
 */
  private Group groupFactory(String name){
    return cp5.addGroup(name)
    .setBackgroundColor(200)
    //.setMoveable(true)
    ;
  }

  /**
   * [getGroup retruns the controlP5 group related to this toolbar]
   * @return [the controlP5 group related to this toolbar]
   */
  public Group getGroup(){
    return g;
  }

/**
 * [setSizeX sets the width of the toolbar in percentage of screen]
 * @param x [percentage of the width of the screen the toolbar takes up]
 */
  protected void setSizeX(float x){
    this.sizeX = x;
  }

/**
 * [setSizeY sets the height of the toolbar in percentage of screen]
 * @param y [percentage of the height of the screen the toolbar takes up]
 */
  protected void setSizeY(float y){
    this.sizeY = y;
  }

/**
 * [setSize sets the width and height of the toolbar in percentage of screen]
 * @param x [percentage of the width of the screen the toolbar takes up]
 * @param y [percentage of the height of the screen the toolbar takes up]
 */
  protected void setSize(float x, float y){
    this.sizeX = x;
    this.sizeY = y;
  }

/**
 * [getSizeX gets the width of the toolbar in percentage of screen]
 * @return [percentage of the width of the screen the toolbar takes up]
 */
  protected float getSizeX(){
    return sizeX;
  }

/**
 * [getSizeY getSizeX gets the height of the toolbar in percentage of screen]
 * @return [percentage of the height of the screen the toolbar takes up]
 */
  protected float getSizeY(){
    return sizeY;
  }

/**
 * [setPosX sets the position of the toolbar in percentage of screen' width]
 * @param x [percentage of screen's width where the toolbar's upper left corner is]
 */
  protected void setPosX(float x){
    this.posX = x;
  }

/**
 * [setPoxY sets the position of the toolbar in percentage of screen's height]
 * @param y [percentage of screen's height where the toolbar's upper left corner is]
 */
  protected void setPosY(float y){
    this.posY = y;
  }

/**
 * [setPos sets the position of the toolbar in percentage of screen's width and height]
 * @param x [percentage of screen's width where the toolbar's upper left corner is]
 * @param y [percentage of screen's height where the toolbar's upper left corner is]
 */
  protected void setPos(float x, float y){
    this.posX = x;
    this.posY = y;
  }

/**
 * [getPosX gets the position of the toolbar in percentage of screen's width]
 * @return [percentage of screen's width where the toolbar's upper left corner is]
 */
  protected float getPosX(){
    return posX;
  }

/**
 * [getPoxY sets the position of the toolbar in percentage of screen's height]]
 * @return [percentage of screen's hieght where the toolbar's upper left corner is]
 */
  protected float getPosY(){
    return posY;
  }






//TODO recomment all the REAL Var SETTERS AND GETTERS



  /**
   * [setRealSizeRX sets the width of the toolbar in piRXel]
   * @param RX [percentage of the width of the screen the toolbar takes up]
   */
    public void setRealSizeRX(int RX){
      this.sizeRX = RX;
    }

  /**
   * [setRealSizeRY sets the height of the toolbar in percentage of screen]
   * @param RY [percentage of the height of the screen the toolbar takes up]
   */
    public void setRealSizeRY(int RY){
      this.sizeRY = RY;
    }

  /**
   * [setRealSize sets the width and height of the toolbar in percentage of screen]
   * @param RX [percentage of the width of the screen the toolbar takes up]
   * @param RY [percentage of the height of the screen the toolbar takes up]
   */
    public void setRealSize(int RX, int RY){
      this.sizeRX = RX;
      this.sizeRY = RY;
    }

  /**
   * [getRealSizeRX gets the width of the toolbar in percentage of screen]
   * @return [percentage of the width of the screen the toolbar takes up]
   */
    public int getRealSizeRX(){
      return sizeRX;
    }

  /**
   * [getRealSizeRY getRealSizeRX gets the height of the toolbar in percentage of screen]
   * @return [percentage of the height of the screen the toolbar takes up]
   */
    public int getRealSizeRY(){
      return sizeRY;
    }

  /**
   * [setRealPosRX sets the position of the toolbar in percentage of screen' width]
   * @param RX [percentage of screen's width where the toolbar's upper left corner is]
   */
    public void setRealPosRX(int RX){
      this.posRX = RX;
    }

  /**
   * [setPoRXRY sets the position of the toolbar in percentage of screen's height]
   * @param RY [percentage of screen's height where the toolbar's upper left corner is]
   */
    public void setRealPosRY(int RY){
      this.posRY = RY;
    }

  /**
   * [setRealPos sets the position of the toolbar in percentage of screen's width and height]
   * @param RX [percentage of screen's width where the toolbar's upper left corner is]
   * @param RY [percentage of screen's height where the toolbar's upper left corner is]
   */
    public void setRealPos(int RX, int RY){
      this.posRX = RX;
      this.posRY = RY;
    }

  /**
   * [getRealPosRX gets the position of the toolbar in percentage of screen's width]
   * @return [percentage of screen's width where the toolbar's upper left corner is]
   */
    public int getRealPosRX(){
      return posRX;
    }

  /**
   * [getPoRXRY sets the position of the toolbar in percentage of screen's height]]
   * @return [percentage of screen's hieght where the toolbar's upper left corner is]
   */
    public int getRealPosRY(){
      return posRY;
    }



/**
 * [setBoarderX sets the toolbar's horizontal boarder/margin in percetage of toolbar's space]
 * @param x [percetabe of toolbar's horizontal boarder]
 */
  protected void setBoarderX(float x){
    boarderX = x;
  }

/**
 * [setBoarderY sets the toolbar's verticle boarder/margin in percetage of toolbar's space]
 * @param y [percetabe of toolbar's verticle boarder]
 */
  protected void setBoarderY(float y){
    boarderY = y;
  }

/**
 * [setBoarder sets the toolbar's horizontal and verticle boarder/margin in percetage of toolbar's space]
 * @param x [percetabe of toolbar's horizontal boarder]
 * @param y [percetabe of toolbar's verticle boarder]
 */
  protected void setBoarder(float x, float y){
    boarderX = x;
    boarderY = y;
  }

/**
 * [getBoarderX gets the toolbar's horizontal boarder/margin in percetage of toolbar's space]
 * @return [percetabe of toolbar's horizontal boarder]
 */
  protected float getBoarderX(){
    return boarderX;
  }


/**
 * [getBoarderY gets toolbar's verticle boarder/margin in percetage of toolbar's space]
 * @return [percetabe of toolbar's verticle boarder]
 */
  protected float getBoarderY(){
    return boarderY;
  }

/**
 * [setBoarderController sets the space between each controller in fuctions by percetage of groups space]
 * @param b [the space between each controller in fuctions by percetage of groups space]
 */
  protected void setBoarderController(int b){
    boarderController = b;
  }

/**
 * [getBoarderController gets the space between each controller in fuctions by percetage of groups space]
 * @return [the space between each controller in fuctions by percetage of groups space]
 */
  protected float getBoarderController(){
    return boarderController;
  }

/**
 * [setName sets the toolbar's name]
 * @param name [the toolbar's name]
 */
  protected void setName(String name){
    this.name = name;
  }

/**
 * [getName gets the toolbar's name]
 * @return [the toolbar's name]
 */
  protected String getName(){
    return name;
  }



/**
 * [addFController adds a FController to the toolbar]
 * @param con [the FController to be added]
 */
  protected void addFController(FController con){
    conList.add(con);
  }

  /**
   * [setVisable sets the visability of the toolbar]
   * @param b [the visability of the toolbar]
   */
  public void setVisable(boolean b){
    g.setVisible(b);
  }


/** TODO compleate this
 * [update updates the scales of the group and its children relitive to PApplet's size.]
 */
  protected void update(){
    //sets toolbar size and position

    setRealSize((int)Math.round(sketch.width*sizeX), (int)Math.round(sketch.height*sizeY));
    setRealPos((int)Math.round(sketch.width*posX), (int)Math.round(sketch.height*posY));
    g.setSize(getRealSizeRX(), getRealSizeRY());
    g.setPosition(getRealPosRX(), getRealPosRY());
    g.setBackgroundHeight(getRealSizeRY());
  //  System.out.println(g.getName() + " Width%: " + sizeX + " Height%: "  + sizeY +
    //"\n Sketch Width: " + sketch.width + "Sketch Height: " + sketch.height +
    //"\n Width pix: " + getRealPosRX());
    //sets FController size and position
    //curent version does not use priotirtys



    int n = conList.size();

    //start of Y boarder
    //start of X boarder
    //delta of Y boarder aka workable space or space in which we can put Controllers
    float deltaY = (1-(boarderY*2));
    float deltaX = (1-(boarderX*2));
    //System.out.println(deltaX);
    //delta of x boarder aka workable space or space in which we can put Controllers, width of controllers
    //    int deltaX
    // find avalable space
    // find the size of each Controller
    // starts resizing each controller

    //for(FController fc : conList)


    //if its longger then it is wide
    if(conList.size()>0){
      FController fc;
      int p = 0;
      if(getSizeX() < getSizeY()){
        for(float i = boarderY; i < deltaY; i+= (deltaY/conList.size())){
          fc = conList.get(p);
          fc.setPos((float)boarderX, (float)i);
          fc.setSize((float)deltaX, (float)deltaY/conList.size());
          fc.update();
          p++;
        }
      }
      else{
        for(float i = boarderX; i < (deltaX+.000005); i+= (deltaX/conList.size())){
          //System.out.println("Forloop started");
          fc = conList.get(p);
          fc.setPos((float)i, (float)boarderY);
          fc.setSize((float)deltaX/conList.size(), (float)deltaY);
          fc.update();
          p++;
        }
      }
    }
  }

/**
 * [boarder creates a boarder around FControllers that call it (not working, CP5 draws over this)]
 * @param xPos  [x start postion]
 * @param yPos  [y start postion]
 * @param xSize [width]
 * @param ySize [height]
 */
  protected void boarder(int xPos, int yPos, int xSize, int ySize){

    sketch.rect(xPos, yPos, xSize, ySize);
  }

}
