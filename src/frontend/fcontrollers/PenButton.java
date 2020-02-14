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

public class PenButton extends FButton{
  public PenButton(ControlP5 cp5, FToolbar parrent, GUI gui){

    super(cp5, "pen", parrent, gui);
    super.button.plugTo(this);
  }

/**
 * [rect fuction name must match name given in supper]
 * @param val [description]
 */
  public void pen(int val){
    //System.out.println(val);
    getGUI().tool = 'p';

  }

}
