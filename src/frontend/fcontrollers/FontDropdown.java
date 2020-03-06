package frontend.fcontrollers;
/**
 * <h1>FontDropdown</h1>
 * FButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class FontDropdown extends FDropdown{
  public FontDropdown(ControlP5 cp5, FToolbar parrent, GUI gui){

    super(cp5, "Font", parrent, gui);
    super.dropdown.plugTo(this, "font");
  }

/**
 * [rect fuction name must match name given in supper]
 * @param val [description]
 */
  public void font(int val){
    //System.out.println(val);
    getGUI().setFont(val);
  }

}
