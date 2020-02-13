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

  //name of the toolbar
  String name;

  //List of fcontrollers
  LinkedList<FController> conList;

  //the groups boarders/margins
  float boarderX, boarderY, boarderController;

  public FToolbar(PApplet sketch, ControlP5 cp5, String name){
    sizeX = 100;
    sizeY = 100;
    posX = 0;
    posX = 0;

    conList = new LinkedList<FController>();

    boarderX = (float).05;
    boarderY = (float).05;

    this.cp5 = cp5;
    g = groupFactory(name);
  }

/**
 * [groupFactory returns a ControlP5 Group object]
 * @return [a ControlP5 Group object]
 */
  private Group groupFactory(String name){
    return cp5.addGroup(name)
    .setBackgroundColor(100)
    //.setMoveable(true)
    ;
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
  protected void setPoxY(float y){
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
  protected float getPoxY(){
    return posY;
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




/** TODO compleate this
 * [update updates the scales of the group and its children relitive to PApplet's size.]
 */
  protected void update(){
    //sets toolbar size and position
    g.setSize((int)Math.round(sketch.width*sizeX), (int)Math.round(sketch.height*sizeY));
    g.setPosition((int)Math.round(sketch.width*posX), (int)Math.round(sketch.height*posY));
    g.setBackgroundHeight((int)Math.round(sketch.height*sizeX));

    //sets FController size and position
    //curent version does not use priotirtys
    float sizeOfEach = (float)conList.size();
    for(int i = 0; i < conList.size(); i++){

    }


    /*
    private int pixlePercentX(float f){}

    private int pixlePercentY(float f){}
   */
  }


}
