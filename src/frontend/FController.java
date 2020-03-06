package frontend;
/**
 * <h1>FController</h1>
 * Fcontroller is an abstract object for fcontrollers. These are adapters for ControlP5 objects.
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */
 import frontend.controlP5.*;
 import frontend.fcontrollers.*;

public abstract class FController{
  protected ControlP5 cp5;

  // the size and position of the group and toolbar
  protected float sizeX, sizeY, posX, posY;

  //name of the controller
  protected String name, bengali;

  //sizePriority is 1 by default. The larger sizePriority is the more space it takes from other FControllers in the group.
  protected float sizePriority = 1;


  //Parrent FToolbar
  protected FToolbar parrent;

  //buttons need to edit gui state
  GUI gui;
  public FController(ControlP5 cp5, String name, FToolbar parrent, GUI gui){
    this.gui = gui;
    this.cp5 = cp5;
    this.name = name;
    this.parrent = parrent;
    sizeX = 100;
    sizeY = 100;
    posX = 0;
    posX = 0;
  }

/**
 * [update updates the ControlP5 object to the FControlers size]
 */
  abstract protected void update();

  /**
   * [setSizeX sets the width of the controller in percentage of group]
   * @param x [percentage of the width of the group the controller takes up]
   */
    protected void setSizeX(float x){
      this.sizeX = x;
    }

  /**
   * [setSizeY sets the height of the controller in percentage of group]
   * @param y [percentage of the height of the group the controller takes up]
   */
    protected void setSizeY(float y){
      this.sizeY = y;
    }

  /**
   * [setSize sets the width and height of the controller in percentage of group]
   * @param x [percentage of the width of the group the controller takes up]
   * @param y [percentage of the height of the group the controller takes up]
   */
    protected void setSize(float x, float y){
      this.sizeX = x;
      this.sizeY = y;
    }

  /**
   * [getSizeX gets the width of the controller in percentage of group]
   * @return [percentage of the width of the group the controller takes up]
   */
    protected float getSizeX(){
      return sizeX;
    }

  /**
   * [getSizeY getSizeX gets the height of the controller in percentage of group]
   * @return [percentage of the height of the group the controller takes up]
   */
    protected float getSizeY(){
      return sizeY;
    }

  /**
   * [setPosX sets the position of the controller in percentage of group' width]
   * @param x [percentage of group's width where the controller's upper left corner is]
   */
    protected void setPosX(float x){
      this.posX = x;
    }

  /**
   * [setPoxY sets the position of the controller in percentage of group's height]
   * @param y [percentage of group's height where the controller's upper left corner is]
   */
    protected void setPoxY(float y){
      this.posY = y;
    }

  /**
   * [setPos sets the position of the controller in percentage of group's width and height]
   * @param x [percentage of group's width where the controller's upper left corner is]
   * @param y [percentage of group's height where the controller's upper left corner is]
   */
    protected void setPos(float x, float y){
      this.posX = x;
      this.posY = y;
    }

  /**
   * [getPosX gets the position of the controller in percentage of group's width]
   * @return [percentage of group's width where the controller's upper left corner is]
   */
    protected float getPosX(){
      return posX;
    }

  /**
   * [getPoxY sets the position of the controller in percentage of group's height]]
   * @return [percentage of group's hieght where the controller's upper left corner is]
   */
    protected float getPosY(){
      return posY;
    }

  /**
   * [setName sets the controller's name]
   * @param name [the controller's name]
   */
    protected void setName(String name){
      this.name = name;
    }

  /**
   * [getName gets the controller's name]
   * @return [the controller's name]
   */
    protected String getName(){
      return name;
    }

  /**
   * [setSizePriority sets the priority of space it takes from other FControllers in the group]
   * @param s [the priority of space it takes from other FControllers in the group]
   */
    protected void setSizePriority(float s){
      sizePriority = s;
    }

  /**
   * [getSizePriority gets the priority of space it takes from other FControllers in the group]
   * @return [the priority of space it takes from other FControllers in the group]
   */
    protected float getSizePriority(){
      return sizePriority;
    }


    protected GUI getGUI(){
      return gui;
    }


    //Comment on this better
    //This was suposed to setup a callback but I can't get it to setup properly from an abstract class
    /**
     * [trigger preforms the action the button is for]
     */
  //  abstract public void trigger(int val);

}
