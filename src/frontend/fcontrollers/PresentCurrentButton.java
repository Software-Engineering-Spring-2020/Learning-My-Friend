package frontend.fcontrollers;
/**
 * <h1>PresentButton</h1>
 * PresentButton
 *
 * @author Hunter Chasens
 * @version 1.0
 * @since 02.12.2019
 *
 */

import frontend.*;
import frontend.controlP5.*;

public class PresentCurrentButton extends FButton{
  public PresentCurrentButton(ControlP5 cp5, FToolbar parrent, GUI gui){
    super(cp5, "From Current", parrent, gui);
    super.button.plugTo(this, "present");
  }


  public void present(int val){
    getGUI().present(false);
  }

}
