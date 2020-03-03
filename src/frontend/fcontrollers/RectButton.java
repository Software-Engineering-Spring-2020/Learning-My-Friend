package frontend.fcontrollers;
/**
 * <h1>FButton</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class RectButton extends FButton{
  public RectButton(ControlP5 cp5, FToolbar parrent, GUI gui){

    super(cp5, "Rectangle", parrent, gui);
    super.button.plugTo(this, "rect");
    //super.button.setImages(loadImage("times-square.png"), loadImage("times-square.png"), loadImage("times-square.png"));
  }

/**
 * [rect fuction name must match name given in supper]
 * @param val [description]
 */
  public void rect(int val){
    getGUI().setTool('r');

  }

}
