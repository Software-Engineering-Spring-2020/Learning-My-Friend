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

public class FadeInButton extends FButton{
  public FadeInButton(ControlP5 cp5, FToolbar parrent, GUI gui){

    super(cp5, "FadeIn", parrent, gui);
    super.button.plugTo(this);
    //super.button.setImages(loadImage("times-square.png"), loadImage("times-square.png"), loadImage("times-square.png"));
  }

/**
 * [rect fuction name must match name given in supper]
 * @param val [description]
 */
  public void FadeIn(int val){
    //System.out.println(val);
    getGUI().setTool('f');

  }

}
