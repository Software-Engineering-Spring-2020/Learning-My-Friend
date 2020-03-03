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

public class RestoreTrashButton extends FButton{
  public RestoreTrashButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "Restore Trash", parrent, gui);
    super.button.plugTo(this, "restore");
  }


  public void restore(int val){
    getGUI().restore();

  }

}
