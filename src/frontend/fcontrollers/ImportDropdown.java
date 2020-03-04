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

public class ImportDropdown extends FDropdown{
  public ImportDropdown(ControlP5 cp5, FToolbar parrent, GUI gui){

    super(cp5, "Import", parrent, gui);
    super.dropdown.plugTo(this, "plug");
  }

/**
 * [rect fuction name must match name given in supper]
 * @param val [description]
 */
  public void plug(int val){
    getGUI().setFont(val);
  }

}
