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

public class TrashButton extends FButton{
  public TrashButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "trash", parrent, gui);
    super.button.plugTo(this);
  }


  public void trash(int val){
    //System.out.println("Elip " + val);
    getGUI().trash();

  }

}
